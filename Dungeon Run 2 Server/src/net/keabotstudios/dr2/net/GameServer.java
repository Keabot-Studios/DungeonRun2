package net.keabotstudios.dr2.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import net.keabotstudios.dr2.net.packets.ConnectPacket;
import net.keabotstudios.dr2.net.packets.DisconnectPacket;
import net.keabotstudios.dr2.net.packets.GamePacket;
import net.keabotstudios.dr2.net.packets.PacketListener;
import net.keabotstudios.dr2.net.packets.GamePacket.PacketType;
import net.keabotstudios.superserial.containers.SSDatabase;

public class GameServer {
	
	// UDP Protocol!
	
	private int port;
	private Thread listenThread;
	private boolean listening = false;
	private DatagramSocket socket;
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receiveDataBuffer = new byte[MAX_PACKET_SIZE * 16];
	
	private ArrayList<PacketListener> packetListeners = new ArrayList<PacketListener>();

	public GameServer(int port) {
		this.port = port;
	}
	
	public void start() {
		if(listening) return;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		listening = true;
		listenThread = new Thread(() -> listen(), "Dungeon Run 2 Server Listen Thread");
		listenThread.start();
	}
	
	public void stop() {
		if(!listening) return;
		listening = false;
		try {
			listenThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		socket.disconnect();
	}

	private void listen() {
		while (listening) {
			DatagramPacket packet = new DatagramPacket(receiveDataBuffer, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
				process(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void process(DatagramPacket rawPacket) {
		byte[] data = rawPacket.getData();
		if(SSDatabase.isValidData(data)) {
			SSDatabase database = SSDatabase.Deserialize(data);
			GamePacket packet = null;
			switch (PacketType.getFromName(database.getName())) {
			case CONNECT:
				packet = new ConnectPacket(database);
				break;
			case DISCONNECT:
				packet = new DisconnectPacket(database);
				break;
			case NULL:
				break;
			}
			process(packet);
		}
	}
	
	private void process(GamePacket packet) {
		if(packet == null) return;
		for(PacketListener pl : packetListeners) {
			pl.onPacketReceived(packet);
		}
	}
	
	public void addPacketListener(PacketListener listener) {
		packetListeners.add(listener);
	}
	
	public void removePacketListener(PacketListener listener) {
		packetListeners.remove(listener);
	}

	public void send(GamePacket packet) {
		send(packet.getData(), packet.getAddress(), packet.getPort());
	}
	
	private void send(byte[] data, InetAddress address, int port) {
		assert(socket.isConnected());
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GameServer server = new GameServer(8192);
		server.start();
	}

}

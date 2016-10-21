package net.keabotstudios.dr2.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.net.packet.ConnectPacket;
import net.keabotstudios.dr2.net.packet.DisconnectPacket;
import net.keabotstudios.dr2.net.packet.GamePacket;
import net.keabotstudios.dr2.net.packet.GamePacket.PacketType;
import net.keabotstudios.superserial.SSSerialization;
import net.keabotstudios.superserial.SSType.SSDataType;

public class GameServer {
	
	// UDP Protocol!
	
	private int port;
	private Thread listenThread, gameThread;
	private boolean listening = false, running = false;;
	private DatagramSocket socket;
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receiveDataBuffer = new byte[MAX_PACKET_SIZE * 16];
	
	private Set<ServerClient> clients = new HashSet<ServerClient>();
	
	private Level level;

	public GameServer(int port) {
		this.port = port;
		level = new Level(100, 100, null);
	}
	
	public void start() {
		if(listening && running) return;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		running = true;
		gameThread = new Thread(() -> update(), "Dungeon Run 2 Server Game Thread");
		gameThread.start();
		listening = true;
		listenThread = new Thread(() -> listen(), "Dungeon Run 2 Server Listen Thread");
		listenThread.start();
		System.out.println("Started server on port " + port);
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
	
	private void update() {
		
	}
	
	private void process(DatagramPacket rawPacket) {
		byte[] data = rawPacket.getData();
		int pointer = 0;
		byte[] header = new byte[GamePacket.PACKET_HEADER.length];
		SSSerialization.readBytes(data, pointer, header);
		pointer += GamePacket.PACKET_HEADER.length;
		if(!Arrays.equals(header, GamePacket.PACKET_HEADER)) return;
		byte id = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();
		PacketType type = PacketType.getFromId(id);
		switch(type) {
		case CONNECT:
			process(new ConnectPacket(data, rawPacket.getAddress(), rawPacket.getPort()));
			break;
		case DISCONNECT:
			process(new DisconnectPacket(data, rawPacket.getAddress(), rawPacket.getPort()));
			break;
		default:
		case NULL:
			break;
		}
	}
	
	private void process(GamePacket packet) {
		if(packet == null) return;
		if(packet instanceof ConnectPacket) {
			ConnectPacket connectPacket = (ConnectPacket) packet;
			System.out.println("Client connected with playerID: " + connectPacket.getPlayerID());
			clients.add(new ServerClient(connectPacket.getAddress(), connectPacket.getPort(), connectPacket.getPlayerID()));
		} else if(packet instanceof DisconnectPacket) {
			DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
			System.out.println("Client with playerID: " + disconnectPacket.getPlayerID() + " has disconnected.");
			clients.remove(new ServerClient(disconnectPacket.getAddress(), disconnectPacket.getPort(), disconnectPacket.getPlayerID()));
		}
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

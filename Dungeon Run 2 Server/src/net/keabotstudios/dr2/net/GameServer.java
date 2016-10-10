package net.keabotstudios.dr2.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import net.keabotstudios.superserial.containers.SSDatabase;

public class GameServer {
	
	// UDP Protocol!
	
	private int port;
	private Thread listenThread;
	private boolean listening = false;
	private DatagramSocket socket;
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receiveDataBuffer = new byte[MAX_PACKET_SIZE * 16];

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
	
	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		
		if(SSDatabase.isValidData(data)) {
			SSDatabase database = SSDatabase.Deserialize(data);
			process(database, address, port);
		}
	}
	
	private void process(SSDatabase database, InetAddress address, int port) {
		System.out.println("---------");
		System.out.println("DATA FROM: " + address.getHostAddress() + ":" + port);
		System.out.println(database.toString());
	}

	public void send(byte[] data, InetAddress address, int port) {
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

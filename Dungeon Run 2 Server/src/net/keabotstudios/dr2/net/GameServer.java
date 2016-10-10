package net.keabotstudios.dr2.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.keabotstudios.superserial.containers.SSDatabase;
import net.keabotstudios.superserial.containers.SSField;
import net.keabotstudios.superserial.containers.SSObject;
import net.keabotstudios.superserial.containers.SSString;

public class GameServer {
	
	// UDP Protocol!
	
	private int port;
	private Thread listenThread;
	private boolean listening = false;
	private DatagramSocket socket;

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
		listenThread = new Thread(() -> listen());
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
	}

	private void listen() {
		while (listening) {
			
		}
	}
	
	private void process(DatagramPacket packet) {
		
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
		
		InetAddress address = null;
		try {
			address = InetAddress.getByName("192.168.1.1");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		SSDatabase database = new SSDatabase("testData");
		SSObject stuff = new SSObject("stuff");
		stuff.addString(new SSString("memes", "How many memes?"));
		database.addObject(stuff);
		byte[] data = new byte[database.getSize()];
		database.writeBytes(data, 0);
		server.send(data, address, 8192);
		server.stop();
	}

}

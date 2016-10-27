package net.keabotstudios.dr2.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import net.keabotstudios.dr2.net.packet.ConnectPacket;
import net.keabotstudios.dr2.net.packet.ConnectionVerifyPacket;
import net.keabotstudios.dr2.net.packet.DisconnectPacket;
import net.keabotstudios.dr2.net.packet.GamePacket;
import net.keabotstudios.dr2.net.packet.GamePacket.PacketType;
import net.keabotstudios.dr2.net.packet.GamePacketListener;
import net.keabotstudios.superserial.SSSerialization;
import net.keabotstudios.superserial.SSType.SSDataType;

public class GameClient {

	// UDP Protocol!

	public enum ConnectionError {
		NONE, INVALID_HOST, SOCKET_EXCEPTION
	}

	private String stringAddress;
	private int port;
	private int playerID;
	private InetAddress serverAddress;
	private ConnectionError errorCode = ConnectionError.NONE;

	private Thread listenThread;
	private volatile boolean listening = false, connected = false;
	private final int MAX_PACKET_SIZE = 4096, PACKET_BUFFER_SIZE = 8;
	private int receiveDataBufferIndex = 0;
	private byte[] receiveDataBuffer = new byte[MAX_PACKET_SIZE * PACKET_BUFFER_SIZE];

	private DatagramSocket socket;
	
	private ArrayList<GamePacketListener> packetListeners = new ArrayList<GamePacketListener>();

	/**
	 * 
	 * @param host
	 *            Eg. {@code 192.168.1.1:5000}
	 */
	public GameClient(String host, int playerID) {
		String[] parts = host.split(":");
		if (parts.length != 2) {
			errorCode = ConnectionError.INVALID_HOST;
			return;
		}
		this.stringAddress = parts[0];
		this.port = Integer.parseInt(parts[1]);
		this.playerID = playerID;
	}

	/**
	 * 
	 * @param host
	 *            Eg. {@code 192.168.1.1}
	 * @param port
	 *            Eg. {@code 5000}
	 */
	public GameClient(String host, int port, int playerID) {
		this.stringAddress = host;
		this.port = port;
		this.playerID = playerID;
	}

	public boolean connect() {
		try {
			serverAddress = InetAddress.getByName(stringAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			errorCode = ConnectionError.INVALID_HOST;
			return false;
		}
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			errorCode = ConnectionError.SOCKET_EXCEPTION;
			return false;
		}
		listening = true;
		listenThread = new Thread(() -> listen(), "Dungeon Run 2 Client Listen Thread");
		listenThread.start();
		send(new ConnectPacket(serverAddress, port, playerID));
		System.out.println("Trying to connect to server: " + serverAddress.getHostAddress() + ":" + port);
		addPacketListener(new GamePacketListener() {
			public void onPacketRecieved(GamePacket packet) {
				if (packet instanceof ConnectionVerifyPacket) {
					if (!isConnected() && packet.getAddress().equals(getServerAddress())) {
						connected = true;
						System.out.println("connected to server: " + packet.getAddress() + ":" + packet.getPort());
					}
				}
			}
		});
		while(!connected) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public void disconnect() {
		if (!connected)
			return;
		try {
			listenThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connected = false;
		send(new DisconnectPacket(serverAddress, port, playerID));
		serverAddress = null;
	}
	
	private void verifyConnection() {
		
	}

	private void listen() {
		while (listening) {
			DatagramPacket packet = new DatagramPacket(receiveDataBuffer, receiveDataBufferIndex * MAX_PACKET_SIZE, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
				receiveDataBufferIndex++;
				if (receiveDataBufferIndex > PACKET_BUFFER_SIZE)
					receiveDataBufferIndex = 0;
				process(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void process(DatagramPacket rawPacket) {
		byte[] data = rawPacket.getData();
		int pointer = 0;
		byte[] header = new byte[GamePacket.PACKET_HEADER.length];
		SSSerialization.readBytes(data, pointer, header);
		pointer += GamePacket.PACKET_HEADER.length;
		if (!Arrays.equals(header, GamePacket.PACKET_HEADER))
			return;
		byte id = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();
		PacketType type = PacketType.getFromId(id);
		GamePacket packet = null;
		switch (type) {
		case CONNECT:
			break;
		case DISCONNECT:
			break;
		case CONNECTION_VERIFY:
			packet = new ConnectionVerifyPacket(data, rawPacket.getAddress(), pointer);
			if (!((ConnectionVerifyPacket) packet).goingToServer() && rawPacket.getAddress().equals(serverAddress))
				connected = true;
			break;
		default:
		case NULL:
			break;
		}
		procces(packet);
	}

	public void send(GamePacket packet) {
		send(packet.getData());
	}

	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void procces(GamePacket packet) {
		if(packet == null) return;
		for(GamePacketListener listener : packetListeners) {
			listener.onPacketRecieved(packet);
		}
	}
	
	public void addPacketListener(GamePacketListener listener) {
		packetListeners.add(listener);
	}

	/**
	 * @return The IP of the server this client wants to connect to, as a {@code String}
	 */
	public String getServerAddressAsString() {
		return stringAddress;
	}

	public int getPort() {
		return port;
	}

	/**
	 * @return The IP of the server this client wants to connect to, as an {@code InetAddress}
	 */
	public InetAddress getServerAddress() {
		return serverAddress;
	}

	public ConnectionError getErrorCode() {
		return errorCode;
	}

	public boolean isConnected() {
		return connected;
	}

}

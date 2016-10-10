package net.keabotstudios.dr2.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.keabotstudios.superserial.containers.SSDatabase;
import net.keabotstudios.superserial.containers.SSObject;

public class GameClient {

	// UDP Protocol!

	public enum ConnectionError {
		NONE, INVALID_HOST, SOCKET_EXCEPTION
	}

	private String ipAddress;
	private int port;
	private ConnectionError errorCode = ConnectionError.NONE;

	private InetAddress serverAddress;
	private DatagramSocket socket;

	/**
	 * 
	 * @param host
	 *            Eg. {@code 192.168.1.1:5000}
	 */
	public GameClient(String host) {
		String[] parts = host.split(":");
		if(parts.length != 2) {
			errorCode = ConnectionError.INVALID_HOST;
			return;
		}
		this.ipAddress = parts[0];
		this.port = Integer.parseInt(parts[1]);
	}

	/**
	 * 
	 * 
	 * @param host Eg. {@code 192.168.1.1}
	 * @param port Eg. {@code 5000}
	 */
	public GameClient(String host, int port) {
		this.ipAddress = host;
		this.port = port;
	}

	public boolean connect() {
		try {
			serverAddress = InetAddress.getByName(ipAddress);
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
		sendConnectionPacket();
		return true;
	}

	private void sendConnectionPacket() {
		SSDatabase database = new SSDatabase("serverMessage");
		SSObject root = new SSObject("root");
		database.addObject(root);
		byte[] data = new byte[database.getSize()];
		database.writeBytes(data, 0);
		send(data);
	}

	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConnectionError getErrorCode() {
		return errorCode;
	}

}

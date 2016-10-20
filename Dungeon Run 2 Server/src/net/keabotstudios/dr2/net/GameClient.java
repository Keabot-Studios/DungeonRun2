package net.keabotstudios.dr2.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.keabotstudios.dr2.net.packets.ConnectPacket;
import net.keabotstudios.dr2.net.packets.GamePacket;

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

	private DatagramSocket socket;

	/**
	 * 
	 * @param host
	 *            Eg. {@code 192.168.1.1:5000}
	 */
	public GameClient(String host, int playerID) {
		String[] parts = host.split(":");
		if(parts.length != 2) {
			errorCode = ConnectionError.INVALID_HOST;
			return;
		}
		this.stringAddress = parts[0];
		this.port = Integer.parseInt(parts[1]);
		this.playerID = playerID;
	}

	/**
	 * 
	 * 
	 * @param host Eg. {@code 192.168.1.1}
	 * @param port Eg. {@code 5000}
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
		send(new ConnectPacket(serverAddress, port, playerID));
		return true;
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

}

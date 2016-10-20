package net.keabotstudios.dr2.net;

import java.net.InetAddress;

public class ServerClient {
	
	public final InetAddress address;
	public final int port;
	public final long playerID;
	public boolean status = false;
	
	public ServerClient(InetAddress address, int port, long playerID) {
		this.address = address;
		this.port = port;
		this.playerID = playerID;
		this.status = true;
	}
	
	public int hashCode() {
		return (int) playerID;
	}

}

package net.keabotstudios.dr2.net.packet;

import java.net.InetAddress;

import net.keabotstudios.superserial.BinaryWriter;
import net.keabotstudios.superserial.SSSerialization;

public class ConnectionVerifyPacket extends GamePacket {
	
	private boolean toServer;

	public ConnectionVerifyPacket(InetAddress address, int port, boolean toServer) {
		super(PacketType.CONNECTION_VERIFY, address, port);
		this.toServer = toServer;
	}

	public ConnectionVerifyPacket(byte[] data, InetAddress address, int port) {
		super(PacketType.CONNECTION_VERIFY, address, port);
		toServer = SSSerialization.readBoolean(data, 0);
	}

	public byte[] getData() {
		BinaryWriter data = new BinaryWriter();
		data.write(toServer);
		return data.getBuffer();
	}
	
	public boolean goingToServer() {
		return toServer;
	}

}

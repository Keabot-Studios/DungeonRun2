package net.keabotstudios.dr2.net.packet;

import java.net.InetAddress;

import net.keabotstudios.superserial.BinaryWriter;
import net.keabotstudios.superserial.SSSerialization;
import net.keabotstudios.superserial.SSType.SSDataType;

public class DisconnectPacket extends GamePacket {

	private int playerID;

	public DisconnectPacket(InetAddress address, int port, int playerID) {
		super(PacketType.CONNECT, address, port);
		this.playerID = playerID;
	}

	public DisconnectPacket(byte[] data, InetAddress address, int port) {
		super(PacketType.CONNECT, address, port);
		int pointer = SSDataType.BYTE.getSize() * (PACKET_HEADER.length + 1);
		port = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		playerID = SSSerialization.readInteger(data, pointer);
	}

	public byte[] getData() {
		BinaryWriter data = new BinaryWriter();
		data.write(PACKET_HEADER);
		data.write(type.getId());
		data.write(playerID);
		return data.getBuffer();
	}

	public int getPlayerID() {
		return playerID;
	}

}

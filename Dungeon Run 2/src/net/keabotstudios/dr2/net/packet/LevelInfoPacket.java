package net.keabotstudios.dr2.net.packet;

import java.net.InetAddress;

import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.superserial.SSSerialization;
import net.keabotstudios.superserial.SSType.SSDataType;

public class LevelInfoPacket extends GamePacket {
	
	private Level level;

	public LevelInfoPacket(PacketType type, InetAddress address, int port, Level level) {
		super(type, address, port);
		this.level = level;
	}
	
	public LevelInfoPacket(byte[] data, InetAddress address, int port) {
		super(PacketType.CONNECT, address, port);
		int pointer = SSDataType.BYTE.getSize() * (PACKET_HEADER.length + 1);
		port = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		int width = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		int height = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		level = new Level(width, height, null);
	}

	public byte[] getData() {
		return null;
	}

}

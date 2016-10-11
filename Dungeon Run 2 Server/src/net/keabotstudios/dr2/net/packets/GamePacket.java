package net.keabotstudios.dr2.net.packets;

import java.net.InetAddress;

import net.keabotstudios.superserial.containers.SSDatabase;
import net.keabotstudios.superserial.containers.SSField;
import net.keabotstudios.superserial.containers.SSObject;
import net.keabotstudios.superserial.containers.SSString;

public abstract class GamePacket {
	
	public enum PacketType {
		NULL("null"), CONNECT("connect"), DISCONNECT("disconnect");
		
		private String name;
		private PacketType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public static PacketType getFromName(String name) {
			if(name.equals(NULL.name)) return NULL;
			for(PacketType t : values()) {
				if(t.name.equals(name)) return t;
			}
			return NULL;
		}
	}
	
	protected final PacketType type;
	protected InetAddress address;
	protected int port;
	
	protected GamePacket(PacketType type, InetAddress address, int port) {
		this.type = type;
		this.address = address;
		this.port = port;
	}

	public PacketType getType() {
		return type;
	}
	
	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}
	
	protected SSDatabase getBaseDatabase() {
		SSDatabase packet = new SSDatabase(type.getName());
		SSObject root = new SSObject("root");
		root.addString(new SSString("address", this.address.getHostAddress()));
		root.addField(SSField.Integer("port", this.port));
		packet.addObject(root);
		return packet;
	}

	public abstract byte[] getData();

}

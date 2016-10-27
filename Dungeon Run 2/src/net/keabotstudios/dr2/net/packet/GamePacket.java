package net.keabotstudios.dr2.net.packet;

import java.net.InetAddress;

public abstract class GamePacket {

	public final static byte[] PACKET_HEADER = new byte[] {
			0x12,
			0x33
	};

	public enum PacketType {
		NULL((byte) 0x00), CONNECT((byte) 0x01), DISCONNECT((byte) 0x02), CONNECTION_VERIFY((byte) 0x03), LEVEL_INFO((byte) 0x04);

		private byte id;

		private PacketType(byte id) {
			this.id = id;
		}

		public static PacketType getFromId(byte id) {
			if (id == NULL.id)
				return NULL;
			for (PacketType t : values()) {
				if (t.id == id)
					return t;
			}
			return NULL;
		}

		public byte getId() {
			return id;
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

	public abstract byte[] getData();

}

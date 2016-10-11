package net.keabotstudios.dr2.net.packets;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.keabotstudios.superserial.containers.SSDatabase;
import net.keabotstudios.superserial.containers.SSField;
import net.keabotstudios.superserial.containers.SSObject;
import net.keabotstudios.superserial.containers.SSString;

public class DisconnectPacket extends GamePacket {

	private long playerID;

	public DisconnectPacket(InetAddress address, int port, long playerID) {
		super(PacketType.DISCONNECT, address, port);
		this.playerID = playerID;
	}
	
	public DisconnectPacket(SSDatabase data) {
		super(PacketType.DISCONNECT, null, -1);
		SSObject root = data.getObject("root");
		try {
			address = InetAddress.getByName(root.getString("address").getString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		port = root.getField("port").getInteger();
		playerID = root.getField("playerID").getLong();
	}

	public byte[] getData() {
		SSDatabase connectPacket = getBaseDatabase();
		SSObject root = connectPacket.getObject("root");
		root.addField(SSField.Long("playerID", this.playerID));
		connectPacket.addObject(root);
		byte[] data = new byte[connectPacket.getSize()];
		connectPacket.writeBytes(data, 0);
		return data;
	}

	public long getPlayerID() {
		return playerID;
	}

}

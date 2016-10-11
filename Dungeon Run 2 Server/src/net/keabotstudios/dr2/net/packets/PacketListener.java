package net.keabotstudios.dr2.net.packets;

public abstract interface PacketListener {
	
	public abstract void onPacketReceived(GamePacket packet);

}

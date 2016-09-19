package net.keabotstudios.dr2.game;

public enum Direction {
	NORTH((byte) 0), NORTHEAST((byte) 1), EAST((byte) 2), SOUTHEAST((byte) 3), SOUTH((byte) 4), SOUTHWEST((byte) 5), WEST((byte) 6), NORTHWEST((byte) 7), UNKNOWN((byte) -1);
	
	private final byte id;
	private Direction(byte id) {
		this.id = id;
	}
	
	public byte getId() {
		return id;
	}
	
	public static Direction getFromId(byte id) {
		for(Direction d : Direction.values()) {
			if(d.getId() == id) return d;
		}
		return Direction.UNKNOWN;
	}
	
	public static Direction getFromRad(double rotRad) {
		double rotDeg = Math.toDegrees(rotRad) % 360.0;
		byte dirId = (byte) ((int) Math.round((double) (rotDeg / 45)) % (Direction.values().length - 1));
		return getFromId(dirId);
	}
}

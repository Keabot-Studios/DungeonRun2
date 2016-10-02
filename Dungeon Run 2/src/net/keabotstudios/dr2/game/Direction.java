package net.keabotstudios.dr2.game;

public enum Direction {
	NORTH(0.0f), NORTHEAST(0.5f), EAST(1.0f), SOUTHEAST(1.5f), SOUTH(2.0f), SOUTHWEST(2.5f), WEST(3.0f), NORTHWEST(3.5f), UNKNOWN(-1.0f);

	private final float id;

	private Direction(float id) {
		this.id = id;
	}

	public float getId() {
		return id;
	}

	public double inRadians() {
		return id * (Math.PI / 2.0);
	}

	public static Direction getFromRad(double rotRad) {
		double rotDeg = Math.toDegrees(rotRad) % 360.0;
		byte dirId = (byte) ((int) Math.round((double) (rotDeg / 45)) % (Direction.values().length - 1));
		return Direction.values()[dirId];
	}
}

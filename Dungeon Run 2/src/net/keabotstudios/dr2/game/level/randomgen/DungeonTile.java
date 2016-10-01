package net.keabotstudios.dr2.game.level.randomgen;

public class DungeonTile {

	public boolean connectedUp;
	public boolean connectedDown;
	public boolean connectedLeft;
	public boolean connectedRight;

	int wallValue = 14;
	int floorValue = 35;

	public DungeonTile(boolean connectUp, boolean connectDown, boolean connectLeft, boolean connectRight) {
		connectedDown = connectDown;
		connectedLeft = connectLeft;
		connectedRight = connectRight;
		connectedUp = connectUp;
	}

	public int getTileID() {
		if (!connectedUp && !connectedDown && !connectedLeft && !connectedRight)
			return 0;
		return -1;
	}

	public int getUpValue() {
		if (connectedUp)
			return floorValue;

		return wallValue;
	}

	public int getDownValue() {
		if (connectedDown)
			return floorValue;

		return wallValue;
	}

	public int getLeftValue() {
		if (connectedLeft)
			return floorValue;

		return wallValue;
	}

	public int getRightValue() {
		if (connectedRight)
			return floorValue;

		return wallValue;
	}

	public String toString() {
		return "(" + connectedUp + ", " + connectedDown + ", " + connectedLeft + ", " + connectedRight + ":"
				+ getTileID() + ")";
	}
}

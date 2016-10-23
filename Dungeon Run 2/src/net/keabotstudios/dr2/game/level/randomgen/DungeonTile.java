package net.keabotstudios.dr2.game.level.randomgen;

public class DungeonTile {

	public boolean connectedUp;
	public boolean connectedDown;
	public boolean connectedLeft;
	public boolean connectedRight;
	
	public int roomType;

	int wallValue = 14;
	int floorValue = 4;

	public DungeonTile(boolean connectUp, boolean connectDown, boolean connectLeft, boolean connectRight, int type) {
		connectedDown = connectDown;
		connectedLeft = connectLeft;
		connectedRight = connectRight;
		connectedUp = connectUp;
		roomType = type;
	}

	public int getTileID() {
		if (!connectedUp && !connectedDown && !connectedLeft && !connectedRight)
			return -1;
		return roomType;
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
		return "(" + connectedUp + ", " + connectedDown + ", " + connectedLeft + ", " + connectedRight + ":" + getTileID() + ")";
	}
}

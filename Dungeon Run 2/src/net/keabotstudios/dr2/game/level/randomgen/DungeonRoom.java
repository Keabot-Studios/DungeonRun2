package net.keabotstudios.dr2.game.level.randomgen;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.keabotstudios.dr2.game.level.object.Vector2;

public class DungeonRoom {

	public DungeonRoom() {
		roomBounds = new Rectangle();
	}

	public Rectangle roomBounds;

	public int connectionsToOtherRooms;

	public Vector2 getRandomWallPosition(Random mapGenRandom) {
		List<Vector2> positions = new ArrayList<Vector2>();
		for (int y = (int) roomBounds.y; y < roomBounds.y + roomBounds.height; y++) {
			for (int x = (int) roomBounds.x; x < roomBounds.x + roomBounds.width; x++) {
				if (x == roomBounds.x || y == roomBounds.y || y == roomBounds.y + roomBounds.height - 1 || x == roomBounds.x + roomBounds.width - 1) {
					positions.add(new Vector2(x, y));
				}
			}
		}

		return positions.get(mapGenRandom.nextInt(positions.size()));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!DungeonRoom.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final DungeonRoom other = (DungeonRoom) obj;
		if (!this.roomBounds.equals(other.roomBounds)) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + roomBounds.hashCode();
		return hash;
	}
}

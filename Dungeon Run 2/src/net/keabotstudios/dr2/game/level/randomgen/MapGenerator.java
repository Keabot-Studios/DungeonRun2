package net.keabotstudios.dr2.game.level.randomgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.keabotstudios.dr2.game.level.object.Vector2;

public class MapGenerator {
	public int mapWidth;
	public int mapHeight;

	public int roomCount;
	public int minRoomSize;
	public int maxRoomSize;
	int[][] tileMap;
	DungeonTile[][] dungeonTileMap;

	private int conCount;

	List<DungeonRoom> dungeonRooms = new ArrayList<DungeonRoom>();

	Random mapGenRandom;

	public MapGenerator(int height, int width, int maxRoomCount, int minRoomSize, int maxRoomSize) {
		mapWidth = width;
		mapHeight = height;
		roomCount = maxRoomCount;
		this.minRoomSize = minRoomSize;
		this.maxRoomSize = maxRoomSize;
	}

	public void generateMap() {
		tileMap = new int[mapWidth][mapHeight];
		dungeonTileMap = new DungeonTile[mapWidth][mapHeight];
		dungeonRooms = new ArrayList<DungeonRoom>();
		vertices = new HashMap<Vector2, Map<Vector2, Integer>>();
		PointsToChange = new ArrayList<Vector2>();

		mapGenRandom = new Random(1234);
		System.out.println("Generating Rooms...");
		generateRooms();

		System.out.println("Filling Empty Tiles...");
		fillEmptyTiles();
		
		setConnectionCount();

		System.out.println("Generating Paths...");
		generatePaths();

		System.out.println("Generating Map...");
		generateIntMap();

		System.out.println("Setting Spawn Point...");
		setSpawnPoint();
	}

	private void generateIntMap() {
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				tileMap[x][y] = dungeonTileMap[x][y].getTileID();
			}
		}
	}

	private void fillEmptyTiles() {
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				if (dungeonTileMap[x][y] == null) {
					dungeonTileMap[x][y] = new DungeonTile(false, false, false, false);
					tileMap[x][y] = 0;
				}
			}
		}
	}

	private void generateRooms() {
		for (int i = 0; i < roomCount; i++) {
			generateRoom();
		}
	}

	private void generateRoom() {
		int tries = 0;

		DungeonRoom newRoom = new DungeonRoom();
		boolean validRoom = false;
		while (!validRoom && tries < 16) {
			validRoom = true;
			newRoom.roomBounds.width = mapGenRandom.nextInt(maxRoomSize) + minRoomSize;
			newRoom.roomBounds.height = mapGenRandom.nextInt(maxRoomSize) + minRoomSize;
			newRoom.roomBounds.x = mapGenRandom.nextInt(mapWidth - newRoom.roomBounds.width);
			newRoom.roomBounds.y = mapGenRandom.nextInt(mapHeight - newRoom.roomBounds.height);

			if (dungeonRooms.size() > 0) {
				for (DungeonRoom room : dungeonRooms) {
					if (room.roomBounds.intersects(newRoom.roomBounds))
						validRoom = false;
				}
			}
			tries++;
		}
		dungeonRooms.add(newRoom);
		int startX = (int) newRoom.roomBounds.x;
		int startY = (int) newRoom.roomBounds.y;
		int width = (int) newRoom.roomBounds.width;
		int height = (int) newRoom.roomBounds.height;
		for (int y = startY; y < startY + height; y++) {
			for (int x = startX; x < startX + width; x++) {
				if (x == startX && y == startY) {
					dungeonTileMap[x][y] = new DungeonTile(true, false, false, true);
				} else if (x == startX && y == startY + height - 1) {
					dungeonTileMap[x][y] = new DungeonTile(false, true, false, true);
				} else if (x == startX + width - 1 && y == startY + height - 1) {
					dungeonTileMap[x][y] = new DungeonTile(false, true, true, false);
				} else if (x == startX + width - 1 && y == startY) {
					dungeonTileMap[x][y] = new DungeonTile(true, false, true, false);
				} else if (x == startX + width - 1) {
					dungeonTileMap[x][y] = new DungeonTile(true, true, true, false);
				} else if (x == startX) {
					dungeonTileMap[x][y] = new DungeonTile(true, true, false, true);
				} else if (y == startY + height - 1) {
					dungeonTileMap[x][y] = new DungeonTile(false, true, true, true);
				} else if (y == startY) {
					dungeonTileMap[x][y] = new DungeonTile(true, false, true, true);
				} else {
					dungeonTileMap[x][y] = new DungeonTile(true, true, true, true);
				}
			}
		}
	}

	Map<Vector2, Map<Vector2, Integer>> vertices;
	List<Vector2> PointsToChange = new ArrayList<Vector2>();

	private DungeonRoom findRoomWithConnections(DungeonRoom startingRoom) {
		if (connectionCount() > 0 && startingRoom.connectionsToOtherRooms == 0) {
			List<DungeonRoom> roomsWithConnections = new ArrayList<DungeonRoom>();
			for (DungeonRoom room : dungeonRooms) {
				if (room.connectionsToOtherRooms > 0 && !room.equals(startingRoom))
					roomsWithConnections.add(room);
			}
			return roomsWithConnections.get(mapGenRandom.nextInt(roomsWithConnections.size()));
		}
		return null;
	}

	private void generatePath(DungeonRoom startingRoom) {
		DungeonPathDigger digger = new DungeonPathDigger();
		digger.addtileMapVertices(dungeonTileMap, vertices, PointsToChange);
		DungeonRoom finishingRoom = findRoomWithConnections(startingRoom);
		while (finishingRoom == null || finishingRoom.equals(startingRoom)) {
			finishingRoom = dungeonRooms.get(mapGenRandom.nextInt(dungeonRooms.size()));
		}
		Vector2 startpos = startingRoom.getRandomWallPosition(mapGenRandom);
		List<Vector2> path = digger.shortestPath(startpos, finishingRoom.getRandomWallPosition(mapGenRandom));
		path.add(startpos);
		PointsToChange = path;
		vertices = digger.getVertices();

		startingRoom.connectionsToOtherRooms++;
		finishingRoom.connectionsToOtherRooms++;

		for (int i = 0; i < path.size(); i++) {
			Vector2 current = path.get(i);
			if (i < path.size() - 1) {
				Vector2 next = path.get(i + 1);
				if (next.getX() < current.getX()) {
					dungeonTileMap[(int) next.getX()][(int) next.getY()].connectedRight = true;
					dungeonTileMap[(int) current.getX()][(int) current.getY()].connectedLeft = true;
				}
				if (next.getX() > current.getX()) {
					dungeonTileMap[(int) next.getX()][(int) next.getY()].connectedLeft = true;
					dungeonTileMap[(int) current.getX()][(int) current.getY()].connectedRight = true;
				}
				if (next.getY() < current.getY()) {
					dungeonTileMap[(int) next.getX()][(int) next.getY()].connectedUp = true;
					dungeonTileMap[(int) current.getX()][(int) current.getY()].connectedDown = true;
				}
				if (next.getY() > current.getY()) {
					dungeonTileMap[(int) next.getX()][(int) next.getY()].connectedDown = true;
					dungeonTileMap[(int) current.getX()][(int) current.getY()].connectedUp = true;
				}
			}
		}
		System.out.println("Path Gen: " + (int) ((float) connectionCount() / (float) conCount * 100f) + "%");
	}

	public int connectionCount() {
		int i = 0;
		for (DungeonRoom room : dungeonRooms) {
			i += room.connectionsToOtherRooms;
		}
		return i;
	}

	private void setConnectionCount() {
		int remaining = maxConnections() - dungeonRooms.size() * 2;		
		
		float chance = mapGenRandom.nextFloat();
		
		int amount = (int)Math.floor(Math.log(chance)/Math.log(.75));
		
		conCount = dungeonRooms.size() * 2 + Math.max(0, Math.max(remaining, amount));
	}

	public int maxConnections() {
		return (int) (0.5f * Math.pow(dungeonRooms.size(), 2f) + dungeonRooms.size() / 2f);
	}

	private void generatePaths() {

		boolean allConnected = false;
		while (!allConnected) {
			allConnected = true;
			for (DungeonRoom room : dungeonRooms) {
				if (room.connectionsToOtherRooms == 0) {
					allConnected = false;
					generatePath(room);
				}
			}
		}

		while (connectionCount() < conCount) {
			List<DungeonRoom> rooms = new ArrayList<DungeonRoom>();
			for (DungeonRoom room : dungeonRooms) {
				rooms.add(room);
			}

			generatePath(rooms.get(mapGenRandom.nextInt(rooms.size())));
		}
	}

	Vector2 spawnPoint;

	public void setSpawnPoint() {
		List<Vector2> validPoints = new ArrayList<Vector2>();
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				if (tileMap[x][y] == -1)
					validPoints.add(new Vector2(x, y));
			}
		}

		spawnPoint = validPoints.get(mapGenRandom.nextInt(validPoints.size()));
	}

	public Vector2 getSpawnPoint() {
		return spawnPoint;
	}

	public int[][] getTileArray() {
		return tileMap;
	}
}

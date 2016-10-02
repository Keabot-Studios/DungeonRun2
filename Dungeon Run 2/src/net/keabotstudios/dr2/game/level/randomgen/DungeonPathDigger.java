package net.keabotstudios.dr2.game.level.randomgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.keabotstudios.dr2.game.level.object.Vector2;

public class DungeonPathDigger {

	Map<Vector2, Map<Vector2, Integer>> vertices = new HashMap<Vector2, Map<Vector2, Integer>>();

	public void addtileMapVertices(DungeonTile[][] tileMap, Map<Vector2, Map<Vector2, Integer>> oldVertices, List<Vector2> changes) {
		if (oldVertices == null || oldVertices.size() == 0) {
			for (int y = 0; y < tileMap[0].length; y++) {
				for (int x = 0; x < tileMap.length; x++) {
					HashMap<Vector2, Integer> connectedTo = new HashMap<Vector2, Integer>();
					if (y > 0)
						connectedTo.put(new Vector2(x, y - 1), tileMap[x][y].getDownValue());
					if (y < tileMap[0].length - 1)
						connectedTo.put(new Vector2(x, y + 1), tileMap[x][y].getUpValue());
					if (x > 0)
						connectedTo.put(new Vector2(x - 1, y), tileMap[x][y].getLeftValue());
					if (x < tileMap.length - 1)
						connectedTo.put(new Vector2(x + 1, y), tileMap[x][y].getRightValue());
					addVertex(new Vector2(x, y), connectedTo);
				}
			}
		} else {
			vertices = oldVertices;
			for (Vector2 position : changes) {
				int x = (int) position.getX();
				int y = (int) position.getY();
				HashMap<Vector2, Integer> connectedTo = new HashMap<Vector2, Integer>();
				if (y > 0)
					connectedTo.put(new Vector2(x, y - 1), tileMap[x][y].getDownValue());
				if (y < tileMap[0].length - 1)
					connectedTo.put(new Vector2(x, y + 1), tileMap[x][y].getUpValue());
				if (x > 0)
					connectedTo.put(new Vector2(x - 1, y), tileMap[x][y].getLeftValue());
				if (x < tileMap.length - 1)
					connectedTo.put(new Vector2(x + 1, y), tileMap[x][y].getRightValue());
				addVertex(new Vector2(x, y), connectedTo);
			}
			oldVertices = vertices;
		}
	}

	public Map<Vector2, Map<Vector2, Integer>> getVertices() {
		return vertices;
	}

	public void clearVertices() {
		vertices.clear();
	}

	public void addVertex(Vector2 tile, Map<Vector2, Integer> edges) {
		vertices.put(tile, edges);
	}

	public List<Vector2> shortestPath(Vector2 start, Vector2 finish) {
		Map<Vector2, Vector2> previous = new HashMap<Vector2, Vector2>();
		Map<Vector2, Integer> distances = new HashMap<Vector2, Integer>();
		List<Vector2> nodes = new ArrayList<Vector2>();

		List<Vector2> path = null;

		for (Map.Entry<Vector2, Map<Vector2, Integer>> vertex : vertices.entrySet()) {
			if (vertex.getKey().equals(start)) {
				distances.put(vertex.getKey(), 0);
			} else {
				distances.put(vertex.getKey(), Integer.MAX_VALUE);
			}

			nodes.add(vertex.getKey());
		}
		while (nodes.size() != 0) {
			nodes.sort((x, y) -> distances.get(x) - distances.get(y));

			Vector2 smallest = nodes.get(0);
			nodes.remove(smallest);

			if (smallest.equals(finish)) {
				path = new ArrayList<Vector2>();
				while (previous.containsKey(smallest)) {
					path.add(smallest);
					smallest = previous.get(smallest);
				}

				break;
			}

			if (distances.get(smallest) == Integer.MAX_VALUE) {
				break;
			}

			for (Map.Entry<Vector2, Integer> neighbor : vertices.get(smallest).entrySet()) {
				int alt = distances.get(smallest) + neighbor.getValue();
				if (alt < distances.get(neighbor.getKey())) {
					distances.put(neighbor.getKey(), alt);
					previous.put(neighbor.getKey(), smallest);
				}
			}
		}

		return path;
	}
}

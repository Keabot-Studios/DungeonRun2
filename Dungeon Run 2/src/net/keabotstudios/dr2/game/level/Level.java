package net.keabotstudios.dr2.game.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.game.level.object.block.Block;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.game.level.object.entity.Player;
import net.keabotstudios.dr2.game.level.object.entity.PlayerMP;
import net.keabotstudios.dr2.game.level.object.entity.SpawnPointEntity;
import net.keabotstudios.dr2.game.level.object.tile.Tile;
import net.keabotstudios.dr2.game.level.randomgen.MapGenerator;
import net.keabotstudios.dr2.math.Vector3;
import net.keabotstudios.superin.Input;

public class Level {

	private int renderDistance = 6000;

	private final int width, height;
	private Block[] blocks;
	private Tile[] tiles;

	private HashMap<String, Entity> entities = new HashMap<String, Entity>();

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.blocks = new Block[width * height];
		this.tiles = new Tile[width * height];

		MapGenerator gen = new MapGenerator(width, height, 8, 8, 15);
		gen.generateMap("oh boi!".hashCode());

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				blocks[height * y + x] = (gen.getTileArray()[x][y] == -1 ? Block.wallBlock : Block.emptyBlock);
				this.tiles[x + y * width] = (gen.getTileArray()[x][y] == 1 ? Tile.outside1 : Tile.inside1);
			}
		}

		SpawnPointEntity spawnEntity = new SpawnPointEntity(new Vector3(gen.getSpawnPoint().getX() + .5, 0.9, gen.getSpawnPoint().getY() + .5));
		entities.put("spawn", spawnEntity);
	}

	public Level(int width, int height, Block[] blocks) {
		this.width = width;
		this.height = height;
		this.blocks = new Block[width * height];
	}
	
	public void setPlayer(Game game) {
		entities.put("player", new Player(entities.get("spawn").getPos().subtract(new Vector3(0, entities.get("spawn").getPos().getY(), 0)), 0.0, game));
	}

	public Player getPlayer() {
		return (Player) getEntity("player");
	}

	public void update(Input input) {
		for (Entity e : entities.values()) {
			e.update(input, this);
		}
	}

	public PlayerMP[] getPlayerMPs() {
		ArrayList<PlayerMP> playerMPs = new ArrayList<PlayerMP>();
		for (Entity e : entities.values()) {
			if (e instanceof PlayerMP)
				playerMPs.add((PlayerMP) e);
		}
		return playerMPs.toArray(new PlayerMP[playerMPs.size()]);
	}

	public void setBlock(int x, int y, Block block) {
		if (x < 0 || y < 0 || x < width || y > height)
			blocks[x + y * width] = Block.wallBlock;
		else
			blocks[x + y * width] = block;
	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Block.wallBlock;
		return blocks[x + y * width];
	}

	public int getRenderDistance() {
		return renderDistance;
	}
	
	public void addEntitiy(String name, Entity entity) {
		
	}
	
	public boolean entityExists(String name) {
		return entities.containsKey(name) && entities.get(name) == null;
	}
	
	public void removeEntitiy(String name) {
		if(entityExists(name)) entities.remove(name);
	}

	public List<Entity> getEntites() {
		return Arrays.asList(entities.values().toArray(new Entity[entities.size()]));
	}

	public Entity getEntity(String eID) {
		if (entities.containsKey(eID))
			return entities.get(eID);
		return null;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.inside1;
		return tiles[x + y * width];
	}
}

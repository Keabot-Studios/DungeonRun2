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
import net.keabotstudios.dr2.game.level.randomgen.MapGenerator;
import net.keabotstudios.dr2.game.save.PlayerInfo;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.dr2.math.Vector3;
import net.keabotstudios.superin.Input;

public class Level {

	private double ceilPos;
	private Bitmap floorTex, ceilTex;
	private int renderDistance = 6000;

	private final int width, height;
	private Block[] blocks;

	private HashMap<String, Entity> entities = new HashMap<String, Entity>();
	
	private Game game;

	public Level(int width, int height, Game game) {
		this.width = width;
		this.height = height;
		this.blocks = new Block[width * height];
		this.game = game;
		Arrays.fill(blocks, Block.emptyBlock);

		MapGenerator gen = new MapGenerator(width, height, 8, 8, 15);
		gen.generateMap("oh boi!".hashCode());

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				blocks[height * y + x] = (gen.getTileArray()[x][y] == 0 ? Block.animBlock : Block.emptyBlock);
			}
		}

		SpawnPointEntity spawnEntity = new SpawnPointEntity(new Vector3(gen.getSpawnPoint().getX()+.5, 0.9, gen.getSpawnPoint().getY()+.5));
		entities.put("spawn", spawnEntity);

		entities.put(String.valueOf(game.getPlayerInfo().getPlayerID()), new Player(spawnEntity.getPos().clone(), 0, game));
		long mpPID = PlayerInfo.getRandomPlayerID();
		entities.put(String.valueOf(mpPID), new PlayerMP(spawnEntity.getPos().clone(), 0, mpPID, "Dat Boi"));

		floorTex = Texture.brick1Floor;
		ceilTex = Texture.brick1;
		ceilPos = 8;
	}
	
	public Level(int width, int height, Block[] blocks, HashMap<String, Entity> entities) {
		this.width = width;
		this.height = height;
		this.blocks = new Block[width * height];
		this.entities = entities;
		
		floorTex = Texture.brick1Floor;
		ceilTex = Texture.brick1;
		ceilPos = 8;
	}

	public Player getPlayer() {
		return (Player) getEntity(String.valueOf(game.getPlayerInfo().getPlayerID()));
	}
	
	public void update(Input input) {
		for (Entity e : entities.values()) {
			e.update(input, this);
		}
	}
	
	public PlayerMP[] getPlayerMPs() {
		ArrayList<PlayerMP> playerMPs = new ArrayList<PlayerMP>();
		for(Entity e : entities.values()) {
			if(e instanceof PlayerMP)
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

	public double getFloorPos() {
		return 8;
	}

	public double getCeilPos() {
		return ceilPos;
	}

	public Bitmap getFloorTexture() {
		return floorTex;
	}

	public Bitmap getCeilTexture() {
		return ceilTex;
	}

	public int getRenderDistance() {
		return renderDistance;
	}

	public List<Entity> getEntites() {
		return Arrays.asList(entities.values().toArray(new Entity[entities.size()]));
	}
	
	public Entity getEntity(String eID) {
		if(entities.containsKey(eID)) return entities.get(eID);
		return null;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}

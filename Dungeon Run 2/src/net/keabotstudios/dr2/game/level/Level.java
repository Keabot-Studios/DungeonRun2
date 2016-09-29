package net.keabotstudios.dr2.game.level;

import java.util.ArrayList;
import java.util.Arrays;

import net.keabotstudios.dr2.game.GameSettings;
import net.keabotstudios.dr2.game.level.object.block.Block;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.game.level.object.entity.Player;
import net.keabotstudios.dr2.game.level.object.entity.TestEntity;
import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.superin.Input;

public class Level {

	private double ceilPos;
	private Render floorTex, ceilTex;
	private int renderDistance = 5000;

	private final int width, height;
	private Block[] blocks;

	private Player player;

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public Level(int width, int height, GameSettings settings) {
		this.width = width;
		this.height = height;
		this.blocks = new Block[width * height];
		Arrays.fill(blocks, Block.empty);
		player = new Player(0, 0, 0, "Player", settings);
		entities.add(new TestEntity(10, 1, 10, "Test"));
		floorTex = Texture.brick1Floor;
		ceilTex = Texture.brick1;
		ceilPos = 4*8;
	}

	public Player getPlayer() {
		return player;
	}

	public void update(Input input) {
		player.update(input, entities);
		for (Entity e : entities) {
			e.update(input);
		}
	}

	public void setBlock(int x, int y, Block block) {
		if (x < 0 || y < 0 || x < width || y > height)
			blocks[x + y * width] = Block.brickWall;
		else
			blocks[x + y * width] = block;
	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Block.brickWall;
		return blocks[x + y * width];
	}

	public double getFloorPos() {
		return 8;
	}

	public double getCeilPos() {
		return ceilPos;
	}

	public Render getFloorTexture() {
		return floorTex;
	}

	public Render getCeilTexture() {
		return ceilTex;
	}

	public int getRenderDistance() {
		return renderDistance;
	}

	public ArrayList<Entity> getEntites() {
		return entities;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}

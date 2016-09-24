package net.keabotstudios.dr2.game.level;

import java.util.ArrayList;
import java.util.Arrays;

import net.keabotstudios.dr2.game.GameSettings;
import net.keabotstudios.dr2.game.level.block.Block;
import net.keabotstudios.dr2.game.level.entity.Entity;
import net.keabotstudios.dr2.game.level.entity.Player;
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
		blocks[10 + 10 * width] = Block.brickWall;
		player = new Player(0, 0, 0, "Player", settings);
		floorTex = Texture.brick1Floor;
		ceilTex = Texture.brick1;
		ceilPos = 64;
	}

	public Player getPlayer() {
		return player;
	}

	public void update(Input input) {
		player.update(input);
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

}

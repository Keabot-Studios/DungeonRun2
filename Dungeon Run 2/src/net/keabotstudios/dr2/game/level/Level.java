package net.keabotstudios.dr2.game.level;

import java.util.ArrayList;
import java.util.Arrays;

import net.keabotstudios.dr2.game.GameSettings;
import net.keabotstudios.dr2.game.level.object.block.AnimatedBlock;
import net.keabotstudios.dr2.game.level.object.block.Block;
import net.keabotstudios.dr2.game.level.object.block.EmptyBlock;
import net.keabotstudios.dr2.game.level.object.block.SolidBlock;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.game.level.object.entity.Player;
import net.keabotstudios.dr2.game.level.object.entity.TestEntity;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.superin.Input;

public class Level {

	private double ceilPos;
	private Bitmap floorTex, ceilTex;
	private int renderDistance = 5000;

	private final int width, height;
	private Block[] blocks;

	private Player player;

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	private LevelViewer viewer;

	public Level(int width, int height, GameSettings settings) {
		this.width = width;
		this.height = height;
		this.blocks = new Block[width * height];
		player = new Player(5, 5, 0, "Player", settings);
		Arrays.fill(blocks, new EmptyBlock());
		
		blocks[(width / 2 - 1) + (height / 2 - 1) * width] = new AnimatedBlock();
		entities.add(new TestEntity(5, 1, 5, "Test"));
		
		floorTex = Texture.brick1Floor;
		ceilTex = Texture.brick1;
		ceilPos = 8;

		if (settings.debugMode) {
			viewer = new LevelViewer(this);
			viewer.update();
		}
	}

	public Player getPlayer() {
		return player;
	}

	double lpx = 0, lpz = 0, lpr = 9;

	public void update(Input input) {
		player.update(input, this);
		for (Entity e : entities) {
			e.update(input, this);
		}
		if (viewer != null) {
			if (lpx != player.getPos().getX() || lpz != player.getPos().getZ() || lpr != player.getRotation()) {
				viewer.update();
				lpx = player.getPos().getX();
				lpz = player.getPos().getZ();
				lpr = player.getRotation();
			}
		}
	}

	public void setBlock(int x, int y, Block block) {
		if (x < 0 || y < 0 || x < width || y > height)
			blocks[x + y * width] = new SolidBlock(Texture.brick1);
		else
			blocks[x + y * width] = block;
	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return new SolidBlock(Texture.brick1);
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

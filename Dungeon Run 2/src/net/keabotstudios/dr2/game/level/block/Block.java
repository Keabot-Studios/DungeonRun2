package net.keabotstudios.dr2.game.level.block;

import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public abstract class Block {
	
	public final int id;
	public final boolean solid, opaque;
	
	public static Block empty;
	public static Block brickWall;
	public static Block animTest;
	
	public Block(int id, boolean solid, boolean opaque) {
		this.id = id;
		this.solid = solid;
		this.opaque = opaque;
	}
	
	public static void init() {
		empty = new EmptyBlock(0);
		brickWall = new SolidBlock(1, Texture.brick1);
		animTest = new AnimatedBlock(2);
	}
	
	public abstract Render getTexture(int side, int y);

}

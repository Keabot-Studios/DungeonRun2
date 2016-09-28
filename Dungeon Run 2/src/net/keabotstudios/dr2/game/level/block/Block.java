package net.keabotstudios.dr2.game.level.block;

import net.keabotstudios.dr2.game.object.CollisionBox;
import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public abstract class Block {

	protected final int id, minimapColor;
	protected final boolean solid, opaque;

	protected CollisionBox collisionBox;

	public static Block empty;
	public static Block brickWall;
	public static Block animTest;

	public Block(int id, boolean solid, boolean opaque, int minimapColor) {
		this.id = id;
		this.solid = solid;
		this.opaque = opaque;
		this.minimapColor = minimapColor;
		collisionBox = new CollisionBox(1, 1, 1);
	}

	public Block(int id, boolean solid, boolean opaque, int minimapColor, CollisionBox colBox) {
		this(id, solid, opaque, minimapColor);
		collisionBox = colBox;
	}

	public static void init() {
		empty = new EmptyBlock(0);
		brickWall = new SolidBlock(1, Texture.brick1);
		animTest = new AnimatedBlock(2);
	}

	public int getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public int minimapColor() {
		return minimapColor;
	}

	public boolean isOpaque() {
		return opaque;
	}

	public abstract Render getTexture(int side, int y);

}

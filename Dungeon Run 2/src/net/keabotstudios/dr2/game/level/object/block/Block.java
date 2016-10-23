package net.keabotstudios.dr2.game.level.object.block;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.dr2.math.Vector3;

public abstract class Block {

	protected final int id, minimapColor;
	protected final boolean solid, opaque;
	protected final double colOffsetX, colOffsetZ;

	protected CollisionBox collisionBox;

	public static Block[] blocks = new Block[4];

	public static Block emptyBlock;
	public static Block barrierBlock;
	public static Block wallBlock;
	public static Block animBlock;
	
	public static void load() {
		emptyBlock = new EmptyBlock(0, false);
		barrierBlock = new EmptyBlock(1, false);
		wallBlock = new SolidBlock(2, Texture.brick);
		animBlock = new SolidBlock(3, Texture.animTest);
	}

	protected Block(int id, boolean solid, boolean opaque, int minimapColor) {
		this(id, solid, opaque, minimapColor, new CollisionBox(1, 1, Double.MAX_VALUE), 0.0, 0.0);
	}

	protected Block(int id, boolean solid, boolean opaque, int minimapColor, CollisionBox colBox, double colOffsetX, double colOffsetZ) {
		this.id = id;
		this.solid = solid;
		this.opaque = opaque;
		this.minimapColor = minimapColor;
		this.colOffsetX = colOffsetX;
		this.colOffsetZ = colOffsetZ;
		this.collisionBox = colBox;
		if (id < blocks.length || id >= 0) {
			if (blocks[id] == null) {
				blocks[id] = this;
			} else {
				System.err.println("Block already exists: " + id);
				System.exit(-1);
			}
		} else {
			System.err.println("Block id is not in range: " + id);
			System.exit(-1);
		}
	}

	public int getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public int getMinimapColor() {
		return minimapColor;
	}

	public boolean isOpaque() {
		return opaque;
	}

	public abstract Bitmap getTexture(int side, int y);

	public boolean collides(int wallX, int wallZ, Entity e, double dx, double dy) {
		return CollisionBox.collides(collisionBox, new Vector3(wallX + colOffsetX, 0, wallZ + colOffsetZ), e.getCollisionBox(), e.getPos());
	}

}

package net.keabotstudios.dr2.game.level.object.block;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.math.Vector3;

public abstract class Block {

	protected final int id, minimapColor;
	protected final boolean solid, opaque;
	protected double colOffsetX, colOffsetZ;

	protected CollisionBox collisionBox;

	protected Block(int id, boolean solid, boolean opaque, int minimapColor) {
		this.id = id;
		this.solid = solid;
		this.opaque = opaque;
		this.minimapColor = minimapColor;
		this.colOffsetX = 0;
		this.colOffsetZ = 0;
		this.collisionBox = new CollisionBox(1, 1, Double.MAX_VALUE);
	}

	protected Block(int id, boolean solid, boolean opaque, int minimapColor, CollisionBox colBox, double colOffsetX, double colOffsetZ) {
		this(id, solid, opaque, minimapColor);
		this.colOffsetX = colOffsetX;
		this.colOffsetZ = colOffsetZ;
		this.collisionBox = colBox;
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

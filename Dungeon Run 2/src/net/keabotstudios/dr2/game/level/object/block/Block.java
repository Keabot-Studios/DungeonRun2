package net.keabotstudios.dr2.game.level.object.block;

import java.awt.Rectangle;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.gfx.Render;

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
		this.collisionBox = new CollisionBox(1, 1, 0);
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

	public abstract Render getTexture(int side, int y);
	
	public boolean intersectsWith(int wallX, int wallZ, Entity e) {
		Rectangle block = new Rectangle(wallX * 8, wallZ * 8, (int) (collisionBox.getWidth() * 8.0), (int) (collisionBox.getLength() * 8.0));
		Rectangle entity = new Rectangle((int) (e.getPos().getX() * 8.0), (int) (e.getPos().getZ() * 8.0), (int) (e.getCollisionBox().getWidth() * 8.0), (int) (e.getCollisionBox().getLength() * 8.0));
		return block.intersects(entity);
	}

}

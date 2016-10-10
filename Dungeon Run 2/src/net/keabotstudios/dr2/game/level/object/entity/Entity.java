package net.keabotstudios.dr2.game.level.object.entity;

import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.math.Vector3;
import net.keabotstudios.superin.Input;

public abstract class Entity {

	protected double rot, dx, dy, dz, dRot;

	protected Vector3 pos;
	protected CollisionBox collisionBox;

	protected Bitmap texture;
	protected int minimapColor;

	protected Entity(Vector3 pos, CollisionBox colBox, double rot, int minimapColor) {
		this.pos = pos;
		this.collisionBox = colBox;
		this.rot = rot;
		this.minimapColor = minimapColor;
	}

	public Vector3 getPos() {
		return pos;
	}

	public CollisionBox getCollisionBox() {
		return collisionBox;
	}

	public double getRotation() {
		return rot;
	}

	public int getMinimapColor() {
		return minimapColor;
	}

	public void setPosition(double x, double y, double z) {
		pos = new Vector3(x, y, z);
	}

	public void setPosition(Vector3 pos) {
		this.pos = pos;
	}

	public void setCollisionBox(CollisionBox box) {
		this.collisionBox = box;
	}

	public void setRotation(double rot) {
		this.rot = rot;
	}

	public void update(Input input, Level level) {
		if (rot > (Math.PI * 2.0)) {
			rot = 0;
		} else if (rot < 0) {
			rot = (Math.PI * 2.0);
		}
	}

	public boolean intersectsWith(Entity e) {
		double left = getPos().getX();
		double bottom = getPos().getY();
		double front = getPos().getZ();
		double right = left + getCollisionBox().getX();
		double top = bottom + getCollisionBox().getY();
		double back = front + getCollisionBox().getZ();

		double eLeft = e.getPos().getX();
		double eBottom = e.getPos().getY();
		double eFront = e.getPos().getZ();
		double eRight = eLeft + e.getCollisionBox().getX();
		double eTop = eBottom + e.getCollisionBox().getY();
		double eBack = eFront + e.getCollisionBox().getZ();

		return !(left < eRight) && !(right > eLeft) && !(top < eBottom) && !(bottom > eTop) && !(front < eBack) && !(back > eFront);
	}

	public abstract Bitmap getTexture();

}

package net.keabotstudios.dr2.game.level.object.entity;

import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.game.level.object.Position3D;
import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.superin.Input;

public abstract class Entity {

	protected double rot, dx, dy, dz, dRot;

	protected Position3D pos;
	protected CollisionBox collisionBox;

	protected String name;
	protected Render texture;
	protected int minimapColor;

	protected Entity(Position3D pos, CollisionBox colBox, double rot, String name, int minimapColor) {
		super();
		this.pos = pos;
		this.collisionBox = colBox;
		this.rot = rot;
		this.name = name;
		this.minimapColor = minimapColor;
	}

	public String getName() {
		return name;
	}

	public Position3D getPos() {
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
		pos = new Position3D(x, y, z);
	}

	public void setPosition(Position3D pos) {
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
		double right = left + getCollisionBox().getWidth();
		double top = bottom + getCollisionBox().getHeight();
		double back = front + getCollisionBox().getLength();

		double eLeft = e.getPos().getX();
		double eBottom = e.getPos().getY();
		double eFront = e.getPos().getZ();
		double eRight = eLeft + e.getCollisionBox().getWidth();
		double eTop = eBottom + e.getCollisionBox().getHeight();
		double eBack = eFront + e.getCollisionBox().getLength();

		return !(left < eRight) && !(right > eLeft) && !(top < eBottom) && !(bottom > eTop) && !(front < eBack) && !(back > eFront);
	}

	public abstract Render getTexture();

}

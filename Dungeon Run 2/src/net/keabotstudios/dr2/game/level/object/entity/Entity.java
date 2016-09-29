package net.keabotstudios.dr2.game.level.object.entity;

import net.keabotstudios.dr2.game.evel.object.CollisionBox;
import net.keabotstudios.dr2.game.evel.object.Position3D;
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

	public void update(Input input) {
		if (rot > (Math.PI * 2.0)) {
			rot = 0;
		} else if (rot < 0) {
			rot = (Math.PI * 2.0);
		}
	}

	public abstract Render getTexture();

}

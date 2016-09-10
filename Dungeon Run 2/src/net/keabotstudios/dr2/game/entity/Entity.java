package net.keabotstudios.dr2.game.entity;

import net.keabotstudios.dr2.gfx.Render;

public class Entity {
	
	protected double x, y, z, rot, dx, dy, dz, dRot;
	protected String name;
	protected Render texture;
	
	protected Entity(double x, double y, double z, double rot, String name) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.rot = rot;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public double getRotation() {
		return rot;
	}
	
	public Render getTexture() {
		return texture;
	}

}

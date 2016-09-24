package net.keabotstudios.dr2.game.level.entity;

import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.superin.Input;

public abstract class Entity {
	
	protected double x, y, z, rot, dx, dy, dz, dRot;
	protected String name;
	protected Render texture;
	protected int minimapColor;
	
	protected Entity(double x, double y, double z, double rot, String name, int minimapColor) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.rot = rot;
		this.name = name;
		this.minimapColor = minimapColor;
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
	
	public int getMinimapColor() {
		return minimapColor;
	}
	
	public void setPosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setRotation(double rot) {
		this.rot = rot;
	}
	
	public void update(Input input) {
		if (rot > (Math.PI * 2.0)) {
			rot = 0;
		} else if(rot < 0) {
			rot = (Math.PI * 2.0);
		}
	}
	
	public abstract Render getTexture();

}

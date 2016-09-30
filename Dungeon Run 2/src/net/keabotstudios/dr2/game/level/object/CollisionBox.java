package net.keabotstudios.dr2.game.level.object;

import java.awt.Rectangle;

public class CollisionBox {
	private double length, width, height;

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setLength(double val) {
		length = val;
	}

	public void setWidth(double val) {
		width = val;
	}

	public void setHeight(double val) {
		height = val;
	}
	
	public CollisionBox(double length, double width, double height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}
	

	
	public static boolean collides(CollisionBox boxA, Position3D boxAPos, CollisionBox boxB, Position3D boxBPos) {
		double aLeft = boxAPos.getX();
		double aRight = aLeft + boxA.getWidth();
		double aFront = boxAPos.getZ();
		double aBack = aFront + boxA.getLength();
		double aBottom = boxAPos.getY();
		double aTop = aBottom + boxA.getHeight();
		double bLeft = boxBPos.getX();
		double bRight = bLeft + boxB.getWidth();
		double bFront = boxBPos.getZ();
		double bBack = bFront + boxB.getLength();
		double bBottom = boxBPos.getY();
		double bTop = bBottom + boxB.getHeight();
		if(aLeft < bRight) return false;
		if(aRight > bLeft) return false;
		if(aTop < bBottom) return false;
		if(aBottom > bTop) return false;
		if(aBack < bFront) return false;
		if(aFront > bBack) return false;
		return true;
	}
}

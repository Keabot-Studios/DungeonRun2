package net.keabotstudios.dr2.game.object;

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
	
	public CollisionBox(double length, double width, double height)
	{
		this.length = length;
		this.width = width;
		this.height = height;
	}
}

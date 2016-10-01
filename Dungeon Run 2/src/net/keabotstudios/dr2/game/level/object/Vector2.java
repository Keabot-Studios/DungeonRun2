package net.keabotstudios.dr2.game.level.object;

public class Vector2 {
	private double x, y;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double val) {
		x = val;
	}

	public void setY(double val) {
		y = val;
	}
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}

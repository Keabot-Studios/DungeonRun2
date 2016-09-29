package net.keabotstudios.dr2.game.evel.object;

public class Position3D {
	private double x, y, z;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double val) {
		x = val;
	}

	public void setY(double val) {
		y = val;
	}

	public void setZ(double val) {
		z = val;
	}
	
	public Position3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}

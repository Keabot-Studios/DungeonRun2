package net.keabotstudios.dr2.game.level.object;

public class Vector3 {
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

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Vector3.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Vector3 other = (Vector3) obj;
		if (this.x != other.getX() || this.y != other.getY() || this.z != other.getZ()) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + Double.valueOf(x).hashCode();
		hash = 31 * hash + Double.valueOf(y).hashCode();
		hash = 31 * hash + Double.valueOf(z).hashCode();
		return hash;
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}

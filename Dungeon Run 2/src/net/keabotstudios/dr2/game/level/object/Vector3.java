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

	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	public Vector3 subtract(Vector3 other) {
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	public Vector3 multiply(Vector3 other) {
		return new Vector3(this.x * other.x, this.y * other.y, this.z * other.z);
	}

	public Vector3 multiply(double scale) {
		return new Vector3(this.x * scale, this.y * scale, this.z * scale);
	}

	public Vector3 divide(Vector3 other) {
		return new Vector3(this.x / other.x, this.y / other.y, this.z / other.z);
	}

	public Vector3 divide(double scale) {
		return new Vector3(this.x / scale, this.y / scale, this.z / scale);
	}

	public Vector3 inverse() {
		return new Vector3(-this.x, -this.y, -this.z);
	}

	public double distance(Vector3 other) {
		return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2));
	}
	
	public Vector3 clone() {
		return new Vector3(x, y, z);
	}
}

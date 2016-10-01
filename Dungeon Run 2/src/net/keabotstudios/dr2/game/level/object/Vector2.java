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

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Vector2.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Vector2 other = (Vector2) obj;
		if (this.x != other.getX() || this.y != other.getY()) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + Double.valueOf(x).hashCode();
		hash = 31 * hash + Double.valueOf(y).hashCode();
		return hash;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

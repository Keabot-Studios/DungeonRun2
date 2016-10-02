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
	
	public Vector2 add(Vector2 other)
	{
		return new Vector2(this.x + other.x, this.y + other.y);
	}
	
	public Vector2 subtract(Vector2 other)
	{
		return new Vector2(this.x - other.x, this.y - other.y);
	}
	
	public Vector2 multiply(Vector2 other)
	{
		return new Vector2(this.x * other.x, this.y * other.y);
	}
	
	public Vector2 multiply(double scale)
	{
		return new Vector2(this.x * scale, this.y * scale);
	}
	
	public Vector2 divide(Vector2 other)
	{
		return new Vector2(this.x / other.x, this.y / other.y);
	}
	
	public Vector2 divide(double scale)
	{
		return new Vector2(this.x / scale, this.y / scale);
	}
	
	public Vector2 inverse()
	{
		return new Vector2(-this.x, -this.y);
	}
	
	public double distance(Vector2 other)
	{
		return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
	}
}

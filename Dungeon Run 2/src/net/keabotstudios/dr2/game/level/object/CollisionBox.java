package net.keabotstudios.dr2.game.level.object;

public class CollisionBox extends Vector3 {

	public CollisionBox(double x, double y, double z) {
		super(x, y, z);
	}

	public static boolean collides(CollisionBox boxA, Vector3 boxAPos, CollisionBox boxB, Vector3 boxBPos) {
		double aLeft = boxAPos.getX();
		double aRight = aLeft + boxA.getX();
		double aFront = boxAPos.getZ();
		double aBack = aFront + boxA.getZ();
		double aBottom = boxAPos.getY();
		double aTop = aBottom + boxA.getY();
		double bLeft = boxBPos.getX();
		double bRight = bLeft + boxB.getX();
		double bFront = boxBPos.getZ();
		double bBack = bFront + boxB.getZ();
		double bBottom = boxBPos.getY();
		double bTop = bBottom + boxB.getY();
		if (aLeft < bRight)
			return false;
		if (aRight > bLeft)
			return false;
		if (aTop < bBottom)
			return false;
		if (aBottom > bTop)
			return false;
		if (aBack < bFront)
			return false;
		if (aFront > bBack)
			return false;
		return true;
	}
}

package net.keabotstudios.dr2.gfx;

import net.keabotstudios.dr2.math.Vector2;

public class BoxBitmap extends Bitmap {

	protected int offL, offR, offT, offB;

	public BoxBitmap(int w, int h, int offLeft, int offRight, int offTop, int offBottom) {
		super(w, h);
		offL = offLeft;
		offR = offRight;
		offT = offTop;
		offB = offBottom;
	}

	public Vector2 getBitmapPosFromBox(int width, int height, int curX, int curY) {
		Vector2 pos = Vector2.zero();
		if (curX < offL)
			pos.setX(curX);
		else if (curX >= offL && curX < width - offR) {
			float p = (float) (curX - offL) / (float) (width - offR);
			int w = (int) ((float) (this.width - offR - offL) * p);
			pos.setX(offL + w);
		} else if (curX >= width - offR) {
			pos.setX(this.width - (width - curX));
		}
		if (curY < offT)
			pos.setY(curY);
		else if (curY >= offT && curY < height - offB) {
			float p = (float) (curY - offT) / (float) (height - offB);
			int h = (int) ((float) (this.height - offB - offT) * p);
			pos.setY(offT + h);
		} else if (curY >= height - offB) {
			pos.setY(this.height - (height - curY));
		}
		return pos;
	}
}

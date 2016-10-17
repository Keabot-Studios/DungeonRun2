package net.keabotstudios.dr2.gfx;

import net.keabotstudios.dr2.Util.ColorUtil;
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

	public void renderBox(Bitmap bitmap, int xOffs, int yOffs, int width, int height, float alpha) {

		if (width > this.width || height > this.height) {
			super.render(bitmap, xOffs, yOffs, alpha);
			return;
		}
		for (int y = 0; y < bitmap.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < bitmap.width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width)
					continue;

				Vector2 pos = getBitmapPosFromBox(width, height, x, y);
				int color = bitmap.pixels[(int)pos.getX() + (int)pos.getY() * bitmap.width];
				if (ColorUtil.alpha(color) > 0)
					pixels[xPix + yPix * width] = color;
			}
		}
	}

	public Vector2 getBitmapPosFromBox(int width, int height, int curX, int curY) {
		Vector2 pos = Vector2.zero();
		if (curX < offL)
			pos.setX(curX);
		else if (curX >= offL && curX < width - offR)
			pos.setX((int) ((float) curX / (float) width * this.width));
		else if (curX >= width - offR) {
			pos.setX(this.width - (width - curX));
		}
		if (curY < offT)
			pos.setY(curY);
		else if (curY >= offT && curY < height - offB)
			pos.setY((int) ((float) curY / (float) height * this.height));
		else if (curY >= height - offB) {
			pos.setY(this.height - (height - curY));
		}
		return pos;
	}
}

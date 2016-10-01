package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.Util;

public class Bitmap {
	public final int width, height;
	public final int[] pixels;

	public Bitmap(int w, int h) {
		width = w;
		height = h;
		pixels = new int[w * h];
	}

	public void clear(Color col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = col.getRGB();
		}
	}

	/**
	 * Draws a Bitmap to this Bitmap object.
	 * 
	 * @param bitmap
	 *            the Bitmap object to draw.
	 * @param xOffs
	 *            the x offset to draw the Render at.
	 * @param yOffs
	 *            the y offset to draw the Render at.
	 */
	public void render(Bitmap bitmap, int xOffs, int yOffs) {
		for (int y = 0; y < bitmap.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < bitmap.width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width)
					continue;
				int color = bitmap.pixels[x + y * bitmap.width];
				if (color > 0xFF000000)
					pixels[xPix + yPix * width] = color;
			}
		}
	}

	/**
	 * Draws a Bitmap to this Bitmap object.
	 * 
	 * @param bitmap
	 *            the Bitmap object to draw.
	 * @param xOffs
	 *            the x offset to draw the Bitmap at.
	 * @param yOffs
	 *            the y offset to draw the Bitmap at.
	 * @param alpha
	 * 			  the alpha value to draw the Bitmap at.
	 */
	public void render(Bitmap bitmap, int xOffs, int yOffs, float alpha) {
		if (alpha >= 1.0f) {
			render(bitmap, xOffs, yOffs);
		} else if (alpha <= 0.0f)
			return;
		for (int y = 0; y < bitmap.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < bitmap.width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width)
					continue;
				int color = bitmap.pixels[x + y * bitmap.width];
				int currentColor = pixels[xPix + yPix * width];
				if (color > 0xFF000000) {
					pixels[xPix + yPix * width] = Util.overlayAlpha(color, currentColor, alpha);
				}
			}
		}
	}
	
	public Bitmap getSubBitmap(int x, int y, int width, int height) {
		if(x + width > this.width || x < 0) return null;
		if(y + height > this.height || y < 0) return null;
		Bitmap result = new Bitmap(width, height);
		for(int px = 0; px < width; px++) {
			for(int py = 0; py < height; py++) {
				result.pixels[px + py * width] = pixels[(px + x) + (py + y) * this.width];
			}
		}
		return result;
	}

	public int getAverageColor() {
		float redBucket = 0;
		float greenBucket = 0;
		float blueBucket = 0;
		long pixelCount = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color c = new Color(pixels[x + y * width]);
				pixelCount++;
				redBucket += c.getRed() / 255.0f;
				greenBucket += c.getGreen() / 255.0f;
				blueBucket += c.getBlue() / 255.0f;
			}
		}

		return new Color(redBucket / pixelCount, greenBucket / pixelCount, blueBucket / pixelCount).getRGB();
	}

}

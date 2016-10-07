package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.Util.ColorUtil;

public class Bitmap {
	protected int width, height;
	protected int[] pixels;

	public Bitmap(int w, int h) {
		width = w;
		height = h;
		pixels = new int[w * h];
	}

	/**
	 * Sets the entire Bitmap to one color.
	 * 
	 * @param col
	 *            the color to set this Bitmap to.
	 */
	public void clear(Color col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = ColorUtil.toARGBColor(col.getRGB());
		}
	}

	/**
	 * Sets the entire Bitmap to one color.
	 * 
	 * @param col
	 *            The ARGB color to set this Bitmap to.
	 */
	public void clear(int col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = col;
		}
	}

	/**
	 * Draws a Bitmap to this Bitmap object.
	 * 
	 * @param bitmap
	 *            The Bitmap object to draw.
	 * @param xOffs
	 *            The x offset to draw the Render at.
	 * @param yOffs
	 *            The y offset to draw the Render at.
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
				if (ColorUtil.alpha(color) > 0)
					pixels[xPix + yPix * width] = color;
			}
		}
	}

	/**
	 * Draws a Bitmap to this Bitmap object.
	 * 
	 * @param bitmap
	 *            The Bitmap object to draw.
	 * @param xOffs
	 *            The x offset to draw the Bitmap at.
	 * @param yOffs
	 *            The y offset to draw the Bitmap at.
	 * @param scale
	 *            The scale to draw the Bitmap at.
	 */
	public void render(Bitmap bitmap, int xOffs, int yOffs, int scale) {
		if (scale < 1)
			return;
		else if (scale == 1) {
			render(bitmap, xOffs, yOffs);
			return;
		}
		for (int y = 0; y < bitmap.height * scale; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < bitmap.width * scale; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width)
					continue;
				int color = bitmap.pixels[(x / scale) + (y / scale) * bitmap.width];
				if (ColorUtil.alpha(color) > 0)
					pixels[xPix + yPix * width] = color;
			}
		}
	}

	/**
	 * Draws a Bitmap to this Bitmap object.
	 * 
	 * @param bitmap
	 *            The Bitmap object to draw.
	 * @param xOffs
	 *            The x offset to draw the Bitmap at.
	 * @param yOffs
	 *            The y offset to draw the Bitmap at.
	 * @param alpha
	 *            The alpha value to draw the Bitmap at.
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
				if (ColorUtil.alpha(color) > 0)
					pixels[xPix + yPix * width] = ColorUtil.overlayAlpha(color, currentColor, alpha);
			}
		}
	}

	/**
	 * Draws a Bitmap to this Bitmap object.
	 * 
	 * @param bitmap
	 *            The Bitmap object to draw.
	 * @param xOffs
	 *            The x offset to draw the Bitmap at.
	 * @param yOffs
	 *            The y offset to draw the Bitmap at.
	 * @param scale
	 *            The scale to draw the Bitmap at.
	 * @param alpha
	 *            The alpha value to draw the Bitmap at.
	 */
	public void render(Bitmap bitmap, int xOffs, int yOffs, int scale, float alpha) {
		if (scale < 1)
			return;
		else if (scale == 1) {
			render(bitmap, xOffs, yOffs, alpha);
			return;
		}
		if (alpha >= 1.0f) {
			render(bitmap, xOffs, yOffs);
		} else if (alpha <= 0.0f)
			return;
		for (int y = 0; y < bitmap.height * scale; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < bitmap.width * scale; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width)
					continue;
				int color = bitmap.pixels[(x / scale) + (y / scale) * bitmap.width];
				int currentColor = pixels[xPix + yPix * width];
				if (ColorUtil.alpha(color) > 0)
					pixels[xPix + yPix * width] = ColorUtil.overlayAlpha(color, currentColor, alpha);
			}
		}
	}

	/**
	 * Gets a Bitmap with the pixels of this Bitmap object.
	 * 
	 * @param x
	 *            the x of the resulting Bitmap on this Bitmap.
	 * @param y
	 *            the y of the resulting Bitmap on this Bitmap.
	 * @param width
	 *            the width of the resulting Bitmap.
	 * @param height
	 *            the height of the resulting Bitmap.
	 * 
	 * @return The Bitmap with the pixels of this Bitmap object.
	 */
	public Bitmap getSubBitmap(int x, int y, int width, int height) {
		Bitmap result = new Bitmap(width, height);
		for (int px = 0; px < width; px++) {
			for (int py = 0; py < height; py++) {
				if ((px + x) >= this.width || (px + x) < 0 || (py + y) >= this.height || (py + y) < 0)
					result.pixels[px + py * width] = ColorUtil.makeARGBColor(0, 0, 0, 0);
				else
					result.pixels[px + py * width] = pixels[(px + x) + (py + y) * this.width];
			}
		}
		return result;
	}

	/**
	 * Gets the average color of all pixels on this Bitmap.
	 * 
	 * @return The average color of all pixels on this Bitmap.
	 */
	public int getAverageColor() {
		float redBucket = 0;
		float greenBucket = 0;
		float blueBucket = 0;
		float alphaBucket = 0;
		long pixelCount = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int c = pixels[x + y * width];
				pixelCount++;
				alphaBucket += ColorUtil.alpha(c) / 255.0f; 
				redBucket += ColorUtil.red(c) / 255.0f;
				greenBucket += ColorUtil.green(c) / 255.0f;
				blueBucket += ColorUtil.blue(c) / 255.0f;
			}
		}

		return new Color(redBucket / pixelCount, greenBucket / pixelCount, blueBucket / pixelCount, alphaBucket / pixelCount).getRGB();
	}

	/**
	 * Creates a new Bitmap with targetColor replaced by color.
	 * 
	 * @param targetColor
	 *            The color to change.
	 * @param color
	 *            The color to change targetColor to.
	 * 
	 * @return The color replaced Bitmap.
	 */
	public Bitmap replaceColor(int targetColor, int color) {
		Bitmap out = this.clone();
		for (int i = 0; i < out.pixels.length; i++) {
			if (out.pixels[i] == targetColor)
				out.pixels[i] = color;
		}
		System.out.println("----------");
		return out;
	}

	/**
	 * Creates a new Bitmap with a rotation of rot.
	 * 
	 * @param rot
	 *            The radian to rotate the Bitmap by.
	 * 
	 * @return The rotated Bitmap.
	 */
	public Bitmap rotate(double rot) {
		Bitmap out = new Bitmap(width, height);
		if (rot == 0.0)
			return this.clone();
		double cos = Math.cos(-rot);
		double sin = Math.sin(-rot);
		int xCenter = width / 2;
		int yCenter = height / 2;
		for (int a = 0, y = 0; y < height; y++) {
			for (int x = 0; x < width; x++, a++) {
				int xp = x - xCenter;
				int yp = y - yCenter;
				int xx = (int) Math.round((xp * cos) - (yp * sin));
				int yy = (int) Math.round((xp * sin) + (yp * cos));
				xp = xx + xCenter;
				yp = yy + yCenter;
				if ((xp >= 0) && (xp < width) && (yp >= 0) && (yp < height))
					out.pixels[a] = pixels[xp + yp * width];
				else
					out.pixels[a] = ColorUtil.makeARGBColor(0, 0, 0, 0);
			}
		}
		return out;
	}

	/**
	 * Draws a rectangle outline to this Bitmap.
	 * @param x The x of the rectangle.
	 * @param y The y of the rectangle.
	 * @param width
	 * @param height
	 * @param color
	 */
	public void drawRect(int x, int y, int width, int height, int color) {
		for (int yPix = 0; yPix < height; yPix++) {
			if (yPix < 0 || yPix >= this.height)
				continue;
			for (int xPix = 0; x < width; xPix++) {
				if (xPix < 0 || xPix >= this.width)
					continue;
				if ((xPix == 0 || xPix == width - 1) && (yPix == 0 || yPix == height - 1)) {
					pixels[xPix + yPix * this.width] = color;
				}
			}
		}
	}

	public void fillRect(int x, int y, int width, int height, int color) {
		for (int yPix = y; yPix < y + height; yPix++) {
			if (yPix < 0 || yPix >= this.height)
				continue;
			for (int xPix = x; xPix < x + width; xPix++) {
				if (xPix < 0 || xPix >= this.width)
					continue;
				pixels[xPix + yPix * this.width] = color;
			}
		}
	}
	
	public void fillRect(int x, int y, int width, int height, int color, float alpha) {
		for (int yPix = y; yPix < y + height; yPix++) {
			if (yPix < 0 || yPix >= this.height)
				continue;
			for (int xPix = x; xPix < x + width; xPix++) {
				if (xPix < 0 || xPix >= this.width)
					continue;
				int currentColor = pixels[xPix + yPix * width];
				pixels[xPix + yPix * this.width] = ColorUtil.overlayAlpha(color, currentColor, alpha);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public Bitmap clone() {
		Bitmap out = new Bitmap(width, height);
		out.pixels = pixels.clone();
		return out;
	}
}

package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.math.Vector2;

public class Bitmap {
	protected int width, height;
	protected int[] pixels;

	public Bitmap(int w, int h) {
		width = w;
		height = h;
		pixels = new int[w * h];
	}

	/**
	 * Sets the entire {@code Bitmap} to one color.
	 * 
	 * @param col
	 *            the color to set this {@code Bitmap} to.
	 */
	public void clear(Color col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = ColorUtil.toARGBColor(col.getRGB());
		}
	}

	/**
	 * Sets the entire {@code Bitmap} to one color.
	 * 
	 * @param col
	 *            The ARGB color to set this {@code Bitmap} to.
	 */
	public void clear(int col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = col;
		}
	}

	/**
	 * Draws a Bitmap to this {@code Bitmap} object.
	 * 
	 * @param bitmap
	 *            The {@code Bitmap} object to draw.
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
				setPixel(xPix, yPix, bitmap.getPixel(x, y));
			}
		}
	}

	/**
	 * Draws a Bitmap to this {@code Bitmap} object.
	 * 
	 * @param bitmap
	 *            The {@code Bitmap} object to draw.
	 * @param xOffs
	 *            The x offset to draw the {@code Bitmap} at.
	 * @param yOffs
	 *            The y offset to draw the {@code Bitmap} at.
	 * @param scale
	 *            The scale to draw the {@code Bitmap} at.
	 */
	public void render(Bitmap bitmap, int xOffs, int yOffs, float scale) {
		if (scale < 0)
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
				setPixel(xPix, yPix, bitmap.getPixel((int) (x / scale), (int) (y / scale)));
			}
		}
	}

	public void renderBox(Bitmap bitmap, int xOffs, int yOffs, int width, int height) {
		if (width > this.width || height > this.height) {
			render(bitmap, xOffs, yOffs);
			return;
		}
		for (int y = 0; y < height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= this.height)
				continue;
			for (int x = 0; x < width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= this.width)
					continue;

				Vector2 pos = bitmap.getBitmapPosFromBox(width, height, x, y);
				int color = bitmap.pixels[(int)pos.getX() + (int)pos.getY() * bitmap.width];
				if (ColorUtil.alpha(color) > 0)
					pixels[xPix + yPix * this.width] = color;
			}
		}
	}

	public Bitmap transparentize(int alpha) {
		Bitmap out = new Bitmap(width, height);
		if (alpha == 0)
			return out;
		for (int i = 0; i < pixels.length; i++) {
			out.setPixel(i % width, i / width, ColorUtil.makeARGBColor(alpha, ColorUtil.red(pixels[i]),
					ColorUtil.green(pixels[i]), ColorUtil.blue(pixels[i])));
		}
		return out;
	}

	/**
	 * Gets a {@code Bitmap} with the pixels of this {@code Bitmap} object.
	 * 
	 * @param x
	 *            the x of the resulting {@code Bitmap} on this {@code Bitmap}.
	 * @param y
	 *            the y of the resulting {@code Bitmap} on this {@code Bitmap}.
	 * @param width
	 *            the width of the resulting {@code Bitmap}.
	 * @param height
	 *            the height of the resulting {@code Bitmap}.
	 * 
	 * @return The {@code Bitmap} with the pixels of this {@code Bitmap} object.
	 */
	public Bitmap getSubBitmap(int x, int y, int width, int height) {
		Bitmap result = new Bitmap(width, height);
		for (int px = 0; px < width; px++) {
			for (int py = 0; py < height; py++) {
				result.setPixel(px, py, getPixel((px + x), (py + y)));
			}
		}
		return result;
	}
	public Vector2 getBitmapPosFromBox(int width, int height, int curX, int curY) {
		
		return new Vector2(curX, curY);
	}

	/**
	 * Gets the average color of all pixels on this {@code Bitmap}.
	 * 
	 * @return The average color of all pixels on this {@code Bitmap}.
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

		return new Color(redBucket / pixelCount, greenBucket / pixelCount, blueBucket / pixelCount,
				alphaBucket / pixelCount).getRGB();
	}

	/**
	 * Creates a new {@code Bitmap} with targetColor replaced by color.
	 * 
	 * @param targetColor
	 *            The color to change.
	 * @param color
	 *            The color to change targetColor to.
	 * 
	 * @return The color replaced {@code Bitmap}.
	 */
	public Bitmap replaceColor(int targetColor, int color) {
		Bitmap out = this.clone();
		for (int i = 0; i < out.pixels.length; i++) {
			if (out.pixels[i] == targetColor)
				out.pixels[i] = color;
		}
		return out;
	}

	/**
	 * Creates a new {@code Bitmap} with a rotation of rot.
	 * 
	 * @param rot
	 *            The radian to rotate the {@code Bitmap} by.
	 * 
	 * @return The rotated {@code Bitmap}.
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
				out.pixels[a] = getPixel(xp, yp);
			}
		}
		return out;
	}

	/**
	 * Draws a rectangle outline to this {@code Bitmap}.
	 * 
	 * @param x
	 *            The x of the rectangle.
	 * @param y
	 *            The y of the rectangle.
	 * @param width
	 *            The width of the rectangle.
	 * @param height
	 *            The height of the rectangle.
	 * @param color
	 *            The color of the rectangle.
	 */
	public void drawRect(int x, int y, int width, int height, int color) {
		for (int yPix = 0; yPix < height; yPix++) {
			if (yPix < 0 || yPix >= this.height)
				continue;
			for (int xPix = 0; xPix < width; xPix++) {
				if (xPix < 0 || xPix >= this.width)
					continue;
				if (xPix == 0 || xPix == width - 1 || yPix == 0 || yPix == height - 1) {
					setPixel(xPix + x, yPix + y, color);
				}
			}
		}
	}

	/**
	 * Draws a rectangle to this {@code Bitmap}.
	 * 
	 * @param x
	 *            The x of the rectangle.
	 * @param y
	 *            The y of the rectangle.
	 * @param width
	 *            The width of the rectangle.
	 * @param height
	 *            The height of the rectangle.
	 * @param color
	 *            The color of the rectangle.
	 */
	public void fillRect(int x, int y, int width, int height, int color) {
		for (int yPix = y; yPix < y + height; yPix++) {
			if (yPix < 0 || yPix >= this.height)
				continue;
			for (int xPix = x; xPix < x + width; xPix++) {
				if (xPix < 0 || xPix >= this.width)
					continue;
				setPixel(xPix, yPix, color);
			}
		}
	}

	/**
	 * Scales a {@code Bitmap} by the desired scale factors.
	 * 
	 * @param xScale
	 *            The scale factor to scale the width by.
	 * @param yScale
	 *            The scale factor to scale the height by.
	 */
	public Bitmap scale(float xScale, float yScale) {
		if (xScale <= 0 || yScale <= 0)
			return null;
		Bitmap out = new Bitmap((int) (this.width * xScale), (int) (this.height * yScale));
		for (int x = 0; x < out.getWidth(); x++) {
			int xPix = Math.round(x / xScale);
			if (xPix < 0 || xPix >= this.width)
				continue;
			for (int y = 0; y < out.getHeight(); y++) {
				int yPix = Math.round(y / yScale);
				if (yPix < 0 || yPix >= this.height)
					continue;
				out.setPixel(x, y, this.getPixel(xPix, yPix));
			}
		}
		return out;
	}

	/**
	 * Scales a {@code Bitmap} by the desired scale factor.
	 * 
	 * @param scale
	 *            The scale factor to scale the {@code Bitmap} by.
	 */
	public Bitmap scale(float scale) {
		return scale(scale, scale);
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

	public void setPixel(int x, int y, int color) {
		if (x >= width || x < 0 || y >= height || y < 0)
			return;
		if (ColorUtil.alpha(color) <= 0)
			return;
		if (ColorUtil.alpha(color) < 255) {
			int oldColor = getPixel(x, y);
			pixels[x + y * this.width] = ColorUtil.mix(oldColor, color);
		} else {
			pixels[x + y * this.width] = color;
		}
		pixels[x + y * width] = color;
	}

	public int getPixel(int x, int y) {
		if (x >= width || x < 0 || y >= height || y < 0)
			return 0;
		return pixels[x + y * width];
	}
}

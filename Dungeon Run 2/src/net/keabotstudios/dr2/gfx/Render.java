package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.Util;

public class Render {
	public final int width, height;
	public final int[] pixels;

	public Render(int w, int h) {
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
	 * Draws a Render to this Render object.
	 * 
	 * @param render
	 *            the Render object to draw.
	 * @param xOffs
	 *            the x offset to draw the Render at.
	 * @param yOffs
	 *            the y offset to draw the Render at.
	 */
	public void render(Render render, int xOffs, int yOffs) {
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < render.width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width)
					continue;
				int color = render.pixels[x + y * render.width];
				if (color > 0xFF000000)
					pixels[xPix + yPix * width] = color;
			}
		}
	}

	public void render(Render render, int xOffs, int yOffs, float alpha) {
		if(alpha >= 1.0f || alpha < 0.0f) {
			render(render, xOffs, yOffs);
		}
		if(alpha == 0.0f) return;
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < render.width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width)
					continue;
				int color = render.pixels[x + y * render.width];
				int currentColor = pixels[xPix + yPix * width];
				if (color > 0xFF000000) {
					pixels[xPix + yPix * width] = Util.overlayAlpha(color, currentColor, alpha);
				}
			}
		}
	}

	public int getAverageColor() {
		double result = 0;
		for (int i = 0; i < width * height; i++) {
			result = (result + pixels[i]) / 2;
		}
		return (int) result;
	}

}

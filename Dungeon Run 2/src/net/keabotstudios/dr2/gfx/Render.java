package net.keabotstudios.dr2.gfx;

import java.awt.Color;

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
	 * @param render the Render object to draw.
	 * @param xOffs the x offset to draw the Render at.
	 * @param yOffs the y offset to draw the Render at.
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
				if(color > 0xff000000)
					pixels[xPix + yPix * width] = color;
			}
		}
	}

}

package net.keabotstudios.dr2.gfx;

public class Render3D extends Render {

	private double[] zBuffer;
	private double renderDistance = 5000;

	public Render3D(int w, int h) {
		super(w, h);
		zBuffer = new double[w * h];
	}

	/**
	 * Draw a floor and ceiling.
	 * 
	 * @param floorPos
	 *            The floor depth, in relation to 0, the middle of the screen.
	 *            (If <= 0, doesn't render.)
	 * @param ceilPos
	 *            The ceiling height, in relation to 0, the middle of the
	 *            screen. (If <= 0, doesn't render.)
	 * @param yOff
	 *            The player y offset, for moving/jumping/crouching.
	 * @param zOff
	 *            The offset to render floor/ceiling at, on the z axis.
	 * @param xOff
	 *            The offset to render floor/ceiling at, on the x axis.
	 * @param rotation
	 *            The rotation to render the floor/ceiling at, in radians.
	 * 
	 */
	public void renderFloorCiel(double floorPos, double ceilPos, double yOff, double zOff, double xOff, double rot) {
		double cos = Math.cos(rot);
		double sin = Math.sin(rot);

		Render floorTex = Texture.brick1Floor;
		Render cielingTex = Texture.brick1;

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;
			double z = (floorPos) / ceiling;

			if (ceiling < 0)
				z = (ceilPos) / -ceiling;

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;

				if (ceiling < 0)
					z = (ceilPos) / -ceiling;

				depth *= z;
				double xx = depth * cos + z * sin;
				double yy = z * cos - depth * sin;
				int xPix = (int) (xx + xOff);
				int yPix = (int) (yy + zOff);
				zBuffer[x + y * width] = z;

				if (ceiling > 0)
					pixels[x + y * width] = floorTex.pixels[(xPix & (floorTex.width - 1)) + (yPix & (floorTex.height - 1)) * floorTex.width];
				else
					pixels[x + y * width] = cielingTex.pixels[(xPix & (cielingTex.width - 1)) + (yPix & (cielingTex.height - 1)) * cielingTex.width];

				if (z > 500) {
					pixels[x + y * width] = 0;
				}
			}
		}
	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);

			if (brightness < 0) {
				brightness = 0;
			} else if (brightness > 255) {
				brightness = 255;
			}

			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}

}

package net.keabotstudios.dr2.gfx;

import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.block.Block;
import net.keabotstudios.dr2.game.level.entity.Entity;

public class Render3D extends Render {

	private double[] zBuffer;
	private double[] zBufferWall;
	private double xOff, yOff, zOff, rotOff;

	public Render3D(int w, int h) {
		super(w, h);
		zBuffer = new double[w * h];
		zBufferWall = new double[w];
	}
	
	public void setOffsets(Entity cam) {
		this.xOff = cam.getX() / 8.0;
		this.yOff = cam.getY();
		this.zOff = cam.getZ() / 8.0;
		this.rotOff = cam.getRotation();
	}

	public void renderLevel(Level l) {
		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0;
		}
		
		double cos = Math.cos(rotOff);
		double sin = Math.sin(rotOff);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;
			double z = (l.getFloorPos() + yOff) / ceiling;

			if (ceiling < 0)
				z = (l.getCeilPos() - yOff) / -ceiling;

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;

				depth *= z;
				double xx = depth * cos + z * sin;
				double yy = z * cos - depth * sin;

				zBuffer[x + y * width] = z;

				if (ceiling > 0) {
					double texScaleX = l.getFloorTexture().width / 8.0;
					double texScaleY = l.getFloorTexture().height / 8.0;
					int xPix = (int) ((xx + xOff) * texScaleX);
					int yPix = (int) ((yy + zOff) * texScaleY);
					pixels[x + y * width] = l.getFloorTexture().pixels[(xPix & (l.getFloorTexture().width - 1)) + (yPix & (l.getFloorTexture().height - 1)) * l.getFloorTexture().width];
				} else {
					double texScaleX = l.getCeilTexture().width / 8.0;
					double texScaleY = l.getCeilTexture().height / 8.0;
					int xPix = (int) ((xx + xOff) * texScaleX);
					int yPix = (int) ((yy + zOff) * texScaleY);
					pixels[x + y * width] = l.getCeilTexture().pixels[(xPix & (l.getCeilTexture().width - 1)) + (yPix & (l.getCeilTexture().height - 1)) * l.getCeilTexture().width];
				}

				if (z > 500) {
					pixels[x + y * width] = 0;
				}
			}
		}

		int size = 20;
		for (int xBlock = -size; xBlock <= size; xBlock++) {
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = l.getBlock(xBlock, zBlock);
				Block north = l.getBlock(xBlock, zBlock - 1);
				Block east = l.getBlock(xBlock + 1, zBlock);
				Block west = l.getBlock(xBlock - 1, zBlock);
				Block south = l.getBlock(xBlock, zBlock + 1);
				if (block.opaque) {
					if (!north.opaque) {
						renderWall(xBlock, zBlock, xBlock + 1, zBlock, 0, block.texture, 8);
						renderWall(xBlock, zBlock, xBlock + 1, zBlock, 0.5, block.texture, 8);
					}
					if (!east.opaque) {
						renderWall(xBlock + 1, zBlock, xBlock + 1, zBlock + 1, 0, block.texture, 8);
						renderWall(xBlock + 1, zBlock, xBlock + 1, zBlock + 1, 0.5, block.texture, 8);
					}
					if (!west.opaque) { 
						renderWall(xBlock, zBlock + 1, xBlock, zBlock, 0, block.texture, 8);
						renderWall(xBlock, zBlock + 1, xBlock, zBlock, 0.5, block.texture, 8);
					}
					if (!south.opaque) {
						renderWall(xBlock + 1, zBlock + 1, xBlock, zBlock + 1, 0, block.texture, 8);
						renderWall(xBlock + 1, zBlock + 1, xBlock, zBlock + 1, 0.5, block.texture, 8);
					}
				}
			}
		}
	}

	public void renderWall(double xLeft, double zLeft, double xRight, double zRight, double wallHeight, Render texture, double floorPos) {
		if (texture == null)
			return;

		double cos = Math.cos(rotOff);
		double sin = Math.sin(rotOff);
		double xOffScaled = (xOff / (floorPos * 2.0));
		double yOffScaled = (-yOff / (floorPos * 2.0));
		double zOffScaled = (zOff / (floorPos * 2.0));

		double xcLeft = ((xLeft / 2.0) - xOffScaled) * 2.0;
		double zcLeft = ((zLeft / 2.0) - zOffScaled) * 2.0;

		double rotLeftX = xcLeft * cos - zcLeft * sin;
		double yCornerTL = ((-wallHeight) - yOffScaled) * 2.0;
		double yCornerBL = ((0.5 - wallHeight) - yOffScaled) * 2.0;
		double rotLeftZ = zcLeft * cos + xcLeft * sin;

		double xcRight = ((xRight / 2.0) - xOffScaled) * 2.0;
		double zcRight = ((zRight / 2.0) - zOffScaled) * 2.0;

		double rotRightX = xcRight * cos - zcRight * sin;
		double yCornerTR = (-wallHeight - yOffScaled) * 2.0;
		double yCornerBR = ((0.5 - wallHeight) - yOffScaled) * 2.0;
		double rotRightZ = zcRight * cos + xcRight * sin;

		double tex20 = 0;
		double tex30 = texture.width;
		double clip = 0.5;

		if (rotLeftZ < clip && rotRightZ < clip)
			return;

		if (rotLeftZ < clip) {
			double clip0 = (clip - rotLeftZ) / (rotRightZ - rotLeftZ);
			rotLeftZ = rotLeftZ + (rotRightZ - rotLeftZ) * clip0;
			rotLeftX = rotLeftX + (rotRightX - rotLeftX) * clip0;
			tex20 = tex20 + (tex30 - tex20) * clip0;
		}

		if (rotRightZ < clip) {
			double clip0 = (clip - rotLeftZ) / (rotRightZ - rotLeftZ);
			rotRightZ = rotLeftZ + (rotRightZ - rotLeftZ) * clip0;
			rotRightX = rotLeftX + (rotRightX - rotLeftX) * clip0;
			tex30 = tex20 + (tex30 - tex20) * clip0;
		}

		double xPixelLeft = (rotLeftX / rotLeftZ * height + width / 2.0);
		double xPixelRight = (rotRightX / rotRightZ * height + width / 2.0);

		if (xPixelLeft >= xPixelRight)
			return;

		int xPixelLeftInt = (int) xPixelLeft;
		int xPixelRightInt = (int) xPixelRight;

		if (xPixelLeftInt < 0)
			xPixelLeftInt = 0;
		if (xPixelRightInt > width)
			xPixelRightInt = width;

		double yPixelTopLeft = yCornerTL / rotLeftZ * height + height / 2.0;
		double yPixelBottomLeft = yCornerBL / rotLeftZ * height + height / 2.0;
		double yPixelTopRight = yCornerTR / rotRightZ * height + height / 2.0;
		double yPixelBottomRight = yCornerBR / rotRightZ * height + height / 2.0;

		double tex0 = 1 / rotLeftZ;
		double tex1 = 1 / rotRightZ;
		double tex2 = tex20 / rotLeftZ;
		double tex3 = tex30 / rotRightZ - tex2;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotationX = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			double zWall = (tex0 + (tex1 - tex0) * pixelRotationX);
			if (zBufferWall[x] > zWall)
				continue;
			zBufferWall[x] = zWall;
			int xTex = (int) ((tex2 + tex3 * pixelRotationX) / zWall);

			double yPixelTop = yPixelTopLeft + (yPixelTopRight - yPixelTopLeft) * pixelRotationX;
			double yPixelBottom = yPixelBottomLeft + (yPixelBottomRight - yPixelBottomLeft) * pixelRotationX;

			int yPixelTopInt = (int) yPixelTop;
			int yPixelBottomInt = (int) yPixelBottom;

			if (yPixelTopInt < 0)
				yPixelTopInt = 0;
			if (yPixelBottomInt > height)
				yPixelBottomInt = height;

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				int yTex = (int) (texture.height * pixelRotationY);
				pixels[x + y * width] = texture.pixels[(xTex & texture.width - 1) + (yTex & texture.height - 1) * texture.width];
				zBuffer[x + y * width] = 1 / (tex0 + (tex1 - tex0) * pixelRotationX) * 8;
			}
		}
	}

	public void renderDistanceLimiter(int renderDistance) {
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

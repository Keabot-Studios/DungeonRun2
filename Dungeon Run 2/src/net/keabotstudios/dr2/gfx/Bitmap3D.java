package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.game.Direction;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.object.block.Block;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.game.level.object.entity.Player;
import net.keabotstudios.dr2.game.level.object.tile.Tile;
import net.keabotstudios.dr2.math.Vector2;
import net.keabotstudios.dr2.math.Vector3;

public class Bitmap3D extends Bitmap {

	private static final double CLIP_DISTANCE = 0.2;
	private static final int MAX_DEPTH = 500;
	private static final int WALL_RENDER_DISTANCE = 40;
	private static final int CEIL_FLOOR_POSITION = 8;

	private double[] zBuffer;
	private double[] zBufferWall;
	private double xOff, yOff, zOff, rotOff;

	public Bitmap3D(int w, int h) {
		super(w, h);
		zBuffer = new double[w * h];
		zBufferWall = new double[w];
	}

	public void setOffsets(Player cam) {
		this.xOff = cam.getPos().getX() * 8.0;
		this.yOff = cam.getPos().getY() + cam.getEyeHeight();
		this.zOff = cam.getPos().getZ() * 8.0;
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
			double z = (CEIL_FLOOR_POSITION + yOff) / ceiling;

			if (ceiling < 0)
				z = (CEIL_FLOOR_POSITION - yOff) / -ceiling;

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;

				depth *= z;
				double xx = depth * cos + z * sin;
				double yy = z * cos - depth * sin;

				zBuffer[x + y * width] = z;

				Tile t = l.getTile((int) ((xx + xOff) / 8.0), (int) ((yy + zOff) / 8.0));

				if (ceiling > 0) {
					double texScaleX = t.getFloorTexture().width / 8.0;
					double texScaleY = t.getFloorTexture().height / 8.0;
					int xPix = (int) ((xx + xOff) * texScaleX);
					int yPix = (int) ((yy + zOff) * texScaleY);
					pixels[x + y * width] = t.getFloorTexture().getPixel(xPix & (t.getFloorTexture().width - 1), yPix & (t.getFloorTexture().height - 1));
				} else {
					if (t.hasSkybox()) {
						pixels[x + y * width] = t.getCeilingTexture().getPixel(x + (int) (((double) t.getCeilingTexture().getWidth() / 2.0) * (rotOff / (Math.PI * 2.0))), y);
						zBuffer[x + y * width] = -1;
					} else {
						double texScaleX = t.getCeilingTexture().width / 8.0;
						double texScaleY = t.getCeilingTexture().height / 8.0;
						int xPix = (int) ((xx + xOff) * texScaleX);
						int yPix = (int) ((yy + zOff) * texScaleY);
						pixels[x + y * width] = t.getCeilingTexture().getPixel(xPix & (t.getCeilingTexture().width - 1), yPix & (t.getCeilingTexture().height - 1));
					}
				}

				if (z > MAX_DEPTH) {
					pixels[x + y * width] = ColorUtil.toARGBColor(0);
				}
			}
		}

		int height = 2;

		Vector2 playerPos = new Vector2(l.getPlayer().getPos().getX(), l.getPlayer().getPos().getZ());
		for (int xBlock = -1; xBlock <= l.getWidth(); xBlock++) {
			for (int zBlock = -1; zBlock <= l.getHeight(); zBlock++) {
				Vector2 blockPos = new Vector2(xBlock, zBlock);
				if (playerPos.distance(blockPos) > WALL_RENDER_DISTANCE)
					continue;

				Block block = l.getBlock(xBlock, zBlock);

				Block north = l.getBlock(xBlock, zBlock - 1);
				Block east = l.getBlock(xBlock + 1, zBlock);
				Block south = l.getBlock(xBlock, zBlock + 1);
				Block west = l.getBlock(xBlock - 1, zBlock);

				for (int y = 0; y < height; y++) {
					if (!north.isOpaque()) {
						Bitmap texture = block.getTexture((int) Direction.NORTH.getId(), height);
						renderWall(xBlock, zBlock, xBlock + 1, zBlock, y / 2.0, texture);
					}
					if (!east.isOpaque()) {
						Bitmap texture = block.getTexture((int) Direction.EAST.getId(), height);
						renderWall(xBlock + 1, zBlock, xBlock + 1, zBlock + 1, y / 2.0, texture);
					}
					if (!south.isOpaque()) {
						Bitmap texture = block.getTexture((int) Direction.SOUTH.getId(), height);
						renderWall(xBlock + 1, zBlock + 1, xBlock, zBlock + 1, y / 2.0, texture);
					}
					if (!west.isOpaque()) {
						Bitmap texture = block.getTexture((int) Direction.WEST.getId(), height);
						renderWall(xBlock, zBlock + 1, xBlock, zBlock, y / 2.0, texture);
					}
				}
			}
		}
		for (Entity e : l.getEntites()) {
			renderSprite(e.getPos(), e.getTexture(), 1);
			/*
			 * if (e instanceof PlayerMP) { // NAMETAG RENDERING String name = ((PlayerMP) e).getPlayerName(); Bitmap nametag = new TextBitmap(Font.SMALL, name, 1, ColorUtil.toARGBColor(Color.BLUE)); renderSprite(e.getPos().add(new Vector3(0, 0.7, 0)), nametag, 1, l.getFloorPos()); }
			 */
		}
	}

	public void renderWall(double xLeft, double zLeft, double xRight, double zRight, double wallHeight, Bitmap texture) {
		if (texture == null)
			return;
		double cos = Math.cos(rotOff);
		double sin = Math.sin(rotOff);
		double xOffScaled = (xOff / (CEIL_FLOOR_POSITION * 2.0));
		double yOffScaled = (-yOff / (CEIL_FLOOR_POSITION * 2.0));
		double zOffScaled = (zOff / (CEIL_FLOOR_POSITION * 2.0));

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

		if (rotLeftZ < CLIP_DISTANCE && rotRightZ < CLIP_DISTANCE)
			return;

		if (rotLeftZ < CLIP_DISTANCE) {
			double clip0 = (CLIP_DISTANCE - rotLeftZ) / (rotRightZ - rotLeftZ);
			rotLeftZ = rotLeftZ + (rotRightZ - rotLeftZ) * clip0;
			rotLeftX = rotLeftX + (rotRightX - rotLeftX) * clip0;
			tex20 = tex20 + (tex30 - tex20) * clip0;
		}

		if (rotRightZ < CLIP_DISTANCE) {
			double clip0 = (CLIP_DISTANCE - rotLeftZ) / (rotRightZ - rotLeftZ);
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

	public void renderSprite(Vector3 pos, Bitmap texture, double scale) {
		if (texture == null)
			return;
		double cos = Math.cos(rotOff);
		double sin = Math.sin(rotOff);
		double xOffScaled = (xOff / (CEIL_FLOOR_POSITION * 2.0));
		double yOffScaled = (-yOff / (CEIL_FLOOR_POSITION * 2.0)) - 0.5;
		double zOffScaled = (zOff / (CEIL_FLOOR_POSITION * 2.0));

		double xc = ((pos.getX() / 2.0) - xOffScaled) * 2.0;
		double yc = ((-pos.getY() / 2.0) - yOffScaled) * 2.0;
		double zc = ((pos.getZ() / 2.0) - zOffScaled) * 2.0;

		double rotX = xc * cos - zc * sin;
		double rotY = yc;
		double rotZ = zc * cos + xc * sin;

		double xCenter = width / 2.0;
		double yCenter = height / 2.0;

		double xPixel = rotX / rotZ * height + xCenter;
		double yPixel = rotY / rotZ * height + yCenter;

		int texWidth = (int) (texture.width * scale) * 12;
		int texHeight = (int) (texture.height * scale) * 12;

		double xPixelL = xPixel - texWidth / rotZ;
		double xPixelR = xPixel + texWidth / rotZ;

		double yPixelL = yPixel - texHeight / rotZ;
		double yPixelR = yPixel + texHeight / rotZ;

		int xpl = (int) xPixelL;
		int xpr = (int) xPixelR;
		int ypl = (int) yPixelL;
		int ypr = (int) yPixelR;

		if (xpl < 0)
			xpl = 0;
		if (xpr > width)
			xpr = width;
		if (ypl < 0)
			ypl = 0;
		if (ypr > height)
			ypr = height;

		rotZ *= 8.0;

		for (int yp = ypl; yp < ypr; yp++) {
			double pixelRotationY = (yp - yPixelL) / (yPixelR - yPixelL);
			int yTex = (int) (texture.height * pixelRotationY);
			for (int xp = xpl; xp < xpr; xp++) {
				double pixelRotationX = (xp - xPixelL) / (xPixelR - xPixelL);
				int xTex = (int) (texture.width * pixelRotationX);
				int color = texture.pixels[(xTex & texture.width - 1) + (yTex & texture.height - 1) * texture.width];
				if (ColorUtil.alpha(color) > 0)
					if (zBuffer[xp + yp * width] > rotZ || zBuffer[xp + yp * width] < 0) {
						pixels[xp + yp * width] = color;
						zBuffer[xp + yp * width] = rotZ;
					}
			}
		}
	}

	public void renderDistanceLimiter(int renderDistance) {
		for (int i = 0; i < width * height; i++) {
			if(zBuffer[i] <= 0) continue;
			int color = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);

			if (brightness < 0) {
				brightness = 0;
			} else if (brightness > 255) {
				brightness = 255;
			}

			int r = ColorUtil.red(color);
			int g = ColorUtil.green(color);
			int b = ColorUtil.blue(color);
			int a = ColorUtil.alpha(color);

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = new Color(r, g, b, a).getRGB();
		}
	}
}

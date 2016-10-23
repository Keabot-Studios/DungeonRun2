package net.keabotstudios.dr2.gfx;

import java.awt.image.BufferedImage;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.Util;
import net.keabotstudios.superlog.Logger;

public class Texture {

	public static Bitmap font_main;
	public static Bitmap font_small;

	public static Bitmap brick;
	public static Bitmap brickFloor;
	public static Bitmap brickHi;
	public static Bitmap brickFloorHi;
	public static Bitmap test;
	public static AnimatedBitmap animTest;
	
	public static Bitmap night;
	
	public static AnimatedBitmap spawnPoint;
	public static Bitmap[] player;
	
	public static Bitmap guiBar;
	public static BoxBitmap[] guiBox;
	public static Bitmap[] playerArrow;

	public static void load(Game game) {
		font_main = loadBitmap("/font/main.png", game.getLogger());
		font_small = loadBitmap("/font/small.png", game.getLogger());

		brick = loadBitmap("/texture/brick1.png", game.getLogger());
		brickFloor = loadBitmap("/texture/brick1Floor.png", game.getLogger());
		brickHi = loadBitmap("/texture/512brickWall.png", game.getLogger());
		brickFloorHi = loadBitmap("/texture/512brickFloor.png", game.getLogger());
		test = loadBitmap("/texture/test.png", game.getLogger());
		animTest = new AnimatedBitmap(loadBitmapSheet("/texture/animTest.png", 16, 16, game.getLogger()), 10);
		
		night = loadBitmap("/texture/night.png", game.getLogger());
		
		spawnPoint = new AnimatedBitmap(loadBitmapSheet("/texture/spawnTexture.png", 16, 16, game.getLogger()), 10);
		player = loadBitmapSheet("/texture/player.png", 32, 32, game.getLogger());
		
		guiBar = loadBitmap("/texture/guiBar.png", game.getLogger());
		guiBox = loadBoxBitmapSheet("/texture/guiBox.png", 24, 24, 8, 8, 8, 8, game.getLogger());
		playerArrow = loadBitmapSheet("/texture/playerArrow.png", 8, 8, game.getLogger());
		
		
	}

	public static void update() {
		animTest.update();
		spawnPoint.update();
	}

	public static Bitmap loadBitmap(String file, Logger logger) {
		BufferedImage image = Util.loadImage(file, logger);
		int width = image.getWidth();
		int height = image.getHeight();
		Bitmap result = new Bitmap(width, height);
		image.getRGB(0, 0, width, height, result.pixels, 0, width);
		return result;
	}

	public static Bitmap[] loadBitmapSheet(String file, int width, int height, Logger logger) {
		BufferedImage image = Util.loadImage(file, logger);
		int numX = image.getWidth() / width;
		int numY = image.getHeight() / height;
		Bitmap[] result = new Bitmap[numX * numY];
		for (int x = 0; x < numX; x++) {
			for (int y = 0; y < numY; y++) {
				result[x + y * numX] = new Bitmap(width, height);
				image.getRGB(x * width, y * height, width, height, result[x + y * numX].pixels, 0, width);
			}
		}
		return result;
	}

	public static BoxBitmap[] loadBoxBitmapSheet(String file, int width, int height, int offLeft, int offRight, int offTop, int offBottom, Logger logger) {
		BufferedImage image = Util.loadImage(file, logger);
		int numX = image.getWidth() / width;
		int numY = image.getHeight() / height;
		BoxBitmap[] result = new BoxBitmap[numX * numY];
		for (int x = 0; x < numX; x++) {
			for (int y = 0; y < numY; y++) {
				result[x + y * numX] = new BoxBitmap(width, height, offLeft, offRight, offTop, offBottom);
				image.getRGB(x * width, y * height, width, height, result[x + y * numX].pixels, 0, width);
			}
		}
		return result;
	}
}

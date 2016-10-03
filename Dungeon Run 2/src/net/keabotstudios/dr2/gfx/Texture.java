package net.keabotstudios.dr2.gfx;

import java.awt.image.BufferedImage;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.Util;
import net.keabotstudios.superlog.Logger;

public class Texture {

	public static Bitmap font_main;
	public static Bitmap font_small;
	
	public static Bitmap brick1;
	public static Bitmap brick1Floor;
	public static Bitmap test;
	public static AnimatedBitmap animTest;
	public static Bitmap guiBar;

	public static void load(Game game) {
		font_main = loadBitmap("/font/main.png", game.getLogger());
		font_small = loadBitmap("/font/small.png", game.getLogger());
		
		brick1 = loadBitmap("/texture/brick1.png", game.getLogger());
		brick1Floor = loadBitmap("/texture/brick1Floor.png", game.getLogger());
		test = loadBitmap("/texture/test.png", game.getLogger());
		animTest = new AnimatedBitmap(loadBitmapSheet("/texture/animTest.png", 16, 16, game.getLogger()), 10);
		guiBar = loadBitmap("/texture/guiBar.png", game.getLogger());
	}

	public static void update() {
		animTest.update();
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

}

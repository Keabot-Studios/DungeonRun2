package net.keabotstudios.dr2.gfx;

import java.awt.image.BufferedImage;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.Util;
import net.keabotstudios.superlog.Logger;

public class Texture {
	
	public static Render brick1;
	public static Render brick1Floor;
	public static Render test;
	public static AnimatedRender animTest;
	
	public static void load(Game game) {
		brick1 = loadBitmap("/texture/brick1.png", game.getLogger());
		brick1Floor = loadBitmap("/texture/brick1Floor.png", game.getLogger());
		test = loadBitmap("/texture/test.png", game.getLogger());
		animTest = new AnimatedRender(loadBitmapSheet("/texture/animTest.png", 16, 16, game.getLogger()), 30);
	}
	
	public static void update() {
		animTest.update();
	}
	
	public static Render loadBitmap(String file, Logger logger) {
		BufferedImage image = Util.loadImage(file, logger);
		int width = image.getWidth();
		int height = image.getHeight();
		Render result = new Render(width, height);
		image.getRGB(0, 0, width, height, result.pixels, 0, width);
		return result;
	}
	
	public static Render[] loadBitmapSheet(String file, int width, int height, Logger logger) {
		BufferedImage image = Util.loadImage(file, logger);
		int numX = image.getWidth() / width;
		int numY = image.getHeight() / height;
		Render[] result = new Render[numX * numY];
		for(int x = 0; x < numX; x++) {
			for(int y = 0; y < numY; y++) {
				result[x + y * numX] = new Render(width, height);
				image.getRGB(x * width, y * height, width, height, result[x + y * numX].pixels, 0, width);
			}
		}
		return result;
	}

}

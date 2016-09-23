package net.keabotstudios.dr2.gfx;

import java.awt.image.BufferedImage;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.Util;
import net.keabotstudios.superlog.Logger;

public class Texture {
	
	public static Render brick1;
	public static Render brick1Floor;
	public static Render test;
	
	public static void load(Game game) {
		brick1 = loadBitmap("/texture/brick1.png", game.getLogger());
		brick1Floor = loadBitmap("/texture/brick1Floor.png", game.getLogger());
		test = loadBitmap("/texture/test.png", game.getLogger());
	}
	
	public static Render loadBitmap(String name, Logger logger) {
		BufferedImage image = Util.loadImage(name, logger);
		int width = image.getWidth();
		int height = image.getHeight();
		Render result = new Render(width, height);
		image.getRGB(0, 0, width, height, result.pixels, 0, width);
		return result;
	}

}

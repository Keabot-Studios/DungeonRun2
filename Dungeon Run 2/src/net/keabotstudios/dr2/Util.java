package net.keabotstudios.dr2;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.keabotstudios.superlog.Logger;

public class Util {

	public static BufferedImage loadImage(String string, Logger logger) {
		try {
			logger.info("Loading image '" + string + "'... ");
			BufferedImage out = ImageIO.read(Util.class.getResourceAsStream(string));
			logger.info("success!");
			return out;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("failure. Could not load image.");
			System.exit(-1);
			return null;
		}
	}
	
	public static int[] convertToPixels(BufferedImage image) {
		return ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

}

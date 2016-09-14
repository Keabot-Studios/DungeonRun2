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
			logger.infoLn("success!");
			return out;
		} catch (IOException e) {
			e.printStackTrace();
			logger.errorLn("failure. Could not load image.");
			System.exit(-1);
			return null;
		}
	}

	public static int[] convertToPixels(BufferedImage image) {
		return ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public static void printBytes(byte[] data, Logger logger) {
		for(int i = 0; i < data.length; i++) {
			logger.info(String.format("0x%x ", data[i]));
		}
		logger.infoLn();
	}

}

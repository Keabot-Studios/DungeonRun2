package net.keabotstudios.dr2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
		for (int i = 0; i < data.length; i++) {
			logger.info(String.format("0x%x ", data[i]));
		}
		logger.infoLn();
	}

	public static Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	public static float getScaleOfRectangeInArea(int areaWidth, int areaHeight, int rectWidth, int rectHeight) {
	    float screenAspect = (float) areaWidth / (float) areaHeight;
	    float rectAspect = (float) rectWidth / (float) rectHeight;

	    float scaleFactor;
	    if (screenAspect > rectAspect)
	        scaleFactor = (float) areaHeight / (float) rectHeight;
	    else
	        scaleFactor = (float) areaWidth / (float) rectWidth;

	    return scaleFactor;
	}

}

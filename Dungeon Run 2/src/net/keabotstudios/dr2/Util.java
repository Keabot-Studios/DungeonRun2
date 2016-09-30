package net.keabotstudios.dr2;

import java.awt.Color;
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
			if(logger != null) logger.info("Loading image '" + string + "'... ");
			BufferedImage out = ImageIO.read(Util.class.getResourceAsStream(string));
			if(logger != null) logger.infoLn("success!");
			return out;
		} catch (IOException e) {
			e.printStackTrace();
			if(logger != null) logger.errorLn("failure. Could not load image.");
			System.exit(-1);
			return null;
		}
	}

	public static int[] convertToPixels(BufferedImage image) {
		return ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
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
	
	public static int overlayAlpha(int topColor, int bottomColor, float topAlpha) {
		int oRed = (int) ((((topColor >> 16) & 0xFF) * topAlpha) + (((bottomColor >> 16) & 0xFF) * (1.0f - topAlpha)));
		int oGreen = (int) ((((topColor >> 8) & 0xFF) * topAlpha) + (((bottomColor >> 8) & 0xFF) * (1.0f - topAlpha)));
		int oBlue = (int) ((((topColor >> 0) & 0xFF) * topAlpha) + (((bottomColor >> 0) & 0xFF) * (1.0f - topAlpha)));
		return ((oRed & 0x0ff) << 16) | ((oGreen & 0x0ff) << 8) | (oBlue & 0x0ff);
	}

}

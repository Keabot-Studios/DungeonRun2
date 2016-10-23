package net.keabotstudios.dr2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superlog.Logger;

public class Util {
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

	public static class ImageUtil {
		
		public static BufferedImage loadImage(String string, Logger logger) {
			try {
				if (logger != null)
					logger.infoLn("Loading image '" + string + "'... ");
				BufferedImage out = ImageIO.read(Util.class.getResourceAsStream(string));
				if (logger != null)
					logger.infoLn("success!");
				return out;
			} catch (IOException e) {
				e.printStackTrace();
				if (logger != null)
					logger.errorLn("failure. Could not load image.");
				System.exit(-1);
				return null;
			}
		}
	
		public static int[] convertToPixels(BufferedImage image) {
			return ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		}
	
		public static Image getScaledImage(Image srcImg, int w, int h) {
			BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = resizedImg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g2.drawImage(srcImg, 0, 0, w, h, null);
			g2.dispose();
	
			return resizedImg;
		}

		public static void writeToFile(Bitmap image, String path) {
			File outputfile = new File(path + ".png");
			BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			out.setRGB(0, 0, image.getWidth(), image.getHeight(), image.getPixels(), 0, image.getWidth());
			try {
				ImageIO.write(out, "png", outputfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class ColorUtil {
		public static int mix(int colorA, int colorB) {
			int a = ColorUtil.alpha(colorA) + (ColorUtil.alpha(colorB) * (255 - ColorUtil.alpha(colorA)) / 255);
			int r = (ColorUtil.red(colorA) * ColorUtil.alpha(colorA) / 255) + (ColorUtil.red(colorB) * ColorUtil.alpha(colorB) * (255 - ColorUtil.alpha(colorA)) / (255 * 255));
			int g = (ColorUtil.green(colorA) * ColorUtil.alpha(colorA) / 255) + (ColorUtil.green(colorB) * ColorUtil.alpha(colorB) * (255 - ColorUtil.alpha(colorA)) / (255 * 255));
			int b = (ColorUtil.blue(colorA) * ColorUtil.alpha(colorA) / 255) + (ColorUtil.blue(colorB) * ColorUtil.alpha(colorB) * (255 - ColorUtil.alpha(colorA)) / (255 * 255));
			return makeARGBColor(a, r, g, b);
		}

		public static int makeARGBColor(int alpha, int red, int green, int blue) {
			return (alpha << 24 | (red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
		}

		public static int toARGBColor(Color rgbCol) {
			return makeARGBColor(255, rgbCol.getRed(), rgbCol.getGreen(), rgbCol.getBlue());
		}

		public static int toARGBColor(int rgbCol) {
			return new Color(rgbCol, false).getRGB();
		}

		public static int alpha(int argbCol) {
			return (argbCol >> 24) & 0xFF;
		}

		public static int red(int argbCol) {
			return (argbCol >> 16) & 0xFF;
		}

		public static int green(int argbCol) {
			return (argbCol >> 8) & 0xFF;
		}

		public static int blue(int argbCol) {
			return (argbCol) & 0xFF;
		}
	}

	public static class NetUtil {
		private static final String IP_PORT_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5]):" + "([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
		private static final Pattern PATTERN;
		private static final int DEFAULT_PORT = 25510;

		static {
			PATTERN = Pattern.compile(IP_PORT_PATTERN);
		}

		public static boolean isIpStringValid(String ip) {
			return PATTERN.matcher(ip).matches();
		}

		public static int getPortFromIpString(String ip) {
			if (NetUtil.isIpStringValid(ip) && ip.indexOf(':') > -1) {
				String[] arr = ip.split(":");
				try {
					return Integer.parseInt(arr[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			return DEFAULT_PORT;
		}

		public static String getIpFromIpString(String ip) {
			if (NetUtil.isIpStringValid(ip)) {
				String[] arr = ip.split(":");
				return arr[0];
			}
			return null;
		}
	}
}

package net.keabotstudios.dr2.game.gui;

import java.util.HashMap;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public class GuiRenderer {

	private static HashMap<GuiBarColor, Bitmap[]> bar;
	private static HashMap<GuiBarColor, Bitmap> wideBar;
	private static Bitmap[] barSymbols;

	public enum GuiBarColor {
		ORANGE, RED, PURPLE, GRAY, BLUE, CYAN, BROWN, GREEN, BACKGROUND
	}

	/** Character you want to use when you want to display the health icon. */
	public static final String HEALTH_CHAR = "ƒ";
	/**
	 * Character you want to use when you want to display the ammunition icon.
	 */
	public static final String AMMO_CHAR = "„";
	/** Character you want to use when you want to display the fps icon. */
	public static final String FPS_CHAR = "†";
	/** Character you want to use when you want to display the position icon. */
	public static final String POS_CHAR = "‡";
	/** Character you want to use when you want to display the rotation icon. */
	public static final String ROT_CHAR = "ˆ";
	/**
	 * Character you want to use when you want to display the direction icon.
	 */
	public static final String DIR_CHAR = "‰";

	private static final String guiSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,/$-ƒ„†‡ˆ‰ ";

	public static Game game;

	public static void init(Game game) {
		GuiRenderer.game = game;
		bar = new HashMap<GuiBarColor, Bitmap[]>();
		wideBar = new HashMap<GuiBarColor, Bitmap>();
		int rx0 = 0;
		int rx1 = 0;
		for (int i = 0; i < GuiBarColor.values().length; i++) {
			GuiBarColor col = GuiBarColor.values()[i];
			Bitmap[] colBitmaps = new Bitmap[(col == GuiBarColor.BACKGROUND ? 1 : 3)];
			if (col == GuiBarColor.BACKGROUND) {
				colBitmaps[0] = Texture.guiBar.getSubBitmap(rx0, 0, 8, 16);
				rx0 += 8;
			} else {
				colBitmaps[0] = Texture.guiBar.getSubBitmap(rx1, 32, 6, 16);
				rx1 += 6;
				colBitmaps[1] = Texture.guiBar.getSubBitmap(rx0, 0, 8, 16);
				rx0 += 8;
				colBitmaps[2] = Texture.guiBar.getSubBitmap(rx1, 32, 6, 16);
				rx1 += 6;
			}
			bar.put(col, colBitmaps);
		}
		barSymbols = new Bitmap[guiSymbols.length()];
		rx0 = 0;
		int ry = 48;
		for (int i = 0; i < guiSymbols.length(); i++) {
			barSymbols[i] = Texture.guiBar.getSubBitmap(rx0 % Texture.guiBar.getWidth(), ry, 16, 16);
			rx0 += 16;
			if (rx0 >= Texture.guiBar.getWidth()) {
				rx0 = 0;
				ry += 16;
			}
		}
		rx0 = 0;
		ry = 16;
		for (int i = 0; i < GuiBarColor.values().length; i++) {
			GuiBarColor col = GuiBarColor.values()[i];
			Bitmap colBitmap;
			colBitmap = Texture.guiBar.getSubBitmap(rx0, ry, 16, 16);
			rx0 += 16;
			wideBar.put(col, colBitmap);
		}
	}

	public static boolean isHudTextRendererable(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (!guiSymbols.contains("" + text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void renderStatBar(Bitmap bitmap, String label, int x, int y, int size, int value, int maxValue, GuiBarColor color, GuiBarColor barColor, GuiBarColor labelColor) {
		if (color == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + color.toString() + " as a Gui color!");
			System.exit(0);
		}
		if (barColor == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + barColor.toString() + " as a GuiBar color!");
			System.exit(0);
		}
		if (labelColor == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + labelColor.toString() + " as a GuiBar color!");
			System.exit(0);
		}
		if (value > maxValue)
			value = maxValue;
		if (!isHudTextRendererable(label)) {
			System.err.println("Can't use " + label + " as a GUI label!");
			System.exit(0);
		}

		int guiLength = label.length() * 2 + 1 + maxValue + 4;
		int rx = x;
		for (int i = 0; i < guiLength; i++) {
			Bitmap part = null;
			if (i == 0) {
				part = bar.get(color)[0];
			} else if (i == guiLength - 1) {
				part = bar.get(color)[2];
			} else {
				part = bar.get(color)[1];
			}
			bitmap.render(part, rx, y, size);
			rx += part.getWidth() * size;
		}
		rx = x + (6 + 8) * size;
		for (int i = 0; i < label.length(); i++) {
			bitmap.render(wideBar.get(labelColor), rx, y, size);
			bitmap.render(barSymbols[guiSymbols.indexOf(label.charAt(i))], rx, y, size);
			rx += 16;
		}
		rx += 8 * size;
		for (int i = 0; i < maxValue; i++) {
			bitmap.render(bar.get(GuiBarColor.BACKGROUND)[0], rx, y, size);
			rx += 8 * size;
		}
		rx -= (maxValue * 8) * size;
		for (int i = 0; i < value; i++) {
			Bitmap part = bar.get(barColor)[1];
			bitmap.render(part, rx, y, size);
			rx += part.getWidth() * size;
		}
	}

	public static void renderStatText(Bitmap bitmap, String label, int x, int y, int size, String text, GuiBarColor color, GuiBarColor labelColor, GuiBarColor textColor) {
		if (color == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + color.toString() + " as a Gui color!");
			System.exit(-1);
		}
		if (labelColor == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + labelColor.toString() + " as a Gui label color!");
			System.exit(-1);
		}
		if (textColor == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + labelColor.toString() + " as a Gui text color!");
			System.exit(-1);
		}
		if (!isHudTextRendererable(label)) {
			System.err.println("Can't use " + label + " as a GUI label!");
			System.exit(-1);
		}
		if (!isHudTextRendererable(text)) {
			System.err.println("Can't use " + text + " as GUI text!");
			System.exit(-1);
		}

		int guiLength = label.length() * 2 + 1 + text.length() * 2 + 4;
		int rx = x;
		for (int i = 0; i < guiLength; i++) {
			Bitmap part = null;
			if (i == 0) {
				part = bar.get(color)[0];
			} else if (i == guiLength - 1) {
				part = bar.get(color)[2];
			} else {
				part = bar.get(color)[1];
			}
			bitmap.render(part, rx, y, size);
			rx += part.getWidth() * size;
		}
		rx = x + (6 + 8) * size;
		for (int i = 0; i < label.length(); i++) {
			bitmap.render(wideBar.get(labelColor), rx, y, size);
			bitmap.render(barSymbols[guiSymbols.indexOf(label.charAt(i))], rx, y, size);
			rx += 16;
		}
		rx += 8 * size;
		for (int i = 0; i < text.length(); i++) {
			bitmap.render(wideBar.get(textColor), rx, y, size);
			bitmap.render(barSymbols[guiSymbols.indexOf(text.charAt(i))], rx, y, size);
			rx += 16;
		}
	}

	public static void renderHudLabel(Bitmap bitmap, String label, int x, int y, int size, GuiBarColor color, GuiBarColor labelColor) {
		if (color == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + color.toString() + " as a Gui color!");
			System.exit(-1);
		}
		if (labelColor == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + labelColor.toString() + " as a Gui label color!");
			System.exit(-1);
		}
		if (!isHudTextRendererable(label)) {
			System.err.println("Can't use " + label + " as a GUI label!");
			System.exit(-1);
		}

		int guiLength = label.length() * 2 + 4;
		int rx = x;
		for (int i = 0; i < guiLength; i++) {
			Bitmap part = null;
			if (i == 0) {
				part = bar.get(color)[0];
			} else if (i == guiLength - 1) {
				part = bar.get(color)[2];
			} else {
				part = bar.get(color)[1];
			}
			bitmap.render(part, rx, y, size);
			rx += part.getWidth() * size;
		}
		rx = x + (6 + 8) * size;
		for (int i = 0; i < label.length(); i++) {
			bitmap.render(wideBar.get(labelColor), rx, y, size);
			bitmap.render(barSymbols[guiSymbols.indexOf(label.charAt(i))], rx, y, size);
			rx += 16;
		}
	}

}

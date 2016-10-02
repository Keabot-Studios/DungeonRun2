package net.keabotstudios.dr2.game.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.keabotstudios.dr2.game.level.object.Vector2;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public class Gui {

	private static HashMap<GuiColor, Bitmap[]> bar;
	private static Bitmap[] symbols;

	public enum GuiColor {
		ORANGE, RED, PURPLE, GRAY, BLUE, CYAN, BROWN, GREEN, BACKGROUND
	}

	public static final String guiSymbols = "0123456789$+=/ ";

	public static void init() {
		bar = new HashMap<GuiColor, Bitmap[]>();
		int rx0 = 0;
		int rx1 = 0;
		for (int i = 0; i < GuiColor.values().length; i++) {
			GuiColor col = GuiColor.values()[i];
			Bitmap[] colBitmaps = new Bitmap[(col == GuiColor.BACKGROUND ? 1 : 3)];
			if (col == GuiColor.BACKGROUND) {
				colBitmaps[0] = Texture.gui.getSubBitmap(rx0, 0, 8, 16);
				rx0 += 8;
			} else {
				colBitmaps[0] = Texture.gui.getSubBitmap(rx1, 16, 6, 16);
				rx1 += 6;
				colBitmaps[1] = Texture.gui.getSubBitmap(rx0, 0, 8, 16);
				rx0 += 8;
				colBitmaps[2] = Texture.gui.getSubBitmap(rx1, 16, 6, 16);
				rx1 += 6;
			}
			bar.put(col, colBitmaps);
		}
		symbols = new Bitmap[guiSymbols.length()];
		for (int x = 0; x < 8; x++)
			symbols[x] = Texture.gui.getSubBitmap(x * 16, 32, 16, 16);
		for (int x = 0; x < 7; x++)
			symbols[x + 8] = Texture.gui.getSubBitmap(x * 16, 48, 16, 16);
	}

	public static void renderGuiBar(Bitmap bitmap, String label, int x, int y, int size, int value, int maxValue,
			GuiColor color, GuiColor barColor) {
		if (color == GuiColor.BACKGROUND) {
			System.err.println("Can't use " + color.toString() + " as a Gui color!");
			System.exit(0);
		}
		if (barColor == GuiColor.BACKGROUND) {
			System.err.println("Can't use " + color.toString() + " as a GuiBar color!");
			System.exit(0);
		}
		if (value > maxValue)
			value = maxValue;
		for (int i = 0; i < label.length(); i++) {
			if (!guiSymbols.contains("" + label.charAt(i))) {
				System.err.println("Can't use " + label + " as a GUI label, GUI does not contain: " + label.charAt(i));
				System.exit(0);
			}
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
			rx += part.width * size;
		}
		rx = x + (6 + 8) * size;
		for (int i = 0; i < label.length(); i++) {
			bitmap.render(symbols[guiSymbols.indexOf(label.charAt(i))], rx, y, size);
			rx += 16;
		}
		rx += 8 * size;
		for (int i = 0; i < maxValue; i++) {
			bitmap.render(bar.get(GuiColor.BACKGROUND)[0], rx, y, size);
			rx += 8 * size;
		}
		rx -= (maxValue * 8) * size;
		for (int i = 0; i < value; i++) {
			Bitmap part = bar.get(barColor)[1];
			bitmap.render(part, rx, y, size);
			rx += part.width * size;
		}
	}

	public static void renderLabel(Bitmap bitmap, String label, int x, int y, int size,
			GuiColor color, GuiColor barColor) {
		if (color == GuiColor.BACKGROUND) {
			System.err.println("Can't use " + color.toString() + " as a Gui color!");
			System.exit(0);
		}
		if (barColor == GuiColor.BACKGROUND) {
			System.err.println("Can't use " + color.toString() + " as a GuiBar color!");
			System.exit(0);
		}
		for (int i = 0; i < label.length(); i++) {
			if (!guiSymbols.contains("" + label.charAt(i))) {
				System.err.println("Can't use " + label + " as a GUI label, GUI does not contain: " + label.charAt(i));
				System.exit(0);
			}
		}

		int guiLength = label.length() * 2 + 1 + 4;
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
			rx += part.width * size;
		}
		rx = x + (6 + 8) * size;
		for (int i = 0; i < label.length(); i++) {
			bitmap.render(symbols[guiSymbols.indexOf(label.charAt(i))], rx, y, size);
			rx += 16;
		}
	}

}

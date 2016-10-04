package net.keabotstudios.dr2.game.gui.font;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public enum Font {
	MAIN(Texture.font_main, new String[] {
			"ABCDGHJK",
			"MNOPQRST",
			"UVWXYZab",
			"cdghkmno",
			"pquvwxyz",
			"02356789",
			"*-<>~   ",
			"EFLefjrst",
			"4^?      ",
			"_-\"        ",
			"I1\\/          ",
			"il!()             ",
			":;'.,                       "
	}, new int[] {
			7,
			7,
			7,
			7,
			7,
			7,
			7,
			6,
			6,
			5,
			4,
			3,
			2
	}, 11, 7, 2, 0),
	SMALL(Texture.font_small, new String[] {
			"ABCDEFGHIJKLMNOP",
			"QRSTUVWXYZ012345",
			"6789?_~@#$      ",
			"=+-/\\{}<>\"%^*        ",
			"!()|[]`'.,                      "
	}, new int[] {
			4,
			4,
			4,
			3,
			2
	}, 6, 2, 2, 1);

	private Bitmap texture;
	private String[] lines;
	private int[] widths;
	private int height, spaceWidth, charSpaceWidth;
	private FontCharacter[] characters;
	private int type;

	private static final int NORMAL = 0;
	private static final int ALL_CAPS = 1;
	private static final int NO_CAPS = 2;

	private Font(Bitmap texture, String[] lines, int[] widths, int height, int spaceWidth, int charSpaceWidth, int type) {
		this.texture = texture;
		this.lines = lines;
		this.widths = widths;
		this.height = height;
		this.spaceWidth = spaceWidth;
		this.charSpaceWidth = charSpaceWidth;
		this.type = type;
	}

	public void loadFont() {
		int numRows = texture.getHeight() / height;
		if (lines.length != numRows || widths.length != numRows) {
			System.err.println("Font sheet is wrong size or character list is wrong. Double check them!");
			System.exit(-1);
		}
		ArrayList<FontCharacter> characterList = new ArrayList<FontCharacter>();
		for (int row = 0; row < numRows; row++) {
			int numCharsInRow = texture.getWidth() / widths[row];
			for (int col = 0; col < numCharsInRow; col++) {
				char c = lines[row].charAt(col);
				if (c == ' ')
					continue;
				Bitmap graphic = texture.getSubBitmap(col * widths[row], row * height, widths[row], height);
				characterList.add(new FontCharacter(c, graphic));
			}
		}
		this.characters = characterList.toArray(new FontCharacter[characterList.size()]);
	}

	public int getHeight() {
		return height;
	}

	public int getSpaceWidth() {
		return spaceWidth;
	}

	public FontCharacter[] getCharacters() {
		return characters;
	}

	private int getCharacterIndex(char c) {
		switch (type) {
		default:
		case NORMAL:
			break;
		case ALL_CAPS:
			c = new String("" + c).toUpperCase(Locale.ROOT).charAt(0);
			break;
		case NO_CAPS:
			c = new String("" + c).toLowerCase(Locale.ROOT).charAt(0);
			break;
		}
		if (c == ' ')
			return -1;
		int index = 0;
		for (int l = 0; l < lines.length; l++) {
			for (int i = 0; i < lines[l].length(); i++) {
				if (c == lines[l].charAt(i)) {
					return index;
				}
				index++;
			}
		}
		System.err.println("Font \"" + name() + "\" does not contain character: " + c);
		return -1;
	}

	public FontCharacter getCharacter(char c) {
		switch (type) {
		default:
		case NORMAL:
			break;
		case ALL_CAPS:
			c = new String("" + c).toUpperCase(Locale.ROOT).charAt(0);
			break;
		case NO_CAPS:
			c = new String("" + c).toLowerCase(Locale.ROOT).charAt(0);
			break;
		}
		return characters[getCharacterIndex(c)];
	}

	public boolean hasChar(char c) {
		return getCharacterIndex(c) >= 0;
	}
	
	public void drawString(Bitmap bitmap, String string, int x, int y, int color) {
		drawString(bitmap, string, x, y, 1, 1.0f, color);
	}

	public void drawString(Bitmap bitmap, String string, int x, int y, int size, int color) {
		drawString(bitmap, string, x, y, size, 1.0f, color);
	}

	public void drawString(Bitmap bitmap, String string, int x, int y, int size, float alpha, int color) {
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(c == ' ') {
				x += spaceWidth * size;
			} if (hasChar(c)) {
				FontCharacter character = characters[getCharacterIndex(c)];
				character.render(bitmap, x, y, size, ColorUtil.toARGBColor(Color.BLUE.getRGB()), alpha);
				x += (characters[i].getWidth() + charSpaceWidth) * size;
			}
		}
	}

	public static void load() {
		for (Font f : Font.values()) {
			f.loadFont();
		}
	}

}

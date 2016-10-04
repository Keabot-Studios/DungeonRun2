package net.keabotstudios.dr2.game.gui.font;

import java.awt.Color;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.gfx.Bitmap;

public class FontCharacter {

	final char character;
	final Bitmap graphic;

	public FontCharacter(char character, Bitmap graphic) {
		this.character = character;
		this.graphic = graphic;
	}

	public char getChar() {
		return character;
	}

	public void render(Bitmap bitmap, int x, int y, int size, int color) {
		bitmap.render(graphic.replaceColor(ColorUtil.toARGBColor(Color.WHITE.getRGB()), color), x, y, size);
	}

	public void render(Bitmap bitmap, int x, int y, int size, int color, float alpha) {
		bitmap.render(graphic.replaceColor(ColorUtil.toARGBColor(Color.WHITE.getRGB()), color), x, y, size, alpha);
	}

	public char getCharacter() {
		return character;
	}

	public int getWidth() {
		return graphic.getWidth();
	}

	public int getHeight() {
		return graphic.getHeight();
	}

}

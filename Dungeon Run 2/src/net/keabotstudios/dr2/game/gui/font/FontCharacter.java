package net.keabotstudios.dr2.game.gui.font;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.gfx.Bitmap;

public class FontCharacter {

	final char character;
	final int width, height;
	final boolean[] graphic;

	public FontCharacter(char character, Bitmap graphic) {
		this.character = character;
		this.graphic = new boolean[graphic.getPixels().length];
		this.width = graphic.getWidth();
		this.height = graphic.getHeight();
		for(int i = 0; i < this.graphic.length; i++) {
			this.graphic[i] = (ColorUtil.alpha(graphic.getPixels()[i]) > 0);
		}
	}

	public char getChar() {
		return character;
	}

	public void render(Bitmap bitmap, int x, int y, int size, int color, float alpha) {
		for(int px = 0; px < getWidth(); px++) {
			for(int py = 0; py < getHeight(); py++) {
				if(graphic[px + py * getWidth()]) {
					bitmap.drawRect(px * size + x, py * size + y, size, size, color);
				}
			}
		}
	}

	public char getCharacter() {
		return character;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}

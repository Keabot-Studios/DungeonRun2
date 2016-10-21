package net.keabotstudios.dr2.game.gui.font;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.gfx.Bitmap;

public class FontCharacter {

	final char character;
	private int width, height;
	private boolean[] graphic;

	public FontCharacter(char character, Bitmap graphic) {
		this.character = character;
		setGraphic(graphic);
	}

	public void setGraphic(Bitmap graphic) {
		if (graphic == null)
			return;
		this.graphic = new boolean[graphic.getPixels().length];
		this.width = graphic.getWidth();
		this.height = graphic.getHeight();
		for (int i = 0; i < this.graphic.length; i++) {
			this.graphic[i] = (ColorUtil.alpha(graphic.getPixels()[i]) > 0);
		}
	}

	public char getChar() {
		return character;
	}

	public void render(Bitmap bitmap, int x, int y, int size, int color) {
		for (int px = 0; px < getWidth() * size; px++) {
			for (int py = 0; py < getHeight() * size; py++) {
				if (graphic[(px / size) + (py / size) * getWidth()]) {
					bitmap.setPixel(x + px, y + py, color);
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

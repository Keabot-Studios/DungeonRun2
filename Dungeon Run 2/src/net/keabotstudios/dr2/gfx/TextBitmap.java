package net.keabotstudios.dr2.gfx;

import net.keabotstudios.dr2.game.gui.font.Font;

public class TextBitmap extends Bitmap {

	private Font font;
	private String text;
	private int size, color;

	public TextBitmap(Font font, String text, int size, int color) {
		super(font.getStringWidth(text, size), font.getHeight() * size);
		this.font = font;
		this.text = text;
		this.size = size;
		this.color = color;
		font.drawString(this, this.text, 0, 0, this.size, this.color);
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		if (font == this.font)
			return;
		this.font = font;
		update();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text.equals(this.text))
			return;
		this.text = text;
		update();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size == this.size)
			return;
		this.size = size;
		update();
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		if (color == this.color)
			return;
		this.color = color;
		update();
	}

	private void update() {
		this.width = font.getStringWidth(text, size);
		this.height = font.getHeight() * size;
		this.pixels = new int[width * height];
		font.drawString(this, text, 0, 0, color, size);
	}

}

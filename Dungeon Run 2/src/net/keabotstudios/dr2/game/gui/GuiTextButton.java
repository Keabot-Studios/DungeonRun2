package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.gfx.Bitmap;

public class GuiTextButton extends GuiButton implements GuiAction {

	String text;

	protected GuiTextButton(Rectangle rect, int size, Bitmap texture, String text) {
		super(rect, size, texture);
		this.text = text;
	}

	protected GuiTextButton(int x, int y, int width, int height, int size, Bitmap texture, String text) {
		this(new Rectangle(x, y, width, height), size, texture, text);
	}

	@Override
	public void render(Bitmap bitmap) {
		super.render(bitmap);
	}
}

package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.gfx.Bitmap;

public class GuiTextButton extends GuiButton {

	String text;

	public GuiTextButton(Rectangle rect, int size, Bitmap texture, String text, boolean renderAsBox) {
		super(rect, size, texture, renderAsBox);
		this.text = text;
	}

	public GuiTextButton(int x, int y, int width, int height, int size, Bitmap texture, String text, boolean renderAsBox) {
		this(new Rectangle(x, y, width, height), size, texture, text, renderAsBox);
	}

	@Override
	public void render(Bitmap bitmap) {
		super.render(bitmap);		
	}
}

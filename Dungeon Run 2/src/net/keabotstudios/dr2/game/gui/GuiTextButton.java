package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.gfx.Bitmap;

public class GuiTextButton extends GuiButton {

	String text;

	public GuiTextButton(Rectangle rect, int size, int color, Bitmap texture, Bitmap activeTexture, String text, boolean renderAsBox) {
		super(rect, size, color, texture, activeTexture, renderAsBox);
		this.text = text;
	}

	public GuiTextButton(int x, int y, int width, int height, int size, int color, Bitmap texture, Bitmap activeTexture, String text, boolean renderAsBox) {
		this(new Rectangle(x, y, width, height), size, color, texture, activeTexture, text, renderAsBox);
	}

	@Override
	public void render(Bitmap bitmap) {
		super.render(bitmap);		
	}
}

package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.gfx.Bitmap;

public class GuiTextButton extends GuiButton {

	String text;

	public GuiTextButton(Rectangle rect, int size, int color, Bitmap texture, Bitmap hoveringTexture, Bitmap clickTexture, String text, boolean renderAsBox, boolean tapToActivate) {
		super(rect, size, color, texture, hoveringTexture, clickTexture, renderAsBox, tapToActivate);
		this.text = text;
	}

	public GuiTextButton(int x, int y, int width, int height, int size, int color, Bitmap texture, Bitmap hoveringTexture, Bitmap clickTexture, String text, boolean renderAsBox, boolean tapToActivate) {
		this(new Rectangle(x, y, width, height), size, color, texture, hoveringTexture, clickTexture, text, renderAsBox, tapToActivate);
	}

	@Override
	public void render(Bitmap bitmap) {
		super.render(bitmap);
	}
}

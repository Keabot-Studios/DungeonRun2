package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.BoxBitmap;
import net.keabotstudios.superin.Input;

public class GuiButton extends GuiComponent {

	private boolean renderBox;
	private Bitmap bitmapTexture;
	private GuiAction action;
	private int width, height;
	private int color;

	public GuiButton(Rectangle rect, int size, int color, Bitmap texture, boolean renderAsBox) {
		this(rect.x, rect.y, rect.width, rect.height, size, color, texture, renderAsBox);
	}

	public GuiButton(int x, int y, int width, int height, int size, int color, Bitmap texture, boolean renderAsBox) {
		super(x, y, size);
		this.width = width;
		this.height = height;
		this.color = color;
		this.bitmapTexture = texture;
		this.renderBox = renderAsBox;
	}

	public void setAction(GuiAction guiAction) {
		this.action = guiAction;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setRenderBox(boolean renderBox) {
		this.renderBox = renderBox;
	}

	public void setBitmapTexture(Bitmap bitmapTexture) {
		this.bitmapTexture = bitmapTexture;
	}

	public void render(Bitmap bitmap) {
		if (bitmapTexture != null) {
			if (renderBox)
				bitmap.renderBox((BoxBitmap)bitmapTexture, x, y, width, height);
			else
				bitmap.render(bitmapTexture, x, y);
		}
	}

	@Override
	public void update(Input input) {
		if (input.getInputTapped("MENU_CONFIRM") && action != null && isMouseOver(input)) {
			action.onAction();
		}
	}

	public boolean isMouseOver(Input input) {
		Rectangle screen = GuiRenderer.game.getScreenRect();
		int scaledMouseX = (int) Math.round(input.getMouseX() / GuiRenderer.game.getScreenScale() + screen.getX());
		int scaledMouseY = (int) Math.round(input.getMouseY() / GuiRenderer.game.getScreenScale() + screen.getY());
		if(scaledMouseX >= x && scaledMouseX < x + width) {
			if(scaledMouseY >= y && scaledMouseY < y + height) {
				return true;
			}
		}
		return false;
	}
}

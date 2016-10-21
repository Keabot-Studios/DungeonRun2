package net.keabotstudios.dr2.game.gui;

import java.awt.Color;
import java.awt.Rectangle;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.BoxBitmap;
import net.keabotstudios.superin.Input;

public class GuiButton extends GuiComponent {

	private boolean renderBox;
	private Bitmap bitmapTexture, activeTexture;
	private GuiAction action;
	private int width, height;
	private int color;
	private boolean pressed = false;

	public GuiButton(Rectangle rect, int size, int color, Bitmap texture, Bitmap activeTexture, boolean renderAsBox) {
		this(rect.x, rect.y, rect.width, rect.height, size, color, texture, activeTexture, renderAsBox);
	}

	public GuiButton(int x, int y, int width, int height, int size, int color, Bitmap texture, Bitmap activeTexture, boolean renderAsBox) {
		super(x, y, size);
		this.width = width;
		this.height = height;
		this.color = color;
		this.bitmapTexture = texture;
		this.activeTexture = activeTexture;
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
				if(pressed)
					bitmap.renderBox(((BoxBitmap) activeTexture), x, y, width * size, height * size);
				else
					bitmap.renderBox(((BoxBitmap) bitmapTexture), x, y, width * size, height * size);
			else
				bitmap.render(bitmapTexture, x, y);
		}
		bitmap.fillRect(scaledMouseX - 1, scaledMouseY - 1, 2, 2, ColorUtil.toARGBColor(Color.GREEN));
	}
	
	int scaledMouseX = 0;
	int scaledMouseY = 0;

	@Override
	public void update(Input input) {
		Rectangle screen = GuiRenderer.game.getScreenRect();
		scaledMouseX = (int) ((double) input.getMouseX() / GuiRenderer.game.getScreenScale() + (double) screen.getX());
		scaledMouseY = (int)((double) input.getMouseY() / GuiRenderer.game.getScreenScale() + (double) screen.getY());
		if (action != null && isMouseOver()) {
			if(input.getInputTapped("MENU_CONFIRM")) {
				action.onAction();
				pressed = true;
			} else if(input.getInput("MENU_CONFIRM")) {
				pressed = true;
			} else {
				pressed = false;
			}
		} else {
			pressed = false;
		}
	}

	public boolean isMouseOver() {
		if(scaledMouseX >= x && scaledMouseX < x + (width * size)) {
			if(scaledMouseY >= y && scaledMouseY < y + (height * size)) {
				return true;
			}
		}
		return false;
	}
}

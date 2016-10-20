package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.BoxBitmap;
import net.keabotstudios.superin.Input;

public class GuiButton extends GuiComponent {

	private boolean renderBox;
	private Bitmap bitmapTexture;
	private Bitmap hoverTexture;
	private Bitmap clickedTexture;
	private GuiAction action;
	private int width, height;
	private int color;
	private int curState;
	private boolean tapType;

	public GuiButton(Rectangle rect, int size, int color, Bitmap texture, Bitmap hoveringTexture, Bitmap clickTexture,
			boolean renderAsBox, boolean tapToActivate) {
		this(rect.x, rect.y, rect.width, rect.height, size, color, texture, hoveringTexture, clickTexture, renderAsBox,
				tapToActivate);
	}

	public GuiButton(int x, int y, int width, int height, int size, int color, Bitmap texture, Bitmap hoveringTexture,
			Bitmap clickTexture, boolean renderAsBox, boolean tapToActivate) {
		super(x, y, size);
		this.width = width;
		this.height = height;
		this.color = color;
		this.bitmapTexture = texture;
		this.hoverTexture = hoveringTexture;
		this.clickedTexture = clickTexture;
		this.renderBox = renderAsBox;
		this.tapType = tapToActivate;
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

	public void setHoverTexture(Bitmap hoverTexture) {
		this.hoverTexture = hoverTexture;
	}

	public void setClickTexture(Bitmap clickedTexture) {
		this.clickedTexture = clickedTexture;
	}

	public void render(Bitmap bitmap) {
		Bitmap texture = getCurTexture();
		if (texture != null) {
			if (renderBox)
				bitmap.renderBox((BoxBitmap) texture, x, y, width, height);
			else
				bitmap.render(texture, x, y);
		}
	}

	public Bitmap getCurTexture() {
		switch (curState) {
		default:
			return this.bitmapTexture;
		case 1:
			return this.hoverTexture;
		case 2:
			return this.clickedTexture;
		}
	}

	@Override
	public void update(Input input) {
		if (input.getInput("MENU_CONFIRM") && action != null && isMouseOver(input)) {
			curState = 2;
			if (tapType ? input.getInputTapped("MENU_CONFIRM") : input.getInput("MENU_CONFIRM"))
				action.onAction();
		} else if (isMouseOver(input)) {
			curState = 1;
		} else
			curState = 0;
	}

	public boolean isMouseOver(Input input) {
		Rectangle screen = GuiRenderer.game.getScreenRect();
		int scaledMouseX = (int) Math.round(input.getMouseX() / GuiRenderer.game.getScreenScale() + screen.getX());
		int scaledMouseY = (int) Math.round(input.getMouseY() / GuiRenderer.game.getScreenScale() + screen.getY());
		if (scaledMouseX >= x && scaledMouseX < x + width) {
			if (scaledMouseY >= y && scaledMouseY < y + height) {
				return true;
			}
		}
		return false;
	}
}

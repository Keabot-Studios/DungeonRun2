package net.keabotstudios.dr2.game.gui;

import java.awt.Color;
import java.awt.Rectangle;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.gui.font.Font;
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

	public GuiButton(Rectangle rect, int size, int color, Bitmap texture, Bitmap hoveringTexture, Bitmap clickTexture, boolean renderAsBox, boolean tapToActivate) {
		this(rect.x, rect.y, rect.width, rect.height, size, color, texture, hoveringTexture, clickTexture, renderAsBox, tapToActivate);
	}

	public GuiButton(int x, int y, int width, int height, int size, int color, Bitmap texture, Bitmap hoveringTexture, Bitmap clickTexture, boolean renderAsBox, boolean tapToActivate) {
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
		int color = ColorUtil.toARGBColor(Color.GREEN);
		int mSize = 2;
		if((GameInfo.TIME % GameInfo.MAX_UPS) < GameInfo.MAX_UPS / 2) color = ColorUtil.toARGBColor(Color.RED); 
		bitmap.fillRect(scaledMouseX - mSize / 2, scaledMouseY - mSize / 2, mSize, mSize, color);
		Font.SMALL.drawString(bitmap, "<Mouse Position", scaledMouseX + mSize / 2 + 2, scaledMouseY - (Font.SMALL.getHeight() * 2) / 2, 2, color);
		Font.SMALL.drawString(bitmap, "(Scaled)", scaledMouseX + mSize / 2 + 20, scaledMouseY + (Font.SMALL.getHeight() * 2) / 2 + Font.SMALL.getHeight() * size, 2, color);
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

	int scaledMouseX = 0;
	int scaledMouseY = 0;

	@Override
	public void update(Input input) {
		Rectangle screen = GuiRenderer.game.getScreenRect();
		scaledMouseX = (int) ((double) input.getMouseX() / GuiRenderer.game.getScreenScale() + (double) screen.getX());
		scaledMouseY = (int) ((double) input.getMouseY() / GuiRenderer.game.getScreenScale() + (double) screen.getY());
		if (input.getInput("MENU_CONFIRM") && action != null && isMouseOver()) {
			if (tapType ? input.getInputTapped("MENU_CONFIRM") : input.getInput("MENU_CONFIRM"))
				action.onAction();
		} else if (isMouseOver()) {
			curState = 1;
		} else
			curState = 0;
	}

	public boolean isMouseOver() {
		if (scaledMouseX >= x && scaledMouseX < x + (width * size)) {
			if (scaledMouseY >= y && scaledMouseY < y + (height * size)) {
				return true;
			}
		}
		return false;
	}
}

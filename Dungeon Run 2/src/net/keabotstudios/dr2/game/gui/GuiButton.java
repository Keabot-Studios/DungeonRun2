package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public class GuiButton extends GuiComponent {

	Rectangle rectangle;

	boolean renderBox;

	Bitmap bitmapTexture;

	private GuiAction action;

	public GuiButton(Rectangle rect, int size, Bitmap texture, boolean renderAsBox) {
		super(rect.x, rect.y, size);
		rectangle = rect;
		bitmapTexture = texture;
		renderBox = renderAsBox;
	}

	public GuiButton(int x, int y, int width, int height, int size, Bitmap texture, boolean renderAsBox) {
		this(new Rectangle(x, y, width, height), size, texture, renderAsBox);
	}

	public void setAction(GuiAction guiAction) {
		action = guiAction;
	}

	@Override
	public void render(Bitmap bitmap) {

		if (bitmapTexture != null) {
			if (renderBox)
				bitmap.renderBox(bitmap, rectangle.x, rectangle.y, rectangle.width, rectangle.height, 1);
			else
				bitmap.render(bitmapTexture, rectangle.x, rectangle.y);
		}

	}

	@Override
	public void update(Input input) {
		if (input.getInputTapped("MENU_CONFIRM") && action != null) {
			action.onAction();
		}
	}

	public boolean isSelected(Input input) {
		return input.getMouseX() * GameInfo.getResScale(GuiRenderer.game) > rectangle.getX()
				&& input.getMouseX() * GameInfo.getResScale(GuiRenderer.game) < rectangle.getMaxX()
				&& input.getMouseY() * GameInfo.getResScale(GuiRenderer.game) > rectangle.getY();

	}
}

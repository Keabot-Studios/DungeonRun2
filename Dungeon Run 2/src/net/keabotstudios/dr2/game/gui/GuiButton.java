package net.keabotstudios.dr2.game.gui;

import java.awt.Rectangle;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public class GuiButton extends GuiComponent implements GuiAction {

	Rectangle rectangle;

	Bitmap bitmapTexture;

	protected GuiButton(Rectangle rect, int size, Bitmap texture) {
		super(rect.x, rect.y, size);
		rectangle = rect;
		bitmapTexture = texture;
	}

	protected GuiButton(int x, int y, int width, int height, int size, Bitmap texture) {
		this(new Rectangle(x, y, width, height), size, texture);
	}

	@Override
	public void render(Bitmap bitmap) {

		if (bitmapTexture != null) {
			bitmap.render(bitmapTexture, rectangle.x, rectangle.y);
		}

	}

	@Override
	public void update(Input input) {
		if (input.getInputTapped("MENU_ENTER")) {
			
		}
	}

	public boolean isSelected(Input input) {
		return input.getMouseX() * GameInfo.getResScale(GuiRenderer.game) > rectangle.getX() && input.getMouseX() * GameInfo.getResScale(GuiRenderer.game) < rectangle.getMaxX() && input.getMouseY() * GameInfo.getResScale(GuiRenderer.game) > rectangle.getY();

	}

	public void onAction() {

	}
}

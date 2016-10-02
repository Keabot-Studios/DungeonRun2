package net.keabotstudios.dr2.game.gui;

import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public abstract class GuiComponent {

	private int x, y, size;
	
	public GuiComponent(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public abstract void render(Bitmap bitmap);
	public abstract void update(Input input);

}

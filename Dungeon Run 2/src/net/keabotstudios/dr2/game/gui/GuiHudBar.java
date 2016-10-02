package net.keabotstudios.dr2.game.gui;

import net.keabotstudios.dr2.game.gui.GuiRenderer.GuiBarColor;
import net.keabotstudios.dr2.gfx.Bitmap;

public abstract class GuiHudBar extends GuiComponent {

	protected GuiBarColor color;

	protected GuiHudBar(int x, int y, int size, GuiBarColor color) {
		super(x, y, size);
		if(color == GuiBarColor.BACKGROUND) {
			System.out.println("Can't use " + color.name() + " as a Gui color!");
			System.exit(-1);
		}
		this.color = color;
	}

	public GuiBarColor getColor() {
		return color;
	}

	public void setColor(GuiBarColor color) {
		if(color == GuiBarColor.BACKGROUND) {
			System.out.println("Can't use " + color.name() + " as a Gui color!");
			System.exit(-1);
		}
		this.color = color;
	}
	
	public void render(Bitmap bitmap) {}

}

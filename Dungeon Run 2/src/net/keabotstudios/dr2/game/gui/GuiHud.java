package net.keabotstudios.dr2.game.gui;

import net.keabotstudios.dr2.game.gui.GuiRenderer.GuiBarColor;

public abstract class GuiHud extends GuiComponent {

	private GuiBarColor bgColor;
	
	public GuiHud(int x, int y, int size) {
		super(x, y, size);
	}

}

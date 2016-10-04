package net.keabotstudios.dr2.game.gui;

import net.keabotstudios.superin.Input;

public interface GuiAction {
	public void onAction();

	public boolean isSelected(Input input);
}

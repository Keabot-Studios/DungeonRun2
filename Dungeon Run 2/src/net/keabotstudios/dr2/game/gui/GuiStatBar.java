package net.keabotstudios.dr2.game.gui;

import net.keabotstudios.dr2.game.gui.GuiRenderer.GuiBarColor;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public class GuiStatBar extends GuiHudLabel {

	private GuiBarColor barColor;
	private int value, maxValue;

	public GuiStatBar(int x, int y, int size, String label, int value, int maxValue, GuiBarColor color, GuiBarColor labelColor, GuiBarColor barColor) {
		super(x, y, size, label, color, labelColor);
		this.value = value;
		this.maxValue = maxValue;
		this.barColor = barColor;
	}

	public GuiBarColor getBarColor() {
		return barColor;
	}

	public void setBarColor(GuiBarColor barColor) {
		this.barColor = barColor;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if(value < 0) value = 0;
		if(value > maxValue) value = maxValue;
		this.value = value;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		if(maxValue < 0) maxValue = 0;
		if(value > maxValue) value = maxValue;
		this.maxValue = maxValue;
	}

	public void render(Bitmap bitmap) {
		GuiRenderer.renderStatBar(bitmap, label, x, y, size, value, maxValue, color, barColor, labelColor);
	}

	public void update(Input input) {}

}

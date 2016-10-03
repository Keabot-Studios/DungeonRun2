package net.keabotstudios.dr2.game.gui;

import net.keabotstudios.dr2.game.gui.GuiRenderer.GuiBarColor;
import net.keabotstudios.dr2.gfx.Bitmap;

public class GuiHudLabel extends GuiHudBar {

	protected GuiBarColor labelColor;
	protected String label;

	public GuiHudLabel(int x, int y, int size, String label, GuiBarColor color, GuiBarColor labelColor) {
		super(x, y, size, color);
		if (!GuiRenderer.isHudTextRendererable(label)) {
			System.out.println("Can't use " + label + " as Gui label!");
			System.exit(-1);
		}
		this.label = label;
		if (labelColor == GuiBarColor.BACKGROUND) {
			System.out.println("Can't use " + color.name() + " as a Gui label color!");
			System.exit(-1);
		}
		this.labelColor = labelColor;
	}

	public GuiBarColor getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(GuiBarColor labelColor) {
		if (labelColor == GuiBarColor.BACKGROUND) {
			System.out.println("Can't use " + color.name() + " as a Gui label color!");
			System.exit(-1);
		}
		this.labelColor = labelColor;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		if (!GuiRenderer.isHudTextRendererable(label)) {
			System.out.println("Can't use " + label + " as Gui label!");
			System.exit(-1);
		}
		this.label = label;
	}

	public void render(Bitmap bitmap) {
		GuiRenderer.renderHudLabel(bitmap, label, x, y, size, color, labelColor);
	}

}

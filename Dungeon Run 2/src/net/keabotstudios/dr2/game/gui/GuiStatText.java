package net.keabotstudios.dr2.game.gui;

import net.keabotstudios.dr2.game.gui.GuiRenderer.GuiBarColor;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public class GuiStatText extends GuiHudLabel {
	
	private GuiBarColor textColor;
	private String text;
	
	public GuiStatText(int x, int y, int size, String label, String text, GuiBarColor color, GuiBarColor labelColor, GuiBarColor textColor) {
		super(x, y, size, label, color, labelColor);
		if(!GuiRenderer.isHudTextRendererable(text)) {
			System.out.println("Can't use " + text + " as Gui text!");
			System.exit(-1);
		}
		this.text = text;
		if (textColor == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + textColor.toString() + " as a Gui text color!");
			System.exit(-1);
		}
		this.textColor = textColor;
	}

	public GuiBarColor getTextColor() {
		return textColor;
	}

	public void setTextColor(GuiBarColor textColor) {
		if (textColor == GuiBarColor.BACKGROUND) {
			System.err.println("Can't use " + textColor.toString() + " as a Gui text color!");
			System.exit(-1);
		}
		this.textColor = textColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if(text.equals(this.text)) return;
		if(!GuiRenderer.isHudTextRendererable(text)) {
			System.out.println("Can't use " + text + " as Gui text!");
			System.exit(-1);
		}
		this.text = text;
	}

	public void render(Bitmap bitmap) {
		GuiRenderer.renderStatText(bitmap, text, x, y, size, text, textColor, textColor, textColor);
	}

	public void update(Input input) {}

}

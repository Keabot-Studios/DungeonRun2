package net.keabotstudios.dr2.game.gui;

import java.util.ArrayList;
import java.util.List;

import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.object.Vector2;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public class Gui {

	private Bitmap[] background;
	private Bitmap[] edges;
	private Bitmap[] symbols;
	
	private int num;

	public Gui() {
		symbols = new Bitmap[15];
		for (int x = 0; x < 8; x++)
			symbols[x] = Texture.gui.getSubBitmap(x * 16, 32, 16, 16);
		for (int x = 0; x < 7; x++)
			symbols[x + 8] = Texture.gui.getSubBitmap(x * 16, 48, 16, 16);
	}

	public void render(Bitmap bitmap, Level level) {
		renderMoney(num, 6, new Vector2(10, 10), bitmap);
		num++;
		
		renderNumber((int)level.getPlayer().getPos().getX(), 3, new Vector2(450, 10), bitmap);
		renderNumber((int)level.getPlayer().getPos().getZ(), 3, new Vector2(514, 10), bitmap);
	}

	public void renderNumber(int num, int maxNumLength, Vector2 pos, Bitmap bitmap) {
		List<Integer> revDigits = new ArrayList<Integer>();
		while (num > 0) {
			revDigits.add(num % 10);
			num = num / 10;
		}
		List<Integer> digits = new ArrayList<Integer>();
		for (int i = revDigits.size() - 1; i >= 0; i--)
			digits.add(revDigits.get(i));

		for (int i = 0; i < maxNumLength; i++) {
			if (maxNumLength - i > digits.size()) {
				bitmap.render(symbols[14], (int) pos.getX() + 16 * i, (int) pos.getY());
			} else {
				bitmap.render(symbols[digits.get(i - (maxNumLength - digits.size()))], (int) pos.getX() + 16 * i,
						(int) pos.getY());
			}
		}
	}
	
	public void renderMoney(int num, int maxNumLength, Vector2 pos, Bitmap bitmap)
	{
		bitmap.render(symbols[10], (int) pos.getX(), (int) pos.getY());
		renderNumber(num, maxNumLength, new Vector2(pos.getX() + 16, pos.getY()), bitmap);
	}
}

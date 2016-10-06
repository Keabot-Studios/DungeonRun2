package net.keabotstudios.dr2.game.level;

import java.awt.Color;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public class Minimap {

	private int x, y, width, height, scale = 2;
	private int zoom = 2;
	private float transparency;
	private Level level;
	
	private Bitmap minimap;
	
	public Minimap(Level level, int x, int y, int width, int height, float transparency) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.transparency = transparency;
		minimap = new Bitmap((level.getWidth() + 1) * scale, (level.getHeight() + 1) * scale);
		minimap.clear(ColorUtil.toARGBColor(Color.BLACK));
	}
	
	public void update() {
		minimap.fillRect(x, y, width, height, ColorUtil.toARGBColor(Color.BLACK));
		for (int x = -1; x <= level.getWidth() + 1; x++) {
			for(int y = level.getHeight(); y >= -1; y--){
				minimap.fillRect(x * scale + scale, (-y + level.getHeight()) * scale, scale, scale, level.getBlock(x, y).getMinimapColor());
			}
		}
	}

	public void render(Bitmap bitmap) {
		double pX = (level.getPlayer().getPos().getX() + 1.5) * scale;
		double pZ = (level.getHeight() * scale) - (Math.round(level.getPlayer().getPos().getZ() + 0.5) * scale);
		double vW = Math.round((double) width / (double) zoom);
		double vH = Math.round((double) width / (double) zoom);
		double vX = pX - vW / 2.0;
		double vY = (int) (pZ - vH / 2.0);
		bitmap.fillRect(x, y, width, height, ColorUtil.toARGBColor(Color.BLACK));
		Bitmap output = minimap.getSubBitmap((int) Math.round(vX), (int) Math.round(vY), (int)vW, (int) vH).rotate(-level.getPlayer().getRotation() + (2.0 * Math.PI));
		bitmap.render(output, x, y, zoom, transparency);
		bitmap.render(Texture.playerArrow, x + width / 2 - Texture.playerArrow.getWidth(), y + height / 2 - Texture.playerArrow.getHeight() / 2);
	}

}

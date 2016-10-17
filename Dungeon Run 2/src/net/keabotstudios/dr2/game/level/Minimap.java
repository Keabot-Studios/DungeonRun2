package net.keabotstudios.dr2.game.level;

import java.awt.Color;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.game.level.object.entity.PlayerMP;
import net.keabotstudios.dr2.game.level.object.entity.SpawnPointEntity;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public class Minimap {

	private int x, y, width, height, scale = 4;
	private int zoom = 1;
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
		init();
	}
	
	public void init() {
		for (int x = -1; x <= level.getWidth() + 1; x++) {
			for(int y = level.getHeight() + 1; y >= -1; y--){
				minimap.fillRect(x * scale + scale, (-y + level.getHeight()) * scale, scale, scale, level.getBlock(x, y).getMinimapColor());
			}
		}
	}
	
	public void update() {}

	public void render(Bitmap bitmap) {
		int vX = (int) (level.getPlayer().getPos().getX() * scale) - (width / zoom) / 2;
		int vY = (int) (level.getHeight() * scale - level.getPlayer().getPos().getZ() * scale) - (height / zoom) / 2;
		Bitmap output = new Bitmap(width / zoom, height / zoom);
		output.clear(Color.BLACK);
		output.render(minimap.getSubBitmap(vX, vY, width / zoom, height / zoom), 0, 0);
		for(PlayerMP pMP : level.getPlayerMPs()) {
			int pMPX = (int) (pMP.getPos().getX() * scale) - vX;
			int pMPY = (int) (level.getHeight() * scale - pMP.getPos().getZ() * scale) - vY;
			output.render(Texture.playerArrow[1].rotate(pMP.getRotation()), pMPX - Texture.playerArrow[3].getWidth() / 2, pMPY - Texture.playerArrow[3].getHeight() / 2, scale / 2);
		}
		SpawnPointEntity spawn = (SpawnPointEntity) level.getEntity("spawn");
		int sX = (int) (spawn.getPos().getX() * scale) - vX;
		int sY = (int) (level.getHeight() * scale - spawn.getPos().getZ() * scale) - vY;
		output.render(spawn.getTexture(), sX - spawn.getTexture().getWidth() / 2, sY - spawn.getTexture().getHeight() / 2 - spawn.getTexture().getHeight() / scale, scale / 4);
		output.render(Texture.playerArrow[0].rotate(level.getPlayer().getRotation()), (width / 2) / zoom - Texture.playerArrow[0].getWidth() / 2, (height / 2) / zoom - Texture.playerArrow[0].getHeight() / 2, scale / 2);
		bitmap.render(output, x, y, zoom, transparency);
	}

}

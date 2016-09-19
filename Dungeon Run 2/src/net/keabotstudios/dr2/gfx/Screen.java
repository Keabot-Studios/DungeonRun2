package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.game.level.Level;

public class Screen extends Render {

	private Render3D render3d;
	private RenderMinimap minimap;

	public Screen(int w, int h) {
		super(w, h);
		render3d = new Render3D(w, h);
	}

	public void render(Level level) {
		clear(Color.BLACK);
		render3d.setOffsets(level.getPlayer());
		render3d.renderLevel(level);
		render3d.renderDistanceLimiter(level.getRenderDistance());
		render(render3d, 0, 0);
	}

}

package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.game.entity.Player;

public class Screen extends Render {

	
	private Render3D level;

	public Screen(int w, int h) {
		super(w, h);
		level = new Render3D(w, h);
	}

	public void render(Player p) {
		clear(Color.BLACK);

		level.renderFloorCiel(10, 10, p.getY(), p.getZ(), p.getX(), p.getRotation());
		level.renderDistanceLimiter();
		render(level, 0, 0);

	}

}

package net.keabotstudios.dr2.gfx;

import java.awt.Color;

import net.keabotstudios.dr2.Controller;

public class Screen extends Render {

	
	private Render3D level;

	public Screen(int w, int h) {
		super(w, h);
		level = new Render3D(w, h);
	}

	public void render(Controller controller) {
		clear(Color.BLACK);

		level.renderFloorCiel(10, 800, 0, controller.z, controller.x, controller.rot);
		level.renderDistanceLimiter();
		render(level, 0, 0);

	}

}

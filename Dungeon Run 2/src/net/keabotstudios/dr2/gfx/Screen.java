package net.keabotstudios.dr2.gfx;

import java.awt.Color;
import java.util.Random;

import net.keabotstudios.dr2.Controller;

public class Screen extends Render {
	
	private Render test;
	private Render3D render;
	private Render ci_mouse;

	public Screen(int w, int h) {
		super(w, h);
		test = new Render(256, 256);
		render = new Render3D(w, h);
		Random random = new Random();
		for(int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}
	}
	
	public void render(Controller controller) {
		clear(Color.BLACK);
		render.renderFloorCiel(10, 10, controller.z, controller.x, controller.rot);
		render.renderDistanceLimiter();
		render(render, 0, 0);
	}

}

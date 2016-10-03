package net.keabotstudios.dr2.game.gamestate;

import java.awt.Color;

import net.keabotstudios.dr2.gfx.Bitmap;

public class NullGameState extends GameState {

	public NullGameState(GameStateManager gsm) {
		super(gsm);
	}

	public void render(Bitmap render) {
		render.clear(Color.BLACK);
	}

	public void update() {}

}

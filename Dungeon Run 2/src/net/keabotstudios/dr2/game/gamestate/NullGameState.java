package net.keabotstudios.dr2.game.gamestate;

import java.awt.Color;

import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public class NullGameState extends GameState {

	public NullGameState(GameStateManager gsm) {
		super(gsm);
	}

	public void render(Bitmap render) {
		render.clear(Color.BLACK);
	}

	public void update(Input input) {}

}

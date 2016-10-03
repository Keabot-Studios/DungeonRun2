package net.keabotstudios.dr2.game.gamestate;

import net.keabotstudios.dr2.gfx.Bitmap;

public abstract class GameState {

	protected final GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public abstract void render(Bitmap bitmap);

	public abstract void update();

}

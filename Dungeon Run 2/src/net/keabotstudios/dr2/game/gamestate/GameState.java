package net.keabotstudios.dr2.game.gamestate;

import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public abstract class GameState {

	protected final GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public abstract void render(Bitmap bitmap);

	public abstract void update(Input input);

}

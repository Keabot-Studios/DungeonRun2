package net.keabotstudios.dr2.game.gamestate;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.gfx.Bitmap;

public class GameStateManager {

	public final Game game;
	public GameState currentState;
	public final GameState nullState = new NullGameState(this);

	public GameStateManager(Game game) {
		this.game = game;
	}

	public void update() {
		if (currentState != null)
			currentState.update();
		else
			nullState.update();
	}

	public void render(Bitmap render) {
		if (currentState != null)
			currentState.render(render);
		else
			nullState.render(render);
	}

	public void setState(GameState state) {
		this.currentState = state;
	}

	public GameState getCurrentState(GameState state) {
		return currentState;
	}

}

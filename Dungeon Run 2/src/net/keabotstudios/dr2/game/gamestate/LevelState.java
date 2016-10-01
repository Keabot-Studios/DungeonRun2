package net.keabotstudios.dr2.game.gamestate;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Bitmap3D;
import net.keabotstudios.superin.Input;

public class LevelState extends GameState {
	
	private Level level;
	private Bitmap3D bitmap3d;

	public LevelState(GameStateManager gsm, Level level) {
		super(gsm);
		this.level = level;
		this.bitmap3d = new Bitmap3D(GameInfo.GAME_WIDTH, GameInfo.GAME_HEIGHT);
	}

	public void render(Bitmap bitmap) {
		bitmap3d.renderLevel(level);
		bitmap3d.renderDistanceLimiter(level.getRenderDistance());
		bitmap.render(bitmap3d, 0, 0);
	}

	public void update(Input input) {
		level.update(input);
		bitmap3d.setOffsets(level.getPlayer());
	}

}

package net.keabotstudios.dr2.game.gamestate;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Render3D;
import net.keabotstudios.dr2.gfx.RenderMinimap;
import net.keabotstudios.superin.Input;

public class LevelState extends GameState {
	
	private Level level;
	private Render3D render3d;
	private RenderMinimap minimap;

	public LevelState(GameStateManager gsm, Level level) {
		super(gsm);
		this.level = level;
		this.render3d = new Render3D(GameInfo.GAME_WIDTH, GameInfo.GAME_HEIGHT);
		this.minimap = new RenderMinimap(50, 50);
	}

	public void render(Render render) {
		render3d.renderLevel(level);
		render3d.renderDistanceLimiter(level.getRenderDistance());
		render.render(render3d, 0, 0);
	}

	public void update(Input input) {
		level.update(input);
		render3d.setOffsets(level.getPlayer());
	}

}

package net.keabotstudios.dr2.game.gamestate;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.gui.GuiRenderer;
import net.keabotstudios.dr2.game.gui.GuiRenderer.GuiColor;
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
		int guiX = -6;
		int guiY = (int) (GameInfo.GAME_HEIGHT - (16.0 * 2.0) - 8.0 - level.getPlayer().getEyeHeight() * 3.0);
		if(guiY > GameInfo.GAME_HEIGHT - 38)
			guiY = GameInfo.GAME_HEIGHT - 38;
		GuiRenderer.renderStatBar(bitmap, "+", guiX, guiY, 1, (int) Math.round(Math.sin(GameInfo.TIME / 60.0) * 10.0) + 10, 20, GuiColor.ORANGE, GuiColor.RED, GuiColor.RED);
		GuiRenderer.renderStatBar(bitmap, "$", guiX, guiY + 20, 1, (int) -Math.round(Math.sin(GameInfo.TIME / 60.0) * 10.0) + 10, 20, GuiColor.ORANGE, GuiColor.GREEN, GuiColor.GREEN);
		GuiRenderer.renderStatText(bitmap, "+", guiX, 70 + 6, 1, "" + GameInfo.FPS, GuiColor.GRAY, GuiColor.BLUE, GuiColor.BLUE);
	}

	public void update(Input input) {
		level.update(input);
		bitmap3d.setOffsets(level.getPlayer());
	}

}

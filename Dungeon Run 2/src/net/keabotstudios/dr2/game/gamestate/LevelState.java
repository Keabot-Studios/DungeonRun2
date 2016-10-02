package net.keabotstudios.dr2.game.gamestate;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.gui.Gui;
import net.keabotstudios.dr2.game.gui.Gui.GuiColor;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Bitmap3D;
import net.keabotstudios.superin.Input;

public class LevelState extends GameState {

	private Level level;
	private Bitmap3D bitmap3d;
	private int testNum;

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
		int guiY = GameInfo.GAME_HEIGHT - (16 * 2) - 8;
		Gui.renderGuiBar(bitmap, "+", guiX, guiY, 1, (int) Math.round(Math.sin(GameInfo.TIME / 60.0) * 10.0) + 10, 20,
				GuiColor.ORANGE, GuiColor.RED, GuiColor.RED);
		Gui.renderGuiBar(bitmap, "$", guiX, guiY + 20, 1, (int) -Math.round(Math.sin(GameInfo.TIME / 60.0) * 10.0) + 10,
				20, GuiColor.ORANGE, GuiColor.GREEN, GuiColor.GREEN);
		Gui.renderLabel(bitmap, "+" + Integer.toString(testNum) + "/" + Integer.toString((int) Math.pow(testNum, 1.01)), guiX,
				guiY - 20, 1, GuiColor.GRAY, GuiColor.RED);
		Gui.renderLabel(bitmap, (int)level.getPlayer().getPos().getX() + "." + (int)level.getPlayer().getPos().getZ(), guiX,
				20, 1, GuiColor.GRAY, GuiColor.BLUE);
	}

	public void update(Input input) {
		level.update(input);
		bitmap3d.setOffsets(level.getPlayer());
		testNum++;
	}

}

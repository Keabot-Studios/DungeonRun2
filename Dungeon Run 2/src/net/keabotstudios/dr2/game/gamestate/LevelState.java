package net.keabotstudios.dr2.game.gamestate;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.keabotstudios.dr2.game.Direction;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.gui.GuiRenderer;
import net.keabotstudios.dr2.game.gui.GuiRenderer.GuiBarColor;
import net.keabotstudios.dr2.game.gui.GuiStatBar;
import net.keabotstudios.dr2.game.gui.GuiStatText;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.Minimap;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Bitmap3D;

public class LevelState extends GameState {

	private Level level;
	private Minimap minimap;
	private Bitmap3D bitmap3d;

	private GuiStatBar health, ammo;
	private GuiStatText fps, pos, rot, dir;

	public LevelState(GameStateManager gsm, Level level) {
		super(gsm);
		this.level = level;
		this.minimap = new Minimap(level, GameInfo.GAME_WIDTH - 110, 10, 100, 100, 0.5f);
		bitmap3d = new Bitmap3D(GameInfo.GAME_WIDTH, GameInfo.GAME_HEIGHT);
		int guiX = -6;
		int guiY = (int) (GameInfo.GAME_HEIGHT - (16.0 * 2.0) - 8.0);
		health = new GuiStatBar(guiX, guiY, 1, GuiRenderer.HEALTH_CHAR, 10, 20, GuiBarColor.ORANGE, GuiBarColor.RED, GuiBarColor.RED);
		ammo = new GuiStatBar(guiX, guiY + 20, 1, GuiRenderer.AMMO_CHAR, 10, 20, GuiBarColor.ORANGE, GuiBarColor.GREEN, GuiBarColor.GREEN);

		fps = new GuiStatText(guiX, 6, 1, GuiRenderer.FPS_CHAR, "" + GameInfo.FPS, GuiBarColor.GRAY, GuiBarColor.BLUE, GuiBarColor.BLUE);
		pos = new GuiStatText(guiX, 6 + 20, 1, GuiRenderer.POS_CHAR, "0.0,0.0,0.0", GuiBarColor.GRAY, GuiBarColor.BLUE, GuiBarColor.BLUE);
		rot = new GuiStatText(guiX, 6 + 40, 1, GuiRenderer.ROT_CHAR, "0.0", GuiBarColor.GRAY, GuiBarColor.BLUE, GuiBarColor.BLUE);
		dir = new GuiStatText(guiX, 6 + 60, 1, GuiRenderer.DIR_CHAR, Direction.UNKNOWN.getId() + "," + Direction.UNKNOWN.name(), GuiBarColor.GRAY, GuiBarColor.BLUE, GuiBarColor.BLUE);
	}

	public void render(Bitmap bitmap) {
		bitmap3d.renderLevel(level);
		bitmap3d.renderDistanceLimiter(level.getRenderDistance());
		bitmap.render(bitmap3d, 0, 0);
		health.render(bitmap);
		ammo.render(bitmap);
		if (gsm.game.getSettings().debugMode) {
			fps.render(bitmap);
			pos.render(bitmap);
			rot.render(bitmap);
			dir.render(bitmap);
		}
		minimap.render(bitmap);
	}

	public void update() {
		int guiX = -6;
		int guiY = (int) (GameInfo.GAME_HEIGHT - (16.0 * 2.0) - 8.0 + this.level.getPlayer().getEyeHeight() * 3.0);
		if (guiY > GameInfo.GAME_HEIGHT - 40) {
			guiY = GameInfo.GAME_HEIGHT - 40;
		}
		health.setX(guiX);
		health.setY(guiY);
		health.setValue((int) Math.round(Math.sin(GameInfo.TIME / 10.0) * 10.0) + 10);
		ammo.setX(guiX);
		ammo.setY(guiY + 20);
		ammo.setValue((int) -Math.round(Math.sin(GameInfo.TIME / 10.0) * 10.0) + 10);

		if (gsm.game.getSettings().debugMode) {
			fps.setText("" + GameInfo.FPS);
			int playerX = (int) Math.round(level.getPlayer().getPos().getX());
			int playerY = (int) Math.round(level.getPlayer().getPos().getY());
			int playerZ = (int) Math.round(level.getPlayer().getPos().getZ());
			pos.setText(playerX + "," + playerY + "," + playerZ);

			double playerRot = level.getPlayer().getRotation();
			BigDecimal pr = new BigDecimal(playerRot).setScale(2, RoundingMode.HALF_EVEN);
			rot.setText("" + pr.floatValue());

			Direction pdir = Direction.getFromRad(level.getPlayer().getRotation());
			dir.setText(pdir.getId() + "," + pdir.name());
		}
		level.update(gsm.game.getInput());
		minimap.update();
		bitmap3d.setOffsets(level.getPlayer());
	}

}

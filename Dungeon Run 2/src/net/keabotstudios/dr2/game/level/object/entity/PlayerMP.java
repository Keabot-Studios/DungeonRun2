package net.keabotstudios.dr2.game.level.object.entity;

import java.awt.Color;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.game.Direction;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.gfx.AnimatedBitmap;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.dr2.math.Vector3;
import net.keabotstudios.superin.Input;

public class PlayerMP extends Entity {

	private AnimatedBitmap[] textures;

	private static final int STANDING_DELAY = GameInfo.MAX_UPS;
	private static final int WALKING_DELAY = GameInfo.MAX_UPS / 4;
	private static final int STANDING = 0;
	private static final int WALKING = 1;

	private int animation = WALKING;
	private int currentTexture = 0;

	private String playerName;
	private long playerID;
	private int health = Player.MAX_HEALTH;

	public PlayerMP(Vector3 pos, double rot, long playerID, String playerName) {
		super(pos, new CollisionBox(0.8, 1.5, 0.8), rot, ColorUtil.toARGBColor(Color.CYAN));
		int numTextures = 4 * 2;
		textures = new AnimatedBitmap[numTextures];
		for (int i = 0; i < numTextures; i++) {
			Bitmap[] texture = new Bitmap[4];
			for (int j = 0; j < 4; j++) {
				texture[j] = Texture.player[j + i * 4];
			}
			int delay = (i % 2 == 0 ? STANDING_DELAY : WALKING_DELAY);
			textures[i] = new AnimatedBitmap(texture, delay);
		}
		this.playerID = playerID;
		this.playerName = playerName;
		dx = 0.02;
	}

	final int WAIT_TIME = 60;
	int waitTimer = 0;
	int lastWaitTimer = 0;
	boolean hasWaited = false;

	public void update(Input input, Level level) {
		currentTexture = (int) Direction.getCardinalFromRad(rot + level.getPlayer().rot).getId() * 2 + animation;
		((AnimatedBitmap) getTexture()).update();

		if (isFree(pos.getX() + dx, pos.getZ(), level)) {
			pos.setX(pos.getX() + dx);
			animation = WALKING;
		} else {
			animation = STANDING;
			if (waitTimer == 0 && lastWaitTimer == 0 && !hasWaited) {
				waitTimer = WAIT_TIME;
				hasWaited = true;
			}
			if (waitTimer == 0 && lastWaitTimer == 0 && hasWaited) {
				hasWaited = false;
				dx = -dx;
			} else {
				waitTimer--;
			}
			lastWaitTimer = waitTimer;
		}

		super.update(input, level);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Bitmap getTexture() {
		return textures[currentTexture];
	}

	private boolean isFree(double x, double z, Level level) {
		int x0 = (int) Math.floor(x + 0.5);
		int x1 = (int) Math.floor(x + 0.5 - collisionBox.getX());
		int z0 = (int) Math.floor(z + 0.5);
		int z1 = (int) Math.floor(z + 0.5 - collisionBox.getZ());

		if (level.getBlock(x0, z0).isSolid())
			return false;
		if (level.getBlock(x1, z0).isSolid())
			return false;
		if (level.getBlock(x0, z1).isSolid())
			return false;
		if (level.getBlock(x1, z1).isSolid())
			return false;
		return true;
	}

	public long getPlayerID() {
		return playerID;
	}

}

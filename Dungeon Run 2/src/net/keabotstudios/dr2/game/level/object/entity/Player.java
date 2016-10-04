package net.keabotstudios.dr2.game.level.object.entity;

import java.awt.Color;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.game.level.object.Vector3;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.superin.Input;

public class Player extends Entity {

	public static final double ROT_SPEED = 0.01;
	public static final double MOUSE_ROT_SPEED = 0.01;
	public static final double WALK_SPEED = 0.5 / 8.0;
	public static final double BOB_MAGNITUTDE = 0.1;
	public static final double STRAFE_WALK_SPEED = 1 / Math.sqrt(2);
	public static final double RUN_SPEED = 1.8;
	public static final double CROUCH_HEIGHT = 0.5;
	public static final double CROUCH_SPEED = 0.5;
	public static final double JUMP_HEIGHT = 1;
	public static final double GRAVITY = 0.002;

	private Game game;

	public Player(Vector3 pos, double rot, String name, Game game) {
		super(pos.clone(), new CollisionBox(0.8, 0.8, 1.5), rot, name, Color.GREEN.getRGB());
		this.game = game;
	}

	private double newMX, oldMX, xa, za, moveSpeed, eyeHeight;
	private boolean walking, running, crouching, jumping;

	public void update(Input input, Level level) {
		dx = 0;
		dz = 0;
		moveSpeed = (running ? RUN_SPEED : 1) * (crouching ? CROUCH_SPEED : 1);

		updateInput(input);

		xa += (dx * Math.cos(rot) + dz * Math.sin(rot)) * WALK_SPEED;
		za += (dz * Math.cos(rot) - dx * Math.sin(rot)) * WALK_SPEED;

		if (isFree(pos.getX() + xa, pos.getZ(), level)) {
			pos.setX(pos.getX() + xa);
		}
		if (isFree(pos.getX(), pos.getZ() + za, level)) {
			pos.setZ(pos.getZ() + za);
		}
		
		if(pos.getY() > 0) {
			dy -= GRAVITY;
			pos.setY(pos.getY() + dy);
			jumping = true;
		} else {
			jumping = false;
			pos.setY(0);
			dy = 0;
		}

		eyeHeight *= 0.9;
		xa *= 0.1;
		za *= 0.1;
		rot += dRot;
		dRot *= 0.8;
		super.update(input, level);
	}

	private void updateInput(Input input) {
		walking = input.getInput("FORWARD") || input.getInput("BACKWARD") || input.getInput("STRAFE_LEFT")
				|| input.getInput("STRAFE_RIGHT");
		if (input.getInputTapped("CROUCH"))
			crouching = !crouching;
		if (!crouching && input.getInputTapped("RUN"))
			running = !running;
		if (crouching)
			running = false;
		if(input.getInputTapped("JUMP") && !jumping) {
			jumping = true;
			dy += JUMP_HEIGHT;
		}

		boolean strafing = input.getInput("STRAFE_LEFT") || input.getInput("STRAFE_RIGHT");

		double zModifier = (strafing ? STRAFE_WALK_SPEED : 1) * moveSpeed;
		if (input.getInput("FORWARD")) {
			dz += 1.0 * Math.abs(input.getInputValue("FORWARD")) * zModifier;
		} else if (input.getInput("BACKWARD")) {
			dz -= 1.0 * Math.abs(input.getInputValue("BACKWARD")) * zModifier;
		}

		if (input.getInput("STRAFE_LEFT")) {
			dx -= 1.0 * Math.abs(input.getInputValue("STRAFE_LEFT")) * moveSpeed;
		} else if (input.getInput("STRAFE_RIGHT")) {
			dx += 1.0 * Math.abs(input.getInputValue("STRAFE_RIGHT")) * moveSpeed;
		}

		newMX = input.getMouseX();
		if (newMX > oldMX && game.getSettings().mouseTurning) {
			dRot += MOUSE_ROT_SPEED;
		} else if (newMX < oldMX && game.getSettings().mouseTurning) {
			dRot -= MOUSE_ROT_SPEED;
		} else if (input.getInput("TURN_LEFT")) {
			dRot -= ROT_SPEED * Math.abs(input.getInputValue("TURN_LEFT"));
		} else if (input.getInput("TURN_RIGHT")) {
			dRot += ROT_SPEED * Math.abs(input.getInputValue("TURN_RIGHT"));
		} else if ((newMX > oldMX || newMX < oldMX) && game.getSettings().mouseTurning) {
			dRot += MOUSE_ROT_SPEED * (newMX - oldMX);
		}
		oldMX = newMX;
		if (walking && game.getSettings().enableBobbing) {
			eyeHeight += Math.sin(GameInfo.TIME / 6.0) * BOB_MAGNITUTDE * (crouching ? 0.3 : 1);
		}

		if (crouching) {
			eyeHeight -= CROUCH_HEIGHT;
		}
	}

	public double getEyeHeight() {
		return eyeHeight;
	}

	public boolean isWalking() {
		return walking;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isCrouching() {
		return crouching;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}

	public Bitmap getTexture() {
		return null;
	}

	private boolean isFree(double x, double z, Level level) {
		int x0 = (int) Math.floor(x + 0.5);
		int x1 = (int) Math.floor(x + 0.5 - collisionBox.getX());
		int z0 = (int) Math.floor(z + 0.5);
		int z1 = (int) Math.floor(z + 0.5 - collisionBox.getY());

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

	public Game getCurrentGame() {
		return game;
	}
}

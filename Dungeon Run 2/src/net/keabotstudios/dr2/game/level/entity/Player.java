package net.keabotstudios.dr2.game.level.entity;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.GameSettings;
import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.superin.Input;

public class Player extends Entity {

	public static final double ROT_SPEED = 0.005;
	public static final double MOUSE_ROT_SPEED = 0.0005;
	public static final double WALK_SPEED = 4.0;
	public static final double BOB_MAGNITUTDE = 0.1;
	public static final double STRAFE_WALK_SPEED = 1 / Math.sqrt(2);
	public static final double RUN_SPEED = 1.8;
	public static final double CROUCH_HEIGHT = 0.5;
	public static final double CROUCH_SPEED = 0.5;
	
	private GameSettings settings;

	public Player(double x, double z, double rot, String name, GameSettings settings) {
		super(x, 0, z, rot, name);
		this.settings = settings;
	}

	private double newMX, oldMX, xa, za, moveSpeed;
	private boolean walking, running, crouching;

	public void update(Input input) {
		dz = 0;
		dx = 0;
		moveSpeed = (running ? RUN_SPEED : 1) * (crouching ? CROUCH_SPEED : 1);

		walking = input.getInput("FORWARD") || input.getInput("BACKWARD") || input.getInput("STRAFE_LEFT") || input.getInput("STRAFE_RIGHT");
		if (input.getInputTapped("CROUCH"))
			crouching = !crouching;
		if (!crouching)
			running = input.getInput("RUN");
		if (crouching)
			running = false;

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
			if (input.getInput("TURN_LEFT")) {
				dRot -= ROT_SPEED * Math.abs(input.getInputValue("TURN_LEFT"));
			} else if (input.getInput("TURN_RIGHT")) {
				dRot += ROT_SPEED * Math.abs(input.getInputValue("TURN_RIGHT"));
			} else if ((newMX > oldMX || newMX < oldMX) && settings.mouseTurning) {
				dRot += MOUSE_ROT_SPEED * (newMX - oldMX);
			}
			oldMX = newMX;
		if (walking && settings.enableBobbing) {
			y += Math.sin(GameInfo.TIME / 6.0) * BOB_MAGNITUTDE * (crouching ? 0.3 : 1);
		}

		if (settings.debugMode && input.getInput("F1")) {
			y += 0.5;
		}

		if (settings.debugMode && input.getInputTapped("F5")) {
			x = 0;
			y = 0;
			z = 0;
			xa = 0;
			za = 0;
			rot = 0;
			dRot = 0;
			return;
		}

		if (crouching) {
			y -= CROUCH_HEIGHT;
		}

		xa += (dx * Math.cos(rot) + dz * Math.sin(rot)) * WALK_SPEED;
		za += (dz * Math.cos(rot) - dx * Math.sin(rot)) * WALK_SPEED;

		x += xa;
		y *= 0.9;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rot += dRot;
		dRot *= 0.8;
		super.update(input);
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

	public void render(Render render) {}

}

package net.keabotstudios.dr2.game.entity;

import net.keabotstudios.dr2.Display;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.superin.Input;

public class Player extends Entity {
	
	private static final double ROTSPEED = 0.005;
	private static final double MOUSEROTSPEED = 0.0005 / (Display.SCALE / 2.0);
	private static final double WALKSPEED = 0.5;
	private static final double STRAFEWALKSPEED = 1 / Math.sqrt(2);
	private static final double RUNSPEED = 1.5;
	private static final double CROUCHHEIGHT = 0.5;
	private static final double CROUCHSPEED = 0.5;

	public Player(double x, double z, double rot, String name) {
		super(x, 0, z, rot, name);
	}
	
	private double newMX, oldMX, xa, za;
	private boolean walking, running, crouching;
	
	public void update(Input input) {
		dz = 0;
		dx = 0;
		
		walking = input.getInput("FORWARD") || input.getInput("BACKWARD") || input.getInput("STRAFE_LEFT") || input.getInput("STRAFE_RIGHT");
		if(input.getInputTapped("CROUCH")) crouching = !crouching;
		if(input.getInputTapped("RUN") && !crouching) running = !running;
		if(crouching) running = false;
		
		boolean strafing = input.getInput("STRAFE_LEFT") || input.getInput("STRAFE_RIGHT");
		
		double zModifier = (strafing ? STRAFEWALKSPEED : 1) * (running ? RUNSPEED : 1) * (crouching ? CROUCHSPEED : 1);
		if (input.getInput("FORWARD")) {
			dz += 1.0 * Math.abs(input.getInputValue("FORWARD")) * zModifier;
		} else if (input.getInput("BACKWARD")) {
			dz -= 1.0 * Math.abs(input.getInputValue("BACKWARD")) * zModifier;
		}
		
		double xModifier = (running ? RUNSPEED : 1) * (crouching ? CROUCHSPEED : 1);
		if (input.getInput("STRAFE_LEFT")) {
			dx -= 1.0 * Math.abs(input.getInputValue("STRAFE_LEFT")) * xModifier;
		} else if (input.getInput("STRAFE_RIGHT")) {
			dx += 1.0 * Math.abs(input.getInputValue("STRAFE_RIGHT")) * xModifier;
		}
		
		
		newMX = input.getMouseX();
		if (newMX > oldMX) {
			dRot += MOUSEROTSPEED * (newMX - oldMX);
		} else if (newMX < oldMX) {
			dRot += MOUSEROTSPEED * (newMX - oldMX);
		} else if (input.getInput("TURN_LEFT")) {
			dRot -= ROTSPEED * Math.abs(input.getInputValue("TURN_LEFT"));
		} else if (input.getInput("TURN_RIGHT")) {
			dRot += ROTSPEED * Math.abs(input.getInputValue("TURN_RIGHT"));
		}
		oldMX = newMX;
		
		if(walking) {
			y += Math.sin(GameInfo.TIME / 6.0) * 0.2 * (crouching ? 0.3 : 1);
		}

		if (GameInfo.DEBUG_MODE && input.getInputTapped("F1")) {
			x = 0;
			y = 0;
			z = 0;
			xa = 0;
			za = 0;
			rot = 0;
			dRot = 0;
			return;
		}
		
		if(crouching) {
			y -= CROUCHHEIGHT ;
		}

		xa += (dx * Math.cos(rot) + dz * Math.sin(rot)) * WALKSPEED;
		za += (dz * Math.cos(rot) - dx * Math.sin(rot)) * WALKSPEED;

		x += xa;
		y *= 0.9;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rot += dRot;
		dRot *= 0.8;
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

}

package net.keabotstudios.dr2;

import net.keabotstudios.superin.Input;

public class Controller {

	public double x, z, rot, dx, dz, drot, xa, za, newMX, oldMX;

	private static final double ROTSPEED = 0.005;
	private static final double MOUSEROTSPEED = 0.0005 / (Display.SCALE / 2.0);
	private static final double WALKSPEED = 0.5;
	private static final double STRAFEWALKSPEED = 1 / Math.sqrt(2);

	public void update(Input input) {
		dz = 0;
		dx = 0;
		boolean strafing = input.getInput("STRAFE_LEFT") || input.getInput("STRAFE_RIGHT");
		if (input.getInput("FORWARD")) {
			dz += 1.0 * Math.abs(input.getInputValue("FORWARD")) * (strafing ? STRAFEWALKSPEED : 1);
		}
		if (input.getInput("BACKWARD")) {
			dz -= 1.0 * Math.abs(input.getInputValue("BACKWARD")) * (strafing ? STRAFEWALKSPEED : 1);
		}

		if (input.getInput("STRAFE_LEFT")) {
			dx -= 1.0 * Math.abs(input.getInputValue("STRAFE_LEFT"));
		}
		if (input.getInput("STRAFE_RIGHT")) {
			dx += 1.0 * Math.abs(input.getInputValue("STRAFE_RIGHT"));
		}
		
		newMX = input.getMouseX();
		if(newMX > oldMX) {
			drot += MOUSEROTSPEED * (newMX - oldMX);
		} else if(newMX < oldMX) {
			drot += MOUSEROTSPEED * (newMX - oldMX);
		} else if (input.getInput("TURN_LEFT")) {
			drot -= ROTSPEED * Math.abs(input.getInputValue("TURN_LEFT"));
		} else if (input.getInput("TURN_RIGHT")) {
			drot += ROTSPEED * Math.abs(input.getInputValue("TURN_RIGHT"));
		}
		oldMX = newMX;

		if (GameInfo.DEBUG_MODE && input.getInput("F1")) {
			x = 0;
			z = 0;
			xa = 0;
			za = 0;
			rot = 0;
			drot = 0;
			return;
		}

		xa += (dx * Math.cos(rot) + dz * Math.sin(rot)) * WALKSPEED;
		za += (dz * Math.cos(rot) - dx * Math.sin(rot)) * WALKSPEED;

		x += xa;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rot += drot;
		drot *= 0.8;
	}

}

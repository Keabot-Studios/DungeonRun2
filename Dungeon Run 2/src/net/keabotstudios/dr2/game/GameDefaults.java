package net.keabotstudios.dr2.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.java.games.input.Component;
import net.keabotstudios.superin.InputAxis;

public class GameDefaults {

	public static final boolean DEBUG_MODE = false, MOUSE_TURNING = true, FULLSCREEN = false, ENABLE_BOBBING = true, USE_XINPUT = true;
	private static final float DEADZONE = 0.125f;
	public static InputAxis[] CONTROLS = new InputAxis[] {
			new InputAxis("FORWARD", KeyEvent.VK_W, Component.Identifier.Axis.Y, -DEADZONE, InputAxis.EMPTY),
			new InputAxis("BACKWARD", KeyEvent.VK_S, Component.Identifier.Axis.Y, DEADZONE, InputAxis.EMPTY),
			new InputAxis("STRAFE_LEFT", KeyEvent.VK_A, Component.Identifier.Axis.X, -DEADZONE, InputAxis.EMPTY),
			new InputAxis("STRAFE_RIGHT", KeyEvent.VK_D, Component.Identifier.Axis.X, DEADZONE, InputAxis.EMPTY),
			new InputAxis("TURN_LEFT", KeyEvent.VK_Q, Component.Identifier.Axis.RX, -DEADZONE, InputAxis.EMPTY),
			new InputAxis("TURN_RIGHT", KeyEvent.VK_E, Component.Identifier.Axis.RX, DEADZONE, InputAxis.EMPTY),
			new InputAxis("RUN", KeyEvent.VK_SHIFT, Component.Identifier.Button._3, 1.0f, InputAxis.EMPTY),
			new InputAxis("CROUCH", KeyEvent.VK_CONTROL, Component.Identifier.Button._2, 1.0f, InputAxis.EMPTY),
			new InputAxis("SHOOT", InputAxis.EMPTY, Component.Identifier.Button._6, 1.0f, MouseEvent.BUTTON1),
			new InputAxis("JUMP", KeyEvent.VK_SPACE, Component.Identifier.Button._1, 1.0f, MouseEvent.BUTTON1),
			new InputAxis("ACTION", KeyEvent.VK_ENTER, Component.Identifier.Button._0, 1.0f, MouseEvent.BUTTON3),
			new InputAxis("RELOAD", KeyEvent.VK_R, Component.Identifier.Button._4, 1.0f, InputAxis.EMPTY),
			new InputAxis("MENU_UP", KeyEvent.VK_UP, Component.Identifier.Axis.Y, -DEADZONE, InputAxis.EMPTY),
			new InputAxis("MENU_DOWN", KeyEvent.VK_DOWN, Component.Identifier.Axis.Y, DEADZONE, InputAxis.EMPTY),
			new InputAxis("MENU_LEFT", KeyEvent.VK_LEFT, Component.Identifier.Axis.X, -DEADZONE, InputAxis.EMPTY),
			new InputAxis("MENU_RIGHT", KeyEvent.VK_RIGHT, Component.Identifier.Axis.X, DEADZONE, InputAxis.EMPTY),
			new InputAxis("MENU_CONFIRM", KeyEvent.VK_ENTER, Component.Identifier.Button._0, 1.0f, MouseEvent.BUTTON1),
			new InputAxis("ESCAPE", KeyEvent.VK_ESCAPE),
			new InputAxis("F1", KeyEvent.VK_F1),
			new InputAxis("F2", KeyEvent.VK_F2),
			new InputAxis("F3", KeyEvent.VK_F3),
			new InputAxis("F4", KeyEvent.VK_F4),
			new InputAxis("F5", KeyEvent.VK_F5),
			new InputAxis("F6", KeyEvent.VK_F6)
	};
	public static final int WINDOW_INDEX = 0;

}

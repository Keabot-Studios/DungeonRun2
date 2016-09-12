package net.keabotstudios.dr2.game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.keabotstudios.dr2.Display;
import net.keabotstudios.dr2.Util;
import net.keabotstudios.superin.InputAxis;

public class GameInfo {

	public static boolean DEBUG_MODE = false;
	public static long TIME;
	public static final ArrayList<Image> WINDOW_ICONS = new ArrayList<Image>();
	public static InputAxis[] CONTROLS;

	public static void update() {
		TIME++;
	}

	private static float DEADZONE = 0.125f;

	public static void init(Display display) {
		WINDOW_ICONS.add(Util.loadImage("/icon/dr2x64.png", display.getLogger()));
		WINDOW_ICONS.add(Util.loadImage("/icon/dr2x32.png", display.getLogger()));
		WINDOW_ICONS.add(Util.loadImage("/icon/dr2x16.png", display.getLogger()));
		TIME = 0;
		CONTROLS = new InputAxis[] {
				new InputAxis("FORWARD", KeyEvent.VK_W, Component.Identifier.Axis.Y, -DEADZONE, InputAxis.EMPTY),
				new InputAxis("BACKWARD", KeyEvent.VK_S, Component.Identifier.Axis.Y, DEADZONE, InputAxis.EMPTY),
				new InputAxis("STRAFE_LEFT", KeyEvent.VK_A, Component.Identifier.Axis.X, -DEADZONE, InputAxis.EMPTY),
				new InputAxis("STRAFE_RIGHT", KeyEvent.VK_D, Component.Identifier.Axis.X, DEADZONE, InputAxis.EMPTY),
				new InputAxis("TURN_LEFT", InputAxis.EMPTY, Component.Identifier.Axis.RX, -DEADZONE, InputAxis.EMPTY),
				new InputAxis("TURN_RIGHT", InputAxis.EMPTY, Component.Identifier.Axis.RX, DEADZONE, InputAxis.EMPTY),
				new InputAxis("RUN", KeyEvent.VK_CONTROL, Component.Identifier.Button._9, 1.0f, InputAxis.EMPTY),
				new InputAxis("CROUCH", KeyEvent.VK_SHIFT, Component.Identifier.Button._2, 1.0f, InputAxis.EMPTY),
				new InputAxis("SHOOT", KeyEvent.VK_SPACE, Component.Identifier.Button._6, 0.2f, MouseEvent.BUTTON1),
				new InputAxis("ACTION", InputAxis.EMPTY, Component.Identifier.Button._0, 1.0f, MouseEvent.BUTTON3),
				new InputAxis("RELOAD", KeyEvent.VK_R, Component.Identifier.Button._4, 1.0f, InputAxis.EMPTY),
				new InputAxis("F1", KeyEvent.VK_F1),
				new InputAxis("F2", KeyEvent.VK_F2),
				new InputAxis("F3", KeyEvent.VK_F3),
				new InputAxis("F4", KeyEvent.VK_F4),
				new InputAxis("F5", KeyEvent.VK_F5),
				new InputAxis("F6", KeyEvent.VK_F6),
				new InputAxis("ESCAPE", KeyEvent.VK_ESCAPE)
		};
	}

}

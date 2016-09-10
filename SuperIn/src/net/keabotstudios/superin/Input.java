package net.keabotstudios.superin;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Input implements KeyListener, MouseMotionListener, MouseListener, FocusListener {
		
	private static final int NUM_KEYS = KeyEvent.KEY_LAST;
	private static final int NUM_MBTNS = MouseEvent.MOUSE_LAST;

	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] lastKeys = new boolean[NUM_KEYS];
	private boolean[] mouseButtons = new boolean[NUM_MBTNS];
	private boolean[] lastMouseButtons = new boolean[NUM_MBTNS];
	private int mouseX = 0, mouseY = 0;

	private Controller activeController = null;
	private HashMap<Component.Identifier, Float> controllerAxes = new HashMap<Component.Identifier, Float>();
	private HashMap<Component.Identifier, Float> lastControllerAxes = new HashMap<Component.Identifier, Float>();

	private boolean hasFocus = false;
	private boolean useXInputController = false;

	private Controllable controllable;

	private InputAxis[] inputAxes;

	public Input(Controllable contollable, boolean useXInputController) {
		this.useXInputController = useXInputController;
		setControllable(contollable);

		scanControllers();
	}

	private void scanControllers() {
		if (usingController() || !useXInputController)
			return;
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (int i = 0; i < ca.length; i++) {
			if (ca[i].getType() == Controller.Type.GAMEPAD) {
				activeController = ca[i];
				break;
			}
		}

		if (!usingController()) {
			controllable.getLogger().info("No gamepad detected.");
		} else {
			controllable.getLogger().info("Gamepad detected: " + activeController.getName());
			Component[] components = activeController.getComponents();
			for (Component c : components) {
				controllerAxes.put(c.getIdentifier(), 0.0f);
				lastControllerAxes.put(c.getIdentifier(), 0.0f);
			}
		}
	}

	public void setControllable(Controllable controllable) {
		if (this.controllable != null) {
			this.controllable.getComponent().removeKeyListener(this);
			this.controllable.getComponent().removeMouseMotionListener(this);
			this.controllable.getComponent().removeMouseListener(this);
			this.controllable.getComponent().removeFocusListener(this);
		}
		this.controllable = controllable;
		this.controllable.getComponent().addKeyListener(this);
		this.controllable.getComponent().addMouseMotionListener(this);
		this.controllable.getComponent().addMouseListener(this);
		this.controllable.getComponent().addFocusListener(this);
	}

	public void setInputs(InputAxis[] inputAxes) {
		this.inputAxes = inputAxes;
	}

	public void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (lastKeys[i] != keys[i]) {
				lastKeys[i] = keys[i];
			}
		}

		for (int i = 0; i < NUM_MBTNS; i++) {
			if (lastMouseButtons[i] != mouseButtons[i]) {
				lastMouseButtons[i] = mouseButtons[i];
			}
		}
		if (usingController() && !activeController.poll()) {
			activeController = null;
			controllable.getLogger().info("Controller disconnected.");
		}
		if (usingController()) {
			scanControllers();
			activeController.poll();
			EventQueue queue = activeController.getEventQueue();
			Event event = new Event();
			while (queue.getNextEvent(event)) {
				Component comp = event.getComponent();
				float value = event.getValue();
				controllerAxes.replace(comp.getIdentifier(), value);
			}
			for (Component comp : activeController.getComponents()) {
				if (lastControllerAxes.get(comp.getIdentifier()) != controllerAxes.get(comp.getIdentifier())) {
					lastControllerAxes.replace(comp.getIdentifier(), controllerAxes.get(comp.getIdentifier()));
				}
			}
		}

	}

	public boolean getInput(String name) {
		InputAxis axis = getInputAxisFromName(name);
		if (!hasFocus)
			return false;
		if (axis.getKeyCode() != InputAxis.EMPTY) {
			if (keys[axis.getKeyCode()])
				return true;
		}
		if (axis.getMouseCode() != InputAxis.EMPTY) {
			if (mouseButtons[axis.getMouseCode()])
				return true;
		}
		if (usingController()) {
			float value = 0.0f;
			if (axis.getIdentifier() != null) {
				value = controllerAxes.get(axis.getIdentifier()).floatValue();
				if (axis.getActZone() > 0) {
					if (value >= axis.getActZone())
						return true;
				} else {
					if (value <= axis.getActZone())
						return true;
				}

			}
		}
		return false;
	}

	public float getInputValue(String name) {
		InputAxis axis = getInputAxisFromName(name);
		if (!hasFocus)
			return 0.0f;
		if (axis.getKeyCode() != InputAxis.EMPTY) {
			if (keys[axis.getKeyCode()])
				return 1.0f;
		}
		if (axis.getMouseCode() != InputAxis.EMPTY) {
			if (mouseButtons[axis.getMouseCode()])
				return 1.0f;
		}
		if (usingController()) {
			if (axis.getIdentifier() != null) {
				return controllerAxes.get(axis.getIdentifier()).floatValue();
			}
		}
		return 0.0f;
	}

	public boolean getInputTapped(String name) {
		InputAxis axis = getInputAxisFromName(name);
		if (!hasFocus)
			return false;
		if (axis.getKeyCode() != InputAxis.EMPTY) {
			if (keys[axis.getKeyCode()] != lastKeys[axis.getKeyCode()] && keys[axis.getKeyCode()])
				return true;
		}
		if (axis.getMouseCode() != InputAxis.EMPTY) {
			if (mouseButtons[axis.getMouseCode()] != lastMouseButtons[axis.getMouseCode()] && mouseButtons[axis.getMouseCode()])
				return true;
		}
		if (usingController()) {
			float value = 0.0f;
			float lastValue = 0.0f;
			if (axis.getIdentifier() != null) {
				value = controllerAxes.get(axis.getIdentifier()).floatValue();
				lastValue = lastControllerAxes.get(axis.getIdentifier()).floatValue();
				if (axis.getActZone() >= 0) {
					if (lastValue != value && value > axis.getActZone())
						return true;
				} else {
					if (lastValue != value && value < axis.getActZone())
						return true;
				}

			}
		}
		return false;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() >= mouseButtons.length)
			return;
		mouseButtons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() >= mouseButtons.length)
			return;
		mouseButtons[e.getButton()] = false;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {}

	public void focusGained(FocusEvent e) {
		hasFocus = true;
	}

	public void focusLost(FocusEvent e) {
		for (int i = 0; i < NUM_KEYS; i++) {
			keys[i] = false;
		}
		for (int i = 0; i < NUM_MBTNS; i++) {
			mouseButtons[i] = false;
		}
		hasFocus = false;
	}

	public boolean usingController() {
		return activeController != null || !useXInputController;
	}

	public boolean hasFocus() {
		return hasFocus;
	}

	public InputAxis getInputAxisFromName(String name) {
		if (inputAxes != null && inputAxes.length != 0) {
			for (int i = 0; i < inputAxes.length; i++) {
				if (inputAxes[i].getName().equalsIgnoreCase(name)) {
					return inputAxes[i];
				}
			}
		}
		controllable.getLogger().fatal("Cannot get axis from name: " + name);
		System.exit(-1);
		return null;
	}

}

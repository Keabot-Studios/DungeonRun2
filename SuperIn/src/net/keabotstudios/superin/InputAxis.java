package net.keabotstudios.superin;

import net.java.games.input.Component.Identifier;

public class InputAxis {
	
	public static int EMPTY = -1;
	
	private final String name;
	private int keyCode = EMPTY, mouseCode = EMPTY;
	private Identifier identifier = null;
	private float actZone = 0.0f;

	public InputAxis(String name, int keyCode, Identifier identifier, float actZone, int mouseCode) {
		this.name = name;
		this.keyCode = keyCode;
		this.identifier = identifier;
		this.actZone = actZone;
		this.mouseCode = mouseCode;
	}

	public InputAxis(String name, int keyCode) {
		this.name = name;
		this.keyCode = keyCode;
	}

	public String getName() {
		return name;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public float getActZone() {
		return actZone;
	}

	public int getMouseCode() {
		return mouseCode;
	}

}

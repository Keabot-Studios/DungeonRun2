package net.keabotstudios.dr2.game;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import net.java.games.input.Component.Identifier;
import net.keabotstudios.superin.InputAxis;
import net.keabotstudios.superserial.containers.SSDatabase;
import net.keabotstudios.superserial.containers.SSField;
import net.keabotstudios.superserial.containers.SSObject;
import net.keabotstudios.superserial.containers.SSString;

public class GameSettings {

	public boolean debugMode, mouseTurning, fullscreen, enableBobbing, useXInput;
	public InputAxis[] controls;
	public int windowSizeIndex;
	public int windowWidth, windowHeight;
	
	public GameSettings() {
		controls = GameDefaults.CONTROLS.clone();
		windowSizeIndex = GameDefaults.WINDOW_INDEX;
		updateWindowSize();
		mouseTurning = GameDefaults.MOUSE_TURNING;
		fullscreen = GameDefaults.FULLSCREEN;
		enableBobbing = GameDefaults.ENABLE_BOBBING;
		useXInput = GameDefaults.USE_XINPUT;
	}
	
	public void updateWindowSize() {
		windowWidth = GameDefaults.WINDOW_WIDTHS[windowSizeIndex];
		windowHeight = windowWidth * 3 / 4;
	}

	public void updateSettingsFromFile() {
		if (!doSettingsExist())
			writeSettings();
		else
			readSettings();
	}

	public static boolean doSettingsExist() {
		String filePath = GameInfo.getAppdataFolderPath() + File.separator + "settings" + SSDatabase.FILE_EXTENTION;
		File file = new File(filePath);
		return file.exists();
	}

	public boolean writeSettings() {
		SSDatabase settings = new SSDatabase("settings");
		
		SSObject gameSettings = new SSObject("game");
		gameSettings.addField(SSField.Boolean("mouseTurning", this.mouseTurning));
		gameSettings.addField(SSField.Boolean("enableBobbing", this.enableBobbing));
		gameSettings.addField(SSField.Boolean("debugMode", this.debugMode));
		gameSettings.addField(SSField.Boolean("fullscreen", this.fullscreen));
		gameSettings.addField(SSField.Boolean("useXInput", this.useXInput));
		gameSettings.addField(SSField.Integer("windowSizeIndex", this.windowSizeIndex));
		settings.addObject(gameSettings);
		
		SSObject input = new SSObject("input");
		input.addField(SSField.Integer("numAxes", controls.length));
		for (int i = 0; i < controls.length; i++) {
			SSObject inputAxis = new SSObject("axis" + i);
			inputAxis.addString(new SSString("name", controls[i].getName()));
			Identifier id = controls[i].getIdentifier();
			if (id instanceof Identifier.Axis) {
				inputAxis.addString(new SSString("identifier", "Axis." + id.getName().toUpperCase()));
			} else if (id instanceof Identifier.Button) {
				inputAxis.addString(new SSString("identifier", "Button._" + id.getName()));
			} else if (id == null) {
				inputAxis.addString(new SSString("identifier", "null"));
			}
			inputAxis.addField(SSField.Float("actZone", controls[i].getActZone()));
			inputAxis.addField(SSField.Integer("keyCode", controls[i].getKeyCode()));
			inputAxis.addField(SSField.Integer("mouseCode", controls[i].getMouseCode()));
			input.addObject(inputAxis);
		}
		settings.addObject(input);

		String filePath = GameInfo.getAppdataFolderPath() + File.separator + "settings" + SSDatabase.FILE_EXTENTION;
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		try {
			byte[] data = new byte[settings.getSize()];
			settings.writeBytes(data, 0);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(filePath));
			stream.write(data);
			stream.close();
			System.out.println("Wrote settings successfully to: " + filePath);
			return true;
		} catch (IOException e) {
			System.err.println("Can't write settings.ssd file to: " + filePath);
			return false;
		}
	}

	public boolean readSettings() {
		String filePath = GameInfo.getAppdataFolderPath() + File.separator + "settings" + SSDatabase.FILE_EXTENTION;
		File file = new File(filePath);
		try {
			byte[] data = Files.readAllBytes(file.toPath());
			SSDatabase settings = SSDatabase.Deserialize(data);
			
			SSObject gameSettings = settings.getObject("game");
			this.mouseTurning = gameSettings.getField("mouseTurning").getBoolean();
			this.enableBobbing = gameSettings.getField("enableBobbing").getBoolean();
			this.debugMode = gameSettings.getField("debugMode").getBoolean();
			this.fullscreen = gameSettings.getField("fullscreen").getBoolean();
			this.useXInput = gameSettings.getField("useXInput").getBoolean();
			this.windowSizeIndex = gameSettings.getField("windowSizeIndex").getInteger();
			this.updateWindowSize();
			
			SSObject input = settings.getObject("input");
			int numAxes = input.getField("numAxes").getInteger();
			controls = new InputAxis[numAxes];
			for (int i = 0; i < numAxes; i++) {
				SSObject inputAxis = input.getObject("axis" + i);
				String name = inputAxis.getString("name").getString();
				int keyCode = inputAxis.getField("keyCode").getInteger();
				Identifier identifier = InputAxis.getIdentifierFromName(inputAxis.getString("identifier").getString());
				float actZone = inputAxis.getField("actZone").getFloat();
				int mouseCode = inputAxis.getField("keyCode").getInteger();
				controls[i] = new InputAxis(name, keyCode, identifier, actZone, mouseCode);
			}
			System.out.println("Loaded settings successfully from: " + filePath);
			return true;
		} catch (IOException e) {
			System.err.println("Can't read settings.ssd file from: " + filePath);
			return false;
		}
	}
	
	public GameSettings clone() {
		GameSettings copy = new GameSettings();
		copy.debugMode = debugMode;
		copy.enableBobbing = enableBobbing;
		copy.fullscreen = fullscreen;
		copy.controls = controls.clone();
		copy.mouseTurning = mouseTurning;
		copy.useXInput = useXInput;
		copy.windowSizeIndex = windowSizeIndex;
		copy.updateWindowSize();
		return copy;
	}
}

package net.keabotstudios.dr2.game.save;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import net.keabotstudios.dr2.game.GameDefaults;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.superin.InputAxis;
import net.keabotstudios.superserial.containers.SSDatabase;
import net.keabotstudios.superserial.containers.SSField;
import net.keabotstudios.superserial.containers.SSObject;

public class GameSettings extends Saveable {

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
		windowWidth = GameInfo.WINDOW_WIDTHS[windowSizeIndex];
		windowHeight = (int) (windowWidth * GameInfo.ASPECT_RATIO);
	}
	
	public boolean write() {
		SSDatabase settings = new SSDatabase(getFileName());

		SSObject root = new SSObject("root");
		root.addField(SSField.Boolean("mouseTurning", this.mouseTurning));
		root.addField(SSField.Boolean("enableBobbing", this.enableBobbing));
		root.addField(SSField.Boolean("fullscreen", this.fullscreen));
		root.addField(SSField.Boolean("useXInput", this.useXInput));
		root.addField(SSField.Integer("windowSizeIndex", this.windowSizeIndex));
		settings.addObject(root);

		getFile().getParentFile().mkdirs();
		try {
			byte[] data = new byte[settings.getSize()];
			settings.writeBytes(data, 0);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(getFilePath()));
			stream.write(data);
			stream.close();
			System.out.println("Wrote " + getFileName() + " successfully to: " + getFilePath());
			return true;
		} catch (Exception e) {
			System.err.println("Can't write " + getFileName() + ".ssd file to: " + getFilePath());
			return false;
		}
	}

	public boolean read() {
		File file = getFile();
		try {
			byte[] data = Files.readAllBytes(file.toPath());
			SSDatabase settings = SSDatabase.Deserialize(data);

			SSObject root = settings.getObject("root");
			this.mouseTurning = root.getField("mouseTurning").getBoolean();
			this.enableBobbing = root.getField("enableBobbing").getBoolean();
			this.fullscreen = root.getField("fullscreen").getBoolean();
			this.useXInput = root.getField("useXInput").getBoolean();
			this.windowSizeIndex = root.getField("windowSizeIndex").getInteger();
			this.updateWindowSize();

			System.out.println("Loaded " + getFileName() + " successfully from: " + getFilePath());
			return true;
		} catch (Exception e) {
			System.err.println("Can't read " + getFileName() +".ssd file from: " + getFilePath() + ", rewriting file.");
			write();
			read();
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

	public String getFileName() {
		return "settings";
	}
}

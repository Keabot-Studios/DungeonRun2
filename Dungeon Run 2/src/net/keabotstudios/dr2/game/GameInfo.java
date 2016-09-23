package net.keabotstudios.dr2.game;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import net.keabotstudios.dr2.Util;
import net.keabotstudios.superlog.Logger;

public class GameInfo {

	public static long TIME;
	public static final ArrayList<Image> WINDOW_ICONS = new ArrayList<Image>();
	public static final String APPDATA_FOLDER_NAME = "dungeonrun2";
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = GAME_WIDTH * 3 / 4;

	public static void update() {
		TIME++;
	}

	public static void init(Logger l) {
		WINDOW_ICONS.add(Util.loadImage("/icon/dr2x64.png", l));
		WINDOW_ICONS.add(Util.loadImage("/icon/dr2x32.png", l));
		WINDOW_ICONS.add(Util.loadImage("/icon/dr2x16.png", l));
		TIME = 0;
	}

	public static String getAppdataFolderPath() {
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN")) {
			return System.getenv("AppData") + File.separator + APPDATA_FOLDER_NAME;
		} else {
			return System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + APPDATA_FOLDER_NAME;
		}
	}

}

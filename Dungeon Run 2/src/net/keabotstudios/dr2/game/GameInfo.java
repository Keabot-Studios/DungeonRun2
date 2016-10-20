package net.keabotstudios.dr2.game;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import net.keabotstudios.dr2.Util;
import net.keabotstudios.superlog.Logger;

public class GameInfo {

	public static final ArrayList<Image> WINDOW_ICONS = new ArrayList<Image>();

	public static final String APPDATA_FOLDER_NAME = "dungeonrun2";

	public static final float ASPECT_RATIO = 3.0f / 4.0f;
	public static final int GAME_WIDTH = 600;
	public static final int GAME_HEIGHT = (int) (GAME_WIDTH * ASPECT_RATIO);
	public static final int[] WINDOW_WIDTHS = {
			GAME_WIDTH,
			800,
			1024,
			1152,
			1280,
			1400,
			1600,
			2048
	};

	public static final int MAX_UPS = 60;

	public static long TIME;
	public static int FPS;

	public static void update(int fps) {
		TIME++;
		FPS = fps;
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

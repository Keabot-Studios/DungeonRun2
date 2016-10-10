package net.keabotstudios.dr2.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import net.keabotstudios.superserial.containers.SSDatabase;

public class PlayerInfo extends Saveable {
	
	// TODO STATS! (Kills, Total damage taken, points won, games won, KDR, etc.)
	
	private String username;
	private long playerID;
	
	public PlayerInfo() {
		Random random = new Random();
		playerID = getRandomPlayerID();
		username = "Player" + playerID;
	}

	public boolean write() {
		return false;
	}

	public boolean read() {
		String filePath = GameInfo.getAppdataFolderPath() + File.separator + "settings" + SSDatabase.FILE_EXTENTION;
		File file = new File(filePath);
		try {
			byte[] data = Files.readAllBytes(file.toPath());
			SSDatabase playerInfo = SSDatabase.Deserialize(data);

			System.out.println("Loaded " + getFileName() + " successfully from: " + filePath);
			return true;
		} catch (IOException e) {
			System.err.println("Can't read " + getFileName() +".ssd file from: " + filePath);
			return false;
		}
	}

	public String getFileName() {
		return "playerInfo";
	}

	public long getPlayerID() {
		return playerID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public static long getRandomPlayerID() {
		return new Random().nextInt(10000);
	}

}

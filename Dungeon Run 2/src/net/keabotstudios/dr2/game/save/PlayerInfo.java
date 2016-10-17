package net.keabotstudios.dr2.game.save;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import net.keabotstudios.superserial.containers.SSDatabase;
import net.keabotstudios.superserial.containers.SSField;
import net.keabotstudios.superserial.containers.SSObject;
import net.keabotstudios.superserial.containers.SSString;

public class PlayerInfo extends Saveable {
	
	// TODO STATS! (Kills, Total damage taken, points won, games won, KDR, etc.)
	
	private static final int MAX_PLAYER_IDS = Integer.MAX_VALUE;
	private int playerID;
	private String playerName;
	
	public PlayerInfo() {
		playerID = getRandomPlayerID();
		playerName = "Player" + playerID;
	}

	public boolean write() {
		SSDatabase playerInfo = new SSDatabase(getFileName());

		SSObject root = new SSObject("root");
		
		root.addField(SSField.Integer("playerID", playerID));
		root.addString(new SSString("playerName", playerName));

		playerInfo.addObject(root);
		
		getFile().getParentFile().mkdirs();
		try {
			byte[] data = new byte[playerInfo.getSize()];
			playerInfo.writeBytes(data, 0);
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
			SSDatabase playerInfo = SSDatabase.Deserialize(data);
			SSObject root = playerInfo.getObject("root");
			
			playerID = root.getField("playerID").getInteger();
			playerName = root.getString("playerName").getString();
			
			System.out.println("Loaded " + getFileName() + " successfully from: " + getFilePath());
			return true;
		} catch (IOException e) {
			System.err.println("Can't read " + getFileName() +".ssd file from: " + getFilePath());
			return false;
		}
	}

	public String getFileName() {
		return "playerInfo";
	}

	public long getPlayerID() {
		return playerID;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public static int getRandomPlayerID() {
		return new Random().nextInt(MAX_PLAYER_IDS);
	}

}

package net.keabotstudios.dr2.game.level.object.tile;

import net.keabotstudios.dr2.Util.ColorUtil;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public class Tile {

	protected final int id, minimapColor;
	private final Bitmap floorTex;
	private final Bitmap ceilTex;
	private final boolean skybox;
	
	public static Tile[] tiles = new Tile[4];

	public static Tile inside1;
	public static Tile outside1;
	
	public static void load() {
		inside1 = new Tile(0, Texture.brickFloor, Texture.brick, false, ColorUtil.toARGBColor(0));
		outside1 = new Tile(1, Texture.brickFloor, Texture.night.scale(2), true, ColorUtil.toARGBColor(0));
	}

	protected Tile(int id, Bitmap floorTex, Bitmap ceilTex, boolean skybox, int minimapColor) {
		this.id = id;
		this.floorTex = floorTex;
		if(skybox) {
			this.ceilTex = ceilTex.stich(ceilTex, ceilTex.getWidth() - 1, 0);
		} else {
			this.ceilTex = ceilTex;
		}
		this.skybox = skybox;
		this.minimapColor = minimapColor;
		if (id < tiles.length || id >= 0) {
			if (tiles[id] == null) {
				tiles[id] = this;
			} else {
				System.err.println("Tile already exists: " + id);
				System.exit(-1);
			}
		} else {
			System.err.println("Tile id is not in range: " + id);
			System.exit(-1);
		}
	}

	public int getId() {
		return id;
	}

	public int getMinimapColor() {
		return minimapColor;
	}

	public Bitmap getFloorTexture() {
		return floorTex;
	}

	public Bitmap getCeilingTexture() {
		return ceilTex;
	}

	public boolean hasSkybox() {
		return skybox;
	}
	
	

}

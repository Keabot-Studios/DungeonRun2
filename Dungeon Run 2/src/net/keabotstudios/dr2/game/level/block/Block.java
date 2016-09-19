package net.keabotstudios.dr2.game.level.block;

import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public class Block {
	
	public final int id;
	public final boolean solid, opaque;
	public final Render texture;
	
	public static Block empty;
	public static Block brickWall;
	
	public Block(int id, boolean solid, boolean opaque, Render texture) {
		this.id = id;
		this.solid = solid;
		this.opaque = opaque;
		this.texture = texture;
	}
	
	public static void init() {
		empty = new Block(0, false, false, null);
		brickWall = new SolidBlock(1, Texture.brick1);
	}

}

package net.keabotstudios.dr2.game.level.object.block;

import net.keabotstudios.dr2.gfx.Render;

public class SolidBlock extends Block {

	private final Render texture;
	
	public SolidBlock(int id, Render texture) {
		super(id, true, true, texture.getAverageColor());
		this.texture = texture;
	}

	public Render getTexture(int side, int y) {
		return texture;
	}

}

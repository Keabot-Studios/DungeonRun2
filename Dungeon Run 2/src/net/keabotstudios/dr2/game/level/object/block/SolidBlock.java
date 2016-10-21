package net.keabotstudios.dr2.game.level.object.block;

import net.keabotstudios.dr2.gfx.Bitmap;

public class SolidBlock extends Block {

	private final Bitmap texture;

	public SolidBlock(int id, Bitmap texture) {
		super(id, true, true, texture.getAverageColor());
		this.texture = texture;
	}

	public Bitmap getTexture(int side, int y) {
		return texture;
	}

}

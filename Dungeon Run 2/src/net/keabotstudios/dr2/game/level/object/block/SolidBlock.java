package net.keabotstudios.dr2.game.level.object.block;

import net.keabotstudios.dr2.gfx.Bitmap;

public class SolidBlock extends Block {

	private final Bitmap texture;

	public SolidBlock(Bitmap texture) {
		super(1, true, true, texture.getAverageColor());
		this.texture = texture;
	}

	public Bitmap getTexture(int side, int y) {
		return texture;
	}

}

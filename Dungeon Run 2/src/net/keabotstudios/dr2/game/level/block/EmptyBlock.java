package net.keabotstudios.dr2.game.level.block;

import net.keabotstudios.dr2.gfx.Render;

public class EmptyBlock extends Block {

	public EmptyBlock(int id) {
		super(id, false, false);
	}

	public Render getTexture(int side, boolean top) {
		return null;
	}

}

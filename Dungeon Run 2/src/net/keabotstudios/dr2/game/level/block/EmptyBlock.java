package net.keabotstudios.dr2.game.level.block;

import java.awt.Color;

import net.keabotstudios.dr2.gfx.Render;

public class EmptyBlock extends Block {

	public EmptyBlock(int id) {
		super(id, false, false, new Color(0, 0, 0, 0).getRGB());
	}

	public Render getTexture(int side, int y) {
		return null;
	}

}

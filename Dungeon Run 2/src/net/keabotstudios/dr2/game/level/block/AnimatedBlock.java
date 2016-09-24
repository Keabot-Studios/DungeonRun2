package net.keabotstudios.dr2.game.level.block;

import java.awt.Color;

import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public class AnimatedBlock extends Block {

	public AnimatedBlock(int id) {
		super(id, true, true, Color.WHITE.getRGB());
	}

	public Render getTexture(int side, int y) {
		return Texture.animTest;
	}

}

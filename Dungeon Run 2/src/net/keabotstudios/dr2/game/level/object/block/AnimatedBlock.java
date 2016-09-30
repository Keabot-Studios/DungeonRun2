package net.keabotstudios.dr2.game.level.object.block;

import java.awt.Color;

import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public class AnimatedBlock extends Block {

	public AnimatedBlock() {
		super(2, true, true, Color.WHITE.getRGB());
	}

	public Render getTexture(int side, int y) {
		return Texture.animTest;
	}

}

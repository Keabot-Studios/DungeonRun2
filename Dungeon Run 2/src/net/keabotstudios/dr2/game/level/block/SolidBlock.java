package net.keabotstudios.dr2.game.level.block;

import net.keabotstudios.dr2.gfx.Render;

public class SolidBlock extends Block {

	public SolidBlock(int id, Render texture) {
		super(id, true, true, texture);
	}

}

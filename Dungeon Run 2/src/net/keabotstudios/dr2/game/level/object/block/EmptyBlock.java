package net.keabotstudios.dr2.game.level.object.block;

import java.awt.Color;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.gfx.Bitmap;

public class EmptyBlock extends Block {

	public EmptyBlock() {
		super(0, false, false, new Color(0, 0, 0, 0).getRGB(), new CollisionBox(0, 0, 0), 0, 0);
	}

	public Bitmap getTexture(int side, int y) {
		return null;
	}

}

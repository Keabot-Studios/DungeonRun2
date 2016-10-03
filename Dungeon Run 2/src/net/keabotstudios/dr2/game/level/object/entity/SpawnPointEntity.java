package net.keabotstudios.dr2.game.level.object.entity;

import java.awt.Color;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.game.level.object.Vector3;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;

public class SpawnPointEntity extends Entity {

	public SpawnPointEntity(Vector3 pos, String name) {
		super(pos, new CollisionBox(0.5, 0.5, 0.5), 0, name, Color.GRAY.getRGB());
	}

	public Bitmap getTexture() {
		return Texture.animTest;
	}

}

package net.keabotstudios.dr2.game.level.object.entity;

import java.awt.Color;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.dr2.math.Vector3;

public class SpawnPointEntity extends Entity {

	public SpawnPointEntity(Vector3 pos) {
		super(pos, new CollisionBox(0.5, 0.5, 0.5), 0, Color.BLUE.getRGB());
	}

	public Bitmap getTexture() {
		return Texture.spawnPoint;
	}

}

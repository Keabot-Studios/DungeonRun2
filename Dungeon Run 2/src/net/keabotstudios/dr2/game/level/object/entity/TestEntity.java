package net.keabotstudios.dr2.game.level.object.entity;

import java.awt.Color;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.dr2.math.Vector3;

public class TestEntity extends Entity {

	public TestEntity(double x, double y, double z) {
		super(new Vector3(x, y, z), new CollisionBox(0.5, 0.5, 0.5), 0, Color.GRAY.getRGB());
	}

	public Bitmap getTexture() {
		return Texture.animTest;
	}

}

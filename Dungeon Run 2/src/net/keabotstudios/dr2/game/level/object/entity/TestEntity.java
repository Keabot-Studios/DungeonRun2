package net.keabotstudios.dr2.game.level.object.entity;

import java.awt.Color;

import net.keabotstudios.dr2.game.level.object.CollisionBox;
import net.keabotstudios.dr2.game.level.object.Vector3;
import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public class TestEntity extends Entity {

	public TestEntity(double x, double y, double z, String name) {
		super(new Vector3(x, y, z), new CollisionBox(0.5, 0.5, 0.5), 0, name, Color.GRAY.getRGB());
	}

	public Render getTexture() {
		return Texture.animTest;
	}

}

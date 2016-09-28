package net.keabotstudios.dr2.game.level.entity;

import java.awt.Color;

import net.keabotstudios.dr2.game.object.CollisionBox;
import net.keabotstudios.dr2.game.object.Position3D;
import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public class TestEntity extends Entity {

	public TestEntity(double x, double y, double z, String name) {
		super(new Position3D(x, y, z), new CollisionBox(1, 1, 1), 0, name, Color.WHITE.getRGB());
	}

	public Render getTexture() {
		return Texture.animTest;
	}

}

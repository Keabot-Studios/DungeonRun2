package net.keabotstudios.dr2.game.level.entity;

import java.awt.Color;

import net.keabotstudios.dr2.gfx.Render;
import net.keabotstudios.dr2.gfx.Texture;

public class TestEntity extends Entity {

	public TestEntity(double x, double y, double z, String name) {
		super(x, y, z, 0, name, Color.WHITE.getRGB());
	}

	public Render getTexture() {
		return Texture.animTest;
	}

}

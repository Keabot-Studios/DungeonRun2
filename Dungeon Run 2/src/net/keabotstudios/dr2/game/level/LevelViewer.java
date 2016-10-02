package net.keabotstudios.dr2.game.level;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import net.keabotstudios.dr2.Util;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.level.object.entity.Entity;
import net.keabotstudios.dr2.game.level.object.entity.Player;

public class LevelViewer extends Canvas {
	private static final long serialVersionUID = 1L;

	private final int scale;

	private JFrame frame;
	private Level level;
	private BufferedImage image, playerArrow;

	// ---------------------------------------------------
	// TODO TESTING PURPOSES - REMOVE LATER
	// ---------------------------------------------------

	public LevelViewer(Level l, int scale) {
		level = l;
		this.scale = scale;

		Dimension size = new Dimension((level.getWidth() + 1) * scale, (level.getHeight() + 1) * scale);
		setMinimumSize(size);
		setSize(size);
		setMaximumSize(size);

		image = new BufferedImage((int) size.getWidth() + scale, (int) size.getHeight() + scale, BufferedImage.TYPE_INT_RGB);
		playerArrow = Util.loadImage("/texture/playerArrow.png", null);
		frame = new JFrame("Level Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImages(GameInfo.WINDOW_ICONS);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void update() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int x = -1; x < level.getWidth() + 2; x++) {
			for (int y = -1; y < level.getHeight() + 2; y++) {
				g.setColor(new Color(level.getBlock(x, y).getMinimapColor()));
				g.fillRect(x * scale + scale, y * scale + scale, scale, scale);
			}
		}

		AffineTransform oldtrans = new AffineTransform();
		AffineTransform trans = new AffineTransform();

		Player p = level.getPlayer();
		double pWidth = p.getCollisionBox().getX() * scale;
		double pHeight = p.getCollisionBox().getY() * scale;
		double pX = p.getPos().getX() * scale + scale;
		double pY = p.getPos().getZ() * scale + scale;

		trans.setToIdentity();
		trans.rotate(-p.getRotation() + Math.PI, pX, pY);
		g.setTransform(trans);
		g.drawImage(playerArrow, (int) (pX - pWidth / 2.0), (int) (pY - pHeight / 2.0), (int) pWidth, (int) pHeight, null);
		g.setTransform(oldtrans);

		for (Entity e : level.getEntites()) {
			g.setColor(new Color(e.getMinimapColor()));
			double eWidth = e.getCollisionBox().getX() * scale;
			double eHeight = e.getCollisionBox().getY() * scale;
			double eX = e.getPos().getX() * scale + scale;
			double eY = e.getPos().getZ() * scale + scale;
			g.fillRect((int) (eX - eWidth / 2.0), (int) (eY - eHeight / 2.0), (int) eWidth, (int) eHeight);
		}
		g.dispose();

		repaint();
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(image, 0, getHeight(), getWidth(), -getHeight(), null);
	}

}

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
	
	private static final int SCALE = 20;
	
	private JFrame frame;
	private Level level;
	private BufferedImage image, playerArrow;
	
	//---------------------------------------------------
	// TODO    TESTING PURPOSES - REMOVE LATER
	//---------------------------------------------------

	public LevelViewer(Level l) {
		level = l;
		
		Dimension size = new Dimension((level.getWidth() + 1) * SCALE, (level.getHeight() + 1) * SCALE);
		setMinimumSize(size);
		setSize(size);
		setMaximumSize(size);
		
		image = new BufferedImage((int)size.getWidth() + SCALE, (int)size.getHeight() + SCALE, BufferedImage.TYPE_INT_RGB);
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
		
		for(int x = -1; x < level.getWidth() + 2; x++) {
			for(int y = -1; y < level.getHeight() + 2; y++) {
				g.setColor(new Color(level.getBlock(x, y).getMinimapColor()));
				g.fillRect(x * SCALE + SCALE, y * SCALE + SCALE, SCALE, SCALE);
			}
		}
		
		 AffineTransform oldtrans = new AffineTransform();
		 AffineTransform trans = new AffineTransform();
		
		Player p = level.getPlayer();
		double pWidth = p.getCollisionBox().getX() * SCALE;
		double pHeight = p.getCollisionBox().getY() * SCALE;
		double pX = p.getPos().getX() * SCALE + SCALE;
		double pY = p.getPos().getZ() * SCALE + SCALE;
		
		trans.setToIdentity();
	    trans.rotate(-p.getRotation() + Math.PI, pX, pY);
	    g.setTransform(trans);
		g.drawImage(playerArrow, (int) (pX - pWidth / 2.0), (int) (pY - pHeight / 2.0), (int) pWidth, (int) pHeight, null);
		g.setTransform(oldtrans);
		
		for(Entity e : level.getEntites()) {
			g.setColor(new Color(e.getMinimapColor()));
			double eWidth = e.getCollisionBox().getX() * SCALE;
			double eHeight = e.getCollisionBox().getY() * SCALE;
			double eX = e.getPos().getX() * SCALE + SCALE;
			double eY = e.getPos().getZ() * SCALE + SCALE;
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

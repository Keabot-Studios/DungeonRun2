package net.keabotstudios.dr2;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;

import net.keabotstudios.dr2.game.GameSettings;
import net.keabotstudios.superlog.Logger;

public class DR2Applet extends Applet {
	private static final long serialVersionUID = 1L;

	private Game game;

	public void init() {
		game = new Game(new Logger(), new GameSettings(), GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
		setLayout(new BorderLayout());
		add(game);
	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}

}

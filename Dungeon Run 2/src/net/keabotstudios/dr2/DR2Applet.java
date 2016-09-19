package net.keabotstudios.dr2;

import java.applet.Applet;
import java.awt.BorderLayout;

import net.keabotstudios.superlog.Logger;

public class DR2Applet extends Applet {
	private static final long serialVersionUID = 1L;
	
	private Display display;
	
	public void init() {
		display = new Display(new Logger());
		setLayout(new BorderLayout());
		add(display);
	}
	
	public void start() {
		display.start();
	}
	
	public void stop() {
		display.stop();
	}

}

package net.keabotstudios.dr2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.entity.Player;
import net.keabotstudios.dr2.gfx.Screen;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.superin.Controllable;
import net.keabotstudios.superin.Input;
import net.keabotstudios.superlog.Logger;
import net.keabotstudios.superlog.Logger.LogLevel;

public class Display extends Canvas implements Runnable, Controllable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 640, HEIGHT = WIDTH * 3 / 4, SCALE = 2;
	public static final String TITLE = "Dungeon Run 2";
	public static final String VERSION = "v0.00a";

	private Thread thread;
	private boolean running = false;
	private int fps;

	private Input input;
	private Player player;
	private Logger logger;
	private JFrame frame;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;

	public Display(Logger logger) {
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		this.logger = logger;
		GameInfo.init(this);
		Texture.load(this);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = Util.convertToPixels(img);
		input = new Input(this, true);
		input.setInputs(GameInfo.CONTROLS);
		player = new Player(0, 0, 0, "Player");
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);

		createJFrame();
		frame.getContentPane().setCursor(blank);

		frame.requestFocus();
		requestFocusInWindow();
	}

	private synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, TITLE + " Game Thread");
		thread.start();
	}

	@SuppressWarnings("unused")
	private synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void run() {
		int frames = 0;
		double skippedSecs = 0;
		long prevTime = System.nanoTime();
		double secsPerTick = 1 / 60.0;
		int tickCount = 0;

		while (running) {
			long currTime = System.nanoTime();
			long elapsedTime = currTime - prevTime;
			prevTime = currTime;
			skippedSecs += elapsedTime / 1000000000.0;
			while (skippedSecs > secsPerTick) {
				update();
				skippedSecs -= secsPerTick;
				tickCount++;
				if (tickCount % 60 == 0 && GameInfo.DEBUG_MODE) {
					fps = frames;
					tickCount = 0;
					frames = 0;
				}
			}
			render();
			frames++;
		}
	}

	private void update() {
		GameInfo.update();
		player.update(input);
		if (input.getInput("ESCAPE")) {
			System.exit(0);
		}
		input.update();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render(player);

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		if (GameInfo.DEBUG_MODE) {
			int fpsX = 5;
			int fpsY = 29;
			g.setFont(new Font("Crypt Of Tomorrow", Font.PLAIN, 36));
			g.setColor(Color.BLACK);
			g.drawString(fps + " FPS", fpsX, fpsY + 5);
			g.setColor(Color.WHITE);
			g.drawString(fps + " FPS", fpsX, fpsY);
		}
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger gameLogger = new Logger();
		if (args.length > 0) {
			List<String> arguments = Arrays.asList(args);
			if (arguments.contains("debug")) {
				GameInfo.DEBUG_MODE = true;
				gameLogger.setLogLevel(LogLevel.INFO);
				gameLogger.debug("DEBUG MODE ENABLED");
			}
		}
		new Display(gameLogger).start();
	}

	public void createJFrame() {
		frame = new JFrame();

		frame.add(this);
		frame.setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		;
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE + (GameInfo.DEBUG_MODE ? " - Debug Mode" : ""));
		frame.setIconImages(GameInfo.WINDOW_ICONS);
		frame.setVisible(true);
	}

	public Logger getLogger() {
		return logger;
	}

	public Component getComponent() {
		return this;
	}

}

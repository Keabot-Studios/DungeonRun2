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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import net.keabotstudios.dr2.game.Direction;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.game.level.block.Block;
import net.keabotstudios.dr2.gfx.Screen;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.superin.Controllable;
import net.keabotstudios.superin.Input;
import net.keabotstudios.superlog.Logger;
import net.keabotstudios.superlog.Logger.LogLevel;

public class Display extends Canvas implements Runnable, Controllable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800, HEIGHT = WIDTH * 3 / 4, SCALE = 1;
	public static final String TITLE = "Dungeon Run 2";
	public static final String VERSION = "v0.00a";

	private Thread thread;
	private boolean running = false;
	private int fps;

	private Input input;
	private Level level;
	private Logger logger;
	private JFrame frame;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;

	public Display(Logger logger) {
		this.logger = logger;
		GameInfo.init(this);
		Texture.load(this);
		Block.init();
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = Util.convertToPixels(img);
		input = new Input(this, true);
		input.setInputs(GameInfo.CONTROLS);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	public void requestFocus() {
		frame.requestFocus();
		requestFocusInWindow();
	}

	public void init() {
		level = new Level(20, 20);
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, TITLE + " Game Thread");
		thread.start();
	}

	public synchronized void stop() {
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

		init();

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
		input.updateControllerInput();
		GameInfo.update();
		level.update(input);
		if (input.getInputTapped("ESCAPE")) {
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

		screen.render(level);

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		if (GameInfo.DEBUG_MODE) {
			int debugX = 2;
			int debugY = 12;
			g.setFont(new Font("Verdana", Font.PLAIN, 12));
			g.setColor(Color.YELLOW);
			g.drawString(fps + " FPS", debugX, debugY);
			double playerX = level.getPlayer().getX() / 8.0;
			BigDecimal px = new BigDecimal(playerX).setScale(1, RoundingMode.HALF_EVEN);
			double playerY = level.getPlayer().getY() / 8.0;
			BigDecimal py = new BigDecimal(playerY).setScale(1, RoundingMode.HALF_EVEN);
			double playerZ = level.getPlayer().getZ() / 8.0;
			BigDecimal pz = new BigDecimal(playerZ).setScale(1, RoundingMode.HALF_EVEN);
			double playerRot = level.getPlayer().getRotation();
			BigDecimal pr = new BigDecimal(playerRot).setScale(3, RoundingMode.HALF_EVEN);
			g.drawString("XYZ: " + px.doubleValue() + ", " + py.doubleValue() + ", " + pz.doubleValue(), debugX, debugY + 12);
			g.drawString("Rotation: " + pr.doubleValue(), debugX, debugY + 12 * 2);
			Direction pdir = Direction.getFromRad(level.getPlayer().getRotation());
			g.drawString("Direction: " + pdir.getId() + " (" + pdir.name() + ")", debugX, debugY + 12 * 3);
			g.drawString("Move Speed: " + level.getPlayer().getMoveSpeed(), debugX, debugY + 12 * 4);
			g.drawString("Crouching: " + level.getPlayer().isCrouching(), debugX, debugY + 12 * 5);
			g.drawString("Running: " + level.getPlayer().isRunning(), debugX, debugY + 12 * 6);
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
				gameLogger.debugLn("DEBUG MODE ENABLED");
			}
		}
		Display display = new Display(gameLogger);
		createJFrame(display);
		display.requestFocus();
		display.start();
	}

	public static void createJFrame(Display display) {
		display.frame = new JFrame();

		display.frame.add(display);
		display.frame.setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		display.frame.setResizable(false);
		display.frame.setLocationRelativeTo(null);
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setTitle(TITLE + (GameInfo.DEBUG_MODE ? " - Debug Mode" : ""));
		display.frame.setIconImages(GameInfo.WINDOW_ICONS);
		display.frame.setVisible(true);

		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		display.frame.getContentPane().setCursor(blank);
	}

	public Logger getLogger() {
		return logger;
	}

	public Component getComponent() {
		return this;
	}

}

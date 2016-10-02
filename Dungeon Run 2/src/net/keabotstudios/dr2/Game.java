package net.keabotstudios.dr2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JFrame;

import net.keabotstudios.dr2.game.Direction;
import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.GameSettings;
import net.keabotstudios.dr2.game.gamestate.GameStateManager;
import net.keabotstudios.dr2.game.gamestate.LevelState;
import net.keabotstudios.dr2.game.level.Level;
import net.keabotstudios.dr2.gfx.Bitmap;
import net.keabotstudios.dr2.gfx.Texture;
import net.keabotstudios.superin.Controllable;
import net.keabotstudios.superin.Input;
import net.keabotstudios.superlog.Logger;

public class Game extends Canvas implements Runnable, Controllable {
	private static final long serialVersionUID = 1L;

	public static final String TITLE = "Dungeon Run 2";
	public static final String VERSION = "v0.00a";

	private Thread thread;
	private boolean running = false;
	private int fps;

	private GameSettings settings;
	private Input input;
	private Level level;
	private Logger logger;
	private JFrame frame;
	private Bitmap screen;
	private BufferedImage img;
	private int[] pixels;
	private int fullScreenImageWidth = 0;
	private int fullScreenImageHeight = 0;
	private int fullScreenXOff = 0;
	private int fullScreenYOff = 0;
	
	private GameStateManager gsm;

	public Game(Logger logger, GameSettings settings) {
		GameInfo.init(logger);
		this.logger = logger;
		this.settings = settings;
		Texture.load(this);
		
		screen = new Bitmap(GameInfo.GAME_WIDTH, GameInfo.GAME_HEIGHT);
		img = new BufferedImage(GameInfo.GAME_WIDTH, GameInfo.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = Util.convertToPixels(img);
		
		input = new Input(this, settings.useXInput);
		input.setInputs(settings.controls);
		
		gsm = new GameStateManager(this);
		level = new Level(50, 50, settings);
		gsm.setState(new LevelState(gsm, level));
		
		Dimension size = new Dimension(settings.windowWidth, settings.windowHeight);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		createJFrame();
		
		if(settings.fullscreen) {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			int screenWidth = gd.getDisplayMode().getWidth();
			int screenHeight = gd.getDisplayMode().getHeight();
			size = new Dimension(screenWidth, screenHeight);
			setMinimumSize(size);
			setPreferredSize(size);
			setMaximumSize(size);
			float fullScreenImageScale = Util.getScaleOfRectangeInArea(screenWidth, screenHeight, GameInfo.GAME_WIDTH, GameInfo.GAME_HEIGHT);
			fullScreenImageWidth = (int) (GameInfo.GAME_WIDTH * fullScreenImageScale);
			fullScreenImageHeight = (int) (GameInfo.GAME_HEIGHT * fullScreenImageScale);
			fullScreenXOff = (int) ((screenWidth - fullScreenImageWidth) / 2.0f);
			fullScreenYOff = (int) ((screenHeight - fullScreenImageHeight) / 2.0f);
		}
		
		frame.setVisible(true);
		requestFocus();
		start();
	}

	public void requestFocus() {
		frame.requestFocus();
		requestFocusInWindow();
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
		double secsPerTick = 1.0 / (double) GameInfo.MAX_UPS;
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
				if (tickCount % 60 == 0 && settings.debugMode) {
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
		Texture.update();
		gsm.update(input);
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
		
		gsm.render(screen);

		for (int i = 0; i < GameInfo.GAME_WIDTH * GameInfo.GAME_HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(settings.fullscreen) {
			g.drawImage(img, fullScreenXOff, fullScreenYOff, fullScreenImageWidth, fullScreenImageHeight, null);
		} else {
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		
		if (settings.debugMode) {
			int debugX = fullScreenXOff + 2;
			int debugY = 12;
			g.setFont(new Font("Verdana", Font.PLAIN, 12));
			g.setColor(Color.YELLOW);
			g.drawString(fps + " FPS", debugX, debugY);
			double playerX = level.getPlayer().getPos().getX();
			BigDecimal px = new BigDecimal(playerX).setScale(1, RoundingMode.HALF_EVEN);
			double playerY = level.getPlayer().getPos().getY();
			BigDecimal py = new BigDecimal(playerY).setScale(1, RoundingMode.HALF_EVEN);
			double playerZ = level.getPlayer().getPos().getZ();
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

	public static void runGame(Logger l, GameSettings settings) {
		new Game(l, settings);
	}

	public void createJFrame() {
		frame = new JFrame();
		frame.add(this);
		if(settings.fullscreen) {
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			frame.setSize(new Dimension(settings.windowWidth, settings.windowHeight));
			frame.setLocationRelativeTo(null);
		}
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE + (settings.debugMode ? " - Debug Mode" : ""));
		frame.setIconImages(GameInfo.WINDOW_ICONS);

		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		frame.getContentPane().setCursor(blank);
	}

	public Logger getLogger() {
		return logger;
	}

	public Component getComponent() {
		return this;
	}

}

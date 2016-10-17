package net.keabotstudios.dr2.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import net.keabotstudios.dr2.Game;
import net.keabotstudios.dr2.game.save.GameSettings;
import net.keabotstudios.superlog.Logger;
import net.keabotstudios.superlog.Logger.LogLevel;

public class Launcher {

	private JFrame frame;
	private JPanel window = new JPanel();
	private JButton play, options, help, quit;
	private GameSettings settings;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger l = new Logger();
		GameSettings settings = new GameSettings();
		settings.updateFromFile();
		Launcher launcher = new Launcher(l, settings);
		if (args.length > 0) {
			List<String> argsList = Arrays.asList(args);
			if (argsList.contains("debug")) {
				settings.debugMode = true;
				l.setLogLevel(LogLevel.INFO);
				l.debugLn("DEBUG MODE ENABLED");
			}
		}
		launcher.frame.setVisible(true);
	}

	public Launcher(Logger l, GameSettings settings) {
		this.settings = settings;
		frame = new JFrame();
		frame.setBackground(Color.BLACK);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Launcher.class.getResource("/icon/dr2x64.png")));
		frame.setTitle(Game.TITLE + " Launcher");
		window.setLayout(new BorderLayout(0, 0));
		content(l);
		frame.getContentPane().add(window);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	private void content(Logger l) {
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.BLACK);
		window.add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] {
				5,
				100,
				5
		};
		gbl_btnPanel.rowHeights = new int[] {
				5,
				20,
				20,
				20,
				20,
				5
		};
		btnPanel.setLayout(gbl_btnPanel);

		play = new JButton("Play Game");
		play.setBackground(Color.WHITE);
		GridBagConstraints gbc_playSingle = new GridBagConstraints();
		gbc_playSingle.fill = GridBagConstraints.BOTH;
		gbc_playSingle.insets = new Insets(0, 0, 5, 0);
		gbc_playSingle.gridx = 1;
		gbc_playSingle.gridy = 1;
		btnPanel.add(play, gbc_playSingle);
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Game(l, settings, frame.getGraphicsConfiguration().getDevice());
				frame.dispose();
			}
		});

		options = new JButton("Options");
		options.setBackground(Color.WHITE);
		GridBagConstraints gbc_options = new GridBagConstraints();
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.insets = new Insets(0, 0, 5, 0);
		gbc_options.gridx = 1;
		gbc_options.gridy = 2;
		btnPanel.add(options, gbc_options);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings = OptionsMenu.showOptions(frame, settings);
			}
		});

		help = new JButton("Help");
		help.setBackground(Color.WHITE);
		GridBagConstraints gbc_help = new GridBagConstraints();
		gbc_help.fill = GridBagConstraints.BOTH;
		gbc_help.insets = new Insets(0, 0, 5, 0);
		gbc_help.gridx = 1;
		gbc_help.gridy = 3;
		btnPanel.add(help, gbc_help);
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		quit = new JButton("Quit");
		quit.setBackground(Color.WHITE);
		GridBagConstraints gbc_quit = new GridBagConstraints();
		gbc_quit.insets = new Insets(0, 0, 5, 0);
		gbc_quit.fill = GridBagConstraints.BOTH;
		gbc_quit.gridx = 1;
		gbc_quit.gridy = 4;
		btnPanel.add(quit, gbc_quit);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel picPanel = new JPanel();
		picPanel.setBackground(Color.BLACK);
		window.add(picPanel, BorderLayout.CENTER);
		picPanel.setLayout(new BorderLayout(0, 0));

		JLabel picture = new JLabel("");
		picture.setBackground(Color.BLACK);
		picPanel.add(picture);
		picture.setHorizontalAlignment(SwingConstants.CENTER);
		Image image = new ImageIcon(Launcher.class.getResource("/launcher/title.png")).getImage();
		picture.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth(null) * 3, image.getHeight(null) * 3, Image.SCALE_DEFAULT)));
	}
}

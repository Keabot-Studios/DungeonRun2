package net.keabotstudios.dr2.launcher;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import net.keabotstudios.dr2.game.GameInfo;
import net.keabotstudios.dr2.game.GameSettings;

public class OptionsMenu extends JDialog {
	private static final long serialVersionUID = 1L;
	private JButton btnCancel;
	private JButton btnDefaults;
	private JButton btnApply;
	private final GameSettings originalSettings;
	private GameSettings editedSettings;
	private JComboBox<String> cBoxResolution;
	private JCheckBox chckbxBobbing;
	private JCheckBox chckbxFullscreen;

	public OptionsMenu(JFrame parent, GameSettings settings) {
		super(parent);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Options");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptionsMenu.class.getResource("/icon/dr2x64.png")));
		this.originalSettings = settings;
		this.editedSettings = settings.clone();
		content();
		updateSettingValues();
		pack();
		setLocationRelativeTo(parent);
	}

	private void content() {
		JTabbedPane optionsPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(optionsPane, BorderLayout.CENTER);

		addGraphicsTab(optionsPane);
		addControlsTab(optionsPane);

		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[] {
				10,
				70,
				50,
				70,
				70,
				4
		};
		gbl_buttonPanel.rowHeights = new int[] {
				5,
				23,
				5
		};
		gbl_buttonPanel.columnWeights = new double[] {
				0.0,
				0.0,
				0.0,
				0.0,
				0.0,
				Double.MIN_VALUE
		};
		gbl_buttonPanel.rowWeights = new double[] {
				0.0,
				0.0,
				Double.MIN_VALUE
		};
		buttonPanel.setLayout(gbl_buttonPanel);

		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 1;
		buttonPanel.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editedSettings = originalSettings.clone();
				dispose();
			}
		});

		btnDefaults = new JButton("Defaults");
		GridBagConstraints gbc_btnDefaults = new GridBagConstraints();
		gbc_btnDefaults.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnDefaults.insets = new Insets(0, 0, 0, 5);
		gbc_btnDefaults.gridx = 3;
		gbc_btnDefaults.gridy = 1;
		buttonPanel.add(btnDefaults, gbc_btnDefaults);
		btnDefaults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editedSettings = new GameSettings();
				updateSettingValues();
			}
		});

		btnApply = new JButton("Apply");
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnApply.gridx = 4;
		gbc_btnApply.gridy = 1;
		buttonPanel.add(btnApply, gbc_btnApply);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editedSettings.writeSettings();
				dispose();
			}
		});
	}

	private void addGraphicsTab(JTabbedPane optionsPane) {
		JPanel graphicsPanel = new JPanel();
		optionsPane.addTab("Graphics", null, graphicsPanel, null);
		optionsPane.setEnabledAt(0, true);
		GridBagLayout gbl_graphicsPanel = new GridBagLayout();
		gbl_graphicsPanel.columnWidths = new int[] {
				10,
				50,
				20,
				50,
				10
		};
		gbl_graphicsPanel.rowHeights = new int[] {
				5,
				15,
				5,
				10,
				5
		};
		gbl_graphicsPanel.columnWeights = new double[] {
				0.0,
				0.0,
				0.0,
				1.0
		};
		gbl_graphicsPanel.rowWeights = new double[] {
				0.0,
				0.0,
				Double.MIN_VALUE
		};
		graphicsPanel.setLayout(gbl_graphicsPanel);

		JLabel lblResolution = new JLabel("Resolution:");
		lblResolution.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblResolution = new GridBagConstraints();
		gbc_lblResolution.insets = new Insets(0, 0, 5, 5);
		gbc_lblResolution.gridx = 1;
		gbc_lblResolution.gridy = 1;
		graphicsPanel.add(lblResolution, gbc_lblResolution);

		cBoxResolution = new JComboBox<String>();
		cBoxResolution.setToolTipText("Has no effect in fullscreen mode.");
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screenWidth = gd.getDisplayMode().getWidth();
		int screenHeight = gd.getDisplayMode().getHeight();
		for (int i = 0; i < GameInfo.WINDOW_WIDTHS.length; i++) {
			int w = GameInfo.WINDOW_WIDTHS[i];
			int h = (int) (GameInfo.WINDOW_WIDTHS[i] * GameInfo.ASPECT_RATIO);
			if (w > screenWidth || h > screenHeight)
				continue;
			String s = w + "x" + h;
			cBoxResolution.addItem(s);
		}
		cBoxResolution.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				editedSettings.windowSizeIndex = cBoxResolution.getSelectedIndex();
				editedSettings.updateWindowSize();
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
		graphicsPanel.add(cBoxResolution, gbc_comboBox);

		chckbxBobbing = new JCheckBox("Enable Bobbing");
		GridBagConstraints gbc_chckbxBobbing = new GridBagConstraints();
		gbc_chckbxBobbing.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxBobbing.gridx = 1;
		gbc_chckbxBobbing.gridy = 3;
		graphicsPanel.add(chckbxBobbing, gbc_chckbxBobbing);
		chckbxBobbing.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				editedSettings.enableBobbing = chckbxBobbing.isSelected();
			}
		});

		chckbxFullscreen = new JCheckBox("Fullscreen");
		GridBagConstraints gbc_chckbxFullscreen = new GridBagConstraints();
		gbc_chckbxFullscreen.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxFullscreen.gridx = 3;
		gbc_chckbxFullscreen.gridy = 3;
		graphicsPanel.add(chckbxFullscreen, gbc_chckbxFullscreen);
		chckbxFullscreen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				editedSettings.fullscreen = chckbxFullscreen.isSelected();
			}
		});
	}

	private void addControlsTab(JTabbedPane optionsPane) {
		JPanel controlsPanel = new JPanel();
		optionsPane.addTab("Controls", null, controlsPanel, null);
		controlsPanel.setLayout(new BorderLayout(0, 0));

		JPanel inputAxisPane = new JPanel();
		controlsPanel.add(inputAxisPane, BorderLayout.EAST);
	}

	public static GameSettings showOptions(JFrame parent, GameSettings settings) {
		OptionsMenu menu = new OptionsMenu(parent, settings);
		menu.setVisible(true);
		while (menu.isVisible())
			;
		return menu.editedSettings.clone();
	}

	public void updateSettingValues() {
		chckbxBobbing.setSelected(editedSettings.enableBobbing);
		chckbxFullscreen.setSelected(editedSettings.fullscreen);
		cBoxResolution.setSelectedIndex(editedSettings.windowSizeIndex);
	}

}

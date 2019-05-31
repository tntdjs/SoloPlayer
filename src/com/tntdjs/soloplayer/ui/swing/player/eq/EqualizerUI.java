package com.tntdjs.soloplayer.ui.swing.player.eq;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class implements an equalizer UI.
 * <p/>
 * The equalizer consists of 32 band-pass filters. Each band of the equalizer
 * can take on a fractional value between -1.0 and +1.0. At -1.0, the input
 * signal is attenuated by 6dB, at +1.0 the signal is amplified by 6dB.
 */
public class EqualizerUI extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = -7025794076350484079L;

	private static Logger LOGGER = LogManager.getLogger(EqualizerUI.class.getName());
//	private static Log log = LogFactory.getLog(EqualizerUI.class);
	private int minGain = 0;
	private int maxGain = 100;
	private int[] gainValue = { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	private int[] PRESET_NORMAL = { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
	private int[] PRESET_CLASSICAL = { 50, 50, 50, 50, 50, 50, 70, 70, 70, 76 };
	private int[] PRESET_CLUB = { 50, 50, 42, 34, 34, 34, 42, 50, 50, 50 };
	private int[] PRESET_DANCE = { 26, 34, 46, 50, 50, 66, 70, 70, 50, 50 };
	private int[] PRESET_FULLBASS = { 26, 26, 26, 36, 46, 62, 76, 78, 78, 78 };
	private int[] PRESET_FULLBASSTREBLE = { 34, 34, 50, 68, 62, 46, 28, 22, 18, 18 };
	private int[] PRESET_FULLTREBLE = { 78, 78, 78, 62, 42, 24, 8, 8, 8, 8 };
	private int[] PRESET_LAPTOP = { 38, 22, 36, 60, 58, 46, 38, 24, 16, 14 };
	private int[] PRESET_LIVE = { 66, 50, 40, 36, 34, 34, 40, 42, 42, 42 };
	private int[] PRESET_PARTY = { 32, 32, 50, 50, 50, 50, 50, 50, 32, 32 };
	private int[] PRESET_POP = { 56, 38, 32, 30, 38, 54, 56, 56, 54, 54 };
	private int[] PRESET_REGGAE = { 48, 48, 50, 66, 48, 34, 34, 48, 48, 48 };
	private int[] PRESET_ROCK = { 32, 38, 64, 72, 56, 40, 28, 24, 24, 24 };
	private int[] PRESET_TECHNO = { 30, 34, 48, 66, 64, 48, 30, 24, 24, 28 };
	private JPopupMenu mainpopup = null;
	public static final int LINEARDIST = 1;
	public static final int OVERDIST = 2;
	private float[] bands = null;
	private int[] eqgains = null;
	private int eqdist = OVERDIST;

	public EqualizerUI() {
		super();
		setDoubleBuffered(true);
		eqgains = new int[10];
		setLayout(new AbsoluteLayout());
		int[] vals = {0};//config.getLastEqualizer();
		if (vals != null) {
			for (int h = 0; h < vals.length; h++) {
				gainValue[h] = vals[h];
			}
		}
		// DnD support disabled.
		DropTargetAdapter dnd = new DropTargetAdapter() {
			@Override
			public void drop(DropTargetDropEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		new DropTarget(this, DnDConstants.ACTION_COPY, dnd, false);
	}

	public void loadUI() {
		LOGGER.info("Load EqualizerUI (EDT=" + SwingUtilities.isEventDispatchThread() + ")");
		removeAll();
		// Background
		ImageBorder border = new ImageBorder();
//		border.setImage(ui.getEqualizerImage());
		setBorder(border);
		// On/Off
//		add(ui.getAcEqOnOff(), ui.getAcEqOnOff().getConstraints());
//		ui.getAcEqOnOff().removeActionListener(this);
//		ui.getAcEqOnOff().addActionListener(this);
//		// Auto
//		add(ui.getAcEqAuto(), ui.getAcEqAuto().getConstraints());
//		ui.getAcEqAuto().removeActionListener(this);
//		ui.getAcEqAuto().addActionListener(this);
//		// Sliders
//		add(ui.getAcEqPresets(), ui.getAcEqPresets().getConstraints());
//		for (int i = 0; i < ui.getAcEqSliders().length; i++) {
//			add(ui.getAcEqSliders()[i], ui.getAcEqSliders()[i].getConstraints());
//			ui.getAcEqSliders()[i].setValue(maxGain - gainValue[i]);
//			ui.getAcEqSliders()[i].removeChangeListener(this);
//			ui.getAcEqSliders()[i].addChangeListener(this);
//		}
//		if (ui.getSpline() != null) {
//			ui.getSpline().setValues(gainValue);
//			add(ui.getSpline(), ui.getSpline().getConstraints());
//		}
		// Popup menu on TitleBar
		mainpopup = new JPopupMenu();
		String[] presets = { "Normal", "Classical", "Club", "Dance", "Full Bass", "Full Bass & Treble", "Full Treble",
				"Laptop", "Live", "Party", "Pop", "Reggae", "Rock", "Techno" };
		JMenuItem mi;
		for (int p = 0; p < presets.length; p++) {
			mi = new JMenuItem(presets[p]);
			mi.removeActionListener(this);
			mi.addActionListener(this);
			mainpopup.add(mi);
		}
//		ui.getAcEqPresets().removeActionListener(this);
//		ui.getAcEqPresets().addActionListener(this);
		validate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.
	 * ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
//		for (int i = 0; i < ui.getAcEqSliders().length; i++) {
//			gainValue[i] = maxGain - ui.getAcEqSliders()[i].getValue();
//		}
//		if (ui.getSpline() != null)
//			ui.getSpline().repaint();
		// Apply equalizer values.
		synchronizeEqualizer();
	}

	/**
	 * Set bands array for equalizer.
	 *
	 * @param bands
	 */
	public void setBands(float[] bands) {
		this.bands = bands;
	}

	/**
	 * Apply equalizer function.
	 *
	 * @param gains
	 * @param min
	 * @param max
	 */
	public void updateBands(int[] gains, int min, int max) {
		if ((gains != null) && (bands != null)) {
			int j = 0;
			float gvalj = (gains[j] * 2.0f / (max - min) * 1.0f) - 1.0f;
			float gvalj1 = (gains[j + 1] * 2.0f / (max - min) * 1.0f) - 1.0f;
			// Linear distribution : 10 values => 32 values.
			if (eqdist == LINEARDIST) {
				float a = (gvalj1 - gvalj) * 1.0f;
				float b = gvalj * 1.0f - (gvalj1 - gvalj) * j;
				// x=s*x'
				float s = (gains.length - 1) * 1.0f / (bands.length - 1) * 1.0f;
				for (int i = 0; i < bands.length; i++) {
					float ind = s * i;
					if (ind > (j + 1)) {
						j++;
						gvalj = (gains[j] * 2.0f / (max - min) * 1.0f) - 1.0f;
						gvalj1 = (gains[j + 1] * 2.0f / (max - min) * 1.0f) - 1.0f;
						a = (gvalj1 - gvalj) * 1.0f;
						b = gvalj * 1.0f - (gvalj1 - gvalj) * j;
					}
					// a*x+b
					bands[i] = a * i * 1.0f * s + b;
				}
			}
			// Over distribution : 10 values => 10 first value of 32 values.
			else if (eqdist == OVERDIST) {
				for (int i = 0; i < gains.length; i++) {
					bands[i] = (gains[i] * 2.0f / (max - min) * 1.0f) - 1.0f;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		LOGGER.debug("Action=" + cmd + " (EDT=" + SwingUtilities.isEventDispatchThread() + ")");
		// On/Off
//		if (cmd.equals(PlayerActionEvent.ACEQONOFF)) {
//			if (ui.getAcEqOnOff().isSelected()) {
//				config.setEqualizerOn(true);
//			} else {
//				config.setEqualizerOn(false);
//			}
			synchronizeEqualizer();
//		}
		// Auto
//		else 
			if (cmd.equals(PlayerActionEvent.ACEQAUTO)) {
//			if (ui.getAcEqAuto().isSelected()) {
//				config.setEqualizerAuto(true);
//			} else {
//				config.setEqualizerAuto(false);
//			}
		}
		// Presets
		else if (cmd.equals(PlayerActionEvent.ACEQPRESETS)) {
			if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
//				mainpopup.show(this, ui.getAcEqPresets().getLocation().x, ui.getAcEqPresets().getLocation().y);
			}
		} else if (cmd.equals("Normal")) {
			updateSliders(PRESET_NORMAL);
			synchronizeEqualizer();
		} else if (cmd.equals("Classical")) {
			updateSliders(PRESET_CLASSICAL);
			synchronizeEqualizer();
		} else if (cmd.equals("Club")) {
			updateSliders(PRESET_CLUB);
			synchronizeEqualizer();
		} else if (cmd.equals("Dance")) {
			updateSliders(PRESET_DANCE);
			synchronizeEqualizer();
		} else if (cmd.equals("Full Bass")) {
			updateSliders(PRESET_FULLBASS);
			synchronizeEqualizer();
		} else if (cmd.equals("Full Bass & Treble")) {
			updateSliders(PRESET_FULLBASSTREBLE);
			synchronizeEqualizer();
		} else if (cmd.equals("Full Treble")) {
			updateSliders(PRESET_FULLTREBLE);
			synchronizeEqualizer();
		} else if (cmd.equals("Laptop")) {
			updateSliders(PRESET_LAPTOP);
			synchronizeEqualizer();
		} else if (cmd.equals("Live")) {
			updateSliders(PRESET_LIVE);
			synchronizeEqualizer();
		} else if (cmd.equals("Party")) {
			updateSliders(PRESET_PARTY);
			synchronizeEqualizer();
		} else if (cmd.equals("Pop")) {
			updateSliders(PRESET_POP);
			synchronizeEqualizer();
		} else if (cmd.equals("Reggae")) {
			updateSliders(PRESET_REGGAE);
			synchronizeEqualizer();
		} else if (cmd.equals("Rock")) {
			updateSliders(PRESET_ROCK);
			synchronizeEqualizer();
		} else if (cmd.equals("Techno")) {
			updateSliders(PRESET_TECHNO);
			synchronizeEqualizer();
		}
	}

	/**
	 * Update sliders from gains array.
	 *
	 * @param gains
	 */
	public void updateSliders(int[] gains) {
		if (gains != null) {
			for (int i = 0; i < gains.length; i++) {
				gainValue[i + 1] = gains[i];
//				ui.getAcEqSliders()[i + 1].setValue(maxGain - gainValue[i + 1]);
			}
		}
	}

	/**
	 * Apply equalizer values.
	 */
	public void synchronizeEqualizer() {
//		config.setLastEqualizer(gainValue);
//		if (config.isEqualizerOn()) {
			for (int j = 0; j < eqgains.length; j++) {
				eqgains[j] = -gainValue[j + 1] + maxGain;
			}
			updateBands(eqgains, minGain, maxGain);
//		} else {
//			for (int j = 0; j < eqgains.length; j++) {
//				eqgains[j] = (maxGain - minGain) / 2;
//			}
//			updateBands(eqgains, minGain, maxGain);
//		}
	}

	/**
	 * Return equalizer bands distribution.
	 * 
	 * @return
	 */
	public int getEqdist() {
		return eqdist;
	}

	/**
	 * Set equalizer bands distribution.
	 * 
	 * @param i
	 */
	public void setEqdist(int i) {
		eqdist = i;
	}
}

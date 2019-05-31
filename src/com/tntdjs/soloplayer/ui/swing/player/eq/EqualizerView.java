package com.tntdjs.soloplayer.ui.swing.player.eq;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * EqualizerView
 * @author tsenausk
 *
 */
public class EqualizerView extends JPanel {
	private static final long serialVersionUID = 8256448890262951863L;

	public EqualizerView() {
		this.setLayout(new BorderLayout());
		this.add(new JSlider(), BorderLayout.CENTER);
	}
	
	
}

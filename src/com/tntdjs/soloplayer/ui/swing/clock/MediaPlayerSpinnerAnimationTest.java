package com.tntdjs.soloplayer.ui.swing.clock;

import java.awt.BorderLayout;
import java.util.Calendar;

import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 * 
 * @author tsenausk
 *
 *	Based on lessons learned from...
 *	Copyleft 2007 Fred Swartz MIT License
 *	http://www.fredosaurus.com/notes-java/examples/animation/50analogclockbuf/analogclockbufexample.html
 */
public class MediaPlayerSpinnerAnimationTest extends JApplet {
	private static final long serialVersionUID = -1086591684769004616L;
	private MediaPlayerSpinnerAnimation MEDIA_PLAYER_SPINNER;

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Analog Clock");
		window.setContentPane(new MediaPlayerSpinnerAnimationTest());
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public MediaPlayerSpinnerAnimationTest() {
		MEDIA_PLAYER_SPINNER = new MediaPlayerSpinnerAnimation();
		setLayout(new BorderLayout());
		add(MEDIA_PLAYER_SPINNER, BorderLayout.CENTER);
		start();	//start animation
	}

	@Override
	public void start() {
		MEDIA_PLAYER_SPINNER.start( Calendar.getInstance().get(Calendar.SECOND));
	}

	@Override
	public void stop() {
		MEDIA_PLAYER_SPINNER.stop();
	}
}
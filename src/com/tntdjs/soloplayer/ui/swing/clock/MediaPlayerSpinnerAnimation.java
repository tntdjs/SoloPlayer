package com.tntdjs.soloplayer.ui.swing.clock;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * MediaPlayerSpinnerAnimation mimics a clock or a record player to 
 * offer animation to the media player.
 * 
 * @author tsenausk
 *
 */
public class MediaPlayerSpinnerAnimation extends JComponent {
	private static final long serialVersionUID = -1869649073486436293L;
	private static final double TWO_PI = 2.0 * Math.PI;
	private static final int UPDATE_INTERVAL = 100; // Millisecs
	private Calendar _now = Calendar.getInstance(); // Current time.

	private int _diameter; // Height and width of clock face
	private int _centerX; // x coord of middle of clock
	private int _centerY; // y coord of middle of clock
	private BufferedImage _clockImage; // Saved image of the clock face.
	private javax.swing.Timer _timer; // Fires to update clock.

	private int seconds = 0;
	
	public MediaPlayerSpinnerAnimation() {
		this.setDoubleBuffered(true);
		setPreferredSize(new Dimension(280, 280));

		_timer = new javax.swing.Timer(UPDATE_INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTime();
				repaint();
			}
		});
	}
	
	public void start(int startSecond) {
		seconds = startSecond;
		_timer.start();
	}

	public int stop() {
		_timer.stop();
		return seconds;
	}

	private void updateTime() { // ... Avoid creating new objects.
		_now.setTimeInMillis(System.currentTimeMillis());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// ... The panel may have been resized, get current dimensions
		int w = getWidth();
		int h = getHeight();
		int sizeAdjust = 2;
		_diameter = ((w < h) ? w : h) - sizeAdjust;
		_centerX = _diameter / 2;
		_centerY = _diameter / 2;

		// ... Create the clock face background image if this is the first time,
		// or if the size of the panel has changed
		if (_clockImage == null || _clockImage.getWidth() != w || _clockImage.getHeight() != h) {
			_clockImage = (BufferedImage) (this.createImage(w, h));

			// ... Get a graphics context from this image
			Graphics2D g2a = _clockImage.createGraphics();
			g2a.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawClockFace(g2a);
		}

		// ... Draw the clock face from the precomputed image
		g2.drawImage(_clockImage, null, 0, 0);

		// ... Draw the clock hands dynamically each time.
		drawClockHands(g2);
	}

	// Draw sweeper line
	private void drawClockHands(Graphics2D g2) {
		seconds++;
		int millis = _now.get(Calendar.MILLISECOND);
		int handMax = _diameter / 2; // Second hand extends to outer rim.
		double fseconds = (seconds + ((double) millis / 1000)) / 60.0;
		drawRadius(g2, fseconds, 0, handMax);
	}

	/**
	 * 
	 * @param g2
	 */
	private void drawClockFace(Graphics2D g2) {
		g2.setColor(new Color(184, 207, 229));	//soloplayer blue
		g2.fillOval(0, 0, _diameter, _diameter);
		g2.setColor(Color.BLACK);
		g2.drawOval(0, 0, _diameter, _diameter);
//		int radius = _diameter / 2;
//
//		// ... Draw the tick marks around the circumference.
//		for (int sec = 0; sec < 60; sec += 3) { // sec++ equals 60 ticks marks on clock
//			int tickStart;
//			if (sec % 5 == 0) {
//				tickStart = radius - 10; // Draw long tick mark every 5.
//			} else {
//				tickStart = radius - 5; // Short tick mark.
//			}
//			drawRadius(g2, sec / 30.0, tickStart, radius);
//		}
	}

	// This draw lines along a radius from the clock face center.
	// By changing the parameters, it can be used to draw tick marks,
	// as well as the hands.
	private void drawRadius(Graphics2D g2, double percent, int minRadius, int maxRadius) {
		// ... percent parameter is the fraction (0.0 - 1.0) of the way
		// clockwise from 12. Because the Graphics2D methods use radians
		// counterclockwise from 3, a little conversion is necessary.
		double radians = (0.5 - percent) * TWO_PI;
		double sine = Math.sin(radians);
		double cosine = Math.cos(radians);

		int dxmin = _centerX + (int) (minRadius * sine);
		int dymin = _centerY + (int) (minRadius * cosine);

		int dxmax = _centerX + (int) (maxRadius * sine);
		int dymax = _centerY + (int) (maxRadius * cosine);
		g2.drawLine(dxmin, dymin, dxmax, dymax);
	}
}
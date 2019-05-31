/*
 * SplinePanel.
 * 
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
package com.tntdjs.soloplayer.ui.swing.player.eq;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.JPanel;

import com.tntdjs.soloplayer.ui.swing.player.spectrumanalyzer.AbsoluteConstraints;


public class SplinePanel extends JPanel {
	private static final long serialVersionUID = 4718706615860951982L;

	private AbsoluteConstraints constraints = null;
	private Image backgroundImage = null;
	private Image barImage = null;
	private int[] values = null;
	private Color[] gradient = null;

	public SplinePanel() {
		super();
		setDoubleBuffered(true);
		setLayout(null);
	}

	public Color[] getGradient() {
		return gradient;
	}

	public void setGradient(Color[] gradient) {
		this.gradient = gradient;
	}

	public void setConstraints(AbsoluteConstraints cnts) {
		constraints = cnts;
	}

	public AbsoluteConstraints getConstraints() {
		return constraints;
	}

	public Image getBarImage() {
		return barImage;
	}

	public void setBarImage(Image barImage) {
		this.barImage = barImage;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public int[] getValues() {
		return values;
	}

	public void setValues(int[] values) {
		this.values = values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		if (backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, null);
		if (barImage != null)
			g.drawImage(barImage, 0, getHeight() / 2, null);
		if ((values != null) && (values.length > 0)) {
			NaturalSpline curve = new NaturalSpline();
			float dx = 1.0f * getWidth() / (values.length - 2);
			int h = getHeight();
			curve.setMaxHeight(h);
			curve.setMinHeight(0);
			for (int i = 1; i < values.length; i++) {
				int x1 = (int) Math.round(dx * (Float.intBitsToFloat(i) - 1));
				int y1 = ((int) Math.round((Float.intBitsToFloat(h) * values[i] / 100)));
				Math.round((Float.intBitsToFloat(h) * values[i] / 100));
				y1 = curve.boundY(y1);
				curve.addPoint(x1, y1);
			}
			Polygon spline = curve.getPolyline();
			if (gradient != null) {
				for (int i = 0; i < (spline.npoints - 1); i++) {
					g.setColor(gradient[spline.ypoints[i]]);
					g.drawLine(spline.xpoints[i], spline.ypoints[i], spline.xpoints[i + 1], spline.ypoints[i + 1]);
				}
			} else {
				g.drawPolyline(spline.xpoints, spline.ypoints, spline.npoints);
			}
		}
	}
}

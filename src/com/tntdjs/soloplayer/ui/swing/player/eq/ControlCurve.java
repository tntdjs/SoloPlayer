/*
 * ControlCurve.
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

import java.awt.Polygon;

public abstract class ControlCurve {
	static final int EPSILON = 36; /* square of distance for picking */
	protected Polygon pts;
	protected int selection = -1;
	int maxHeight = -1;
	int minHeight = -1;

	public ControlCurve() {
		pts = new Polygon();
	}

	public int boundY(int y) {
		int ny = y;
		if ((minHeight >= 0) && (y < minHeight)) {
			ny = 0;
		}
		if ((maxHeight >= 0) && (y >= maxHeight)) {
			ny = maxHeight - 1;
		}
		return ny;
	}

	public void setMaxHeight(int h) {
		maxHeight = h;
	}

	public void setMinHeight(int h) {
		minHeight = h;
	}

	/**
	 * Return index of control point near to (x,y) or -1 if nothing near.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int selectPoint(int x, int y) {
		int mind = Integer.MAX_VALUE;
		selection = -1;
		for (int i = 0; i < pts.npoints; i++) {
			int d = sqr(pts.xpoints[i] - x) + sqr(pts.ypoints[i] - y);
			if (d < mind && d < EPSILON) {
				mind = d;
				selection = i;
			}
		}
		return selection;
	}

	/**
	 * Square of an int.
	 * 
	 * @param x
	 * @return
	 */
	static int sqr(int x) {
		return x * x;
	}

	/**
	 * Add a control point, return index of new control point.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int addPoint(int x, int y) {
		pts.addPoint(x, y);
		return selection = pts.npoints - 1;
	}

	/**
	 * Set selected control point.
	 * 
	 * @param x
	 * @param y
	 */
	public void setPoint(int x, int y) {
		if (selection >= 0) {
			pts.xpoints[selection] = x;
			pts.ypoints[selection] = y;
		}
	}

	/**
	 * Remove selected control point.
	 */
	public void removePoint() {
		if (selection >= 0) {
			pts.npoints--;
			for (int i = selection; i < pts.npoints; i++) {
				pts.xpoints[i] = pts.xpoints[i + 1];
				pts.ypoints[i] = pts.ypoints[i + 1];
			}
		}
	}

	public abstract Polygon getPolyline();
}

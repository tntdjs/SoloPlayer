/*
 * Cubic.
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

public class Cubic {
	float a, b, c, d; /* a + b*u + c*u^2 +d*u^3 */

	public Cubic(float a, float b, float c, float d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	/**
	 * Evaluate cubic.
	 * 
	 * @param u
	 * @return
	 */
	public float eval(float u) {
		return (((d * u) + c) * u + b) * u + a;
	}
}

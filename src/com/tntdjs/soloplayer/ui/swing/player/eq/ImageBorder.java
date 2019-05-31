/*
 * ImageBorder.
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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.border.Border;

public class ImageBorder implements Border {
	private Insets insets = new Insets(0, 0, 0, 0);
	private Image image = null;

	public ImageBorder() {
		super();
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (image != null) {
			int x0 = x + (width - image.getWidth(null)) / 2;
			int y0 = y + (height - image.getHeight(null)) / 2;
			g.drawImage(image, x0, y0, null);
		}
	}

	public Insets getBorderInsets(Component c) {
		return insets;
	}
}

/*
 * AbsoluteLayout.
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

/**
 * AbsoluteLayout is a LayoutManager that works as a replacement for "null"
 * layout to allow placement of components in absolute positions.
 */
public class AbsoluteLayout implements LayoutManager2, java.io.Serializable {
	/**
	 * generated Serialized Version UID
	 */
	static final long serialVersionUID = -1919857869177070440L;

	/**
	 * Adds the specified component with the specified name to the layout.
	 *
	 * @param name
	 *            the component name
	 * @param comp
	 *            the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
		throw new IllegalArgumentException();
	}

	/**
	 * Removes the specified component from the layout.
	 *
	 * @param comp
	 *            the component to be removed
	 */
	public void removeLayoutComponent(Component comp) {
		constraints.remove(comp);
	}

	/**
	 * Calculates the preferred dimension for the specified panel given the
	 * components in the specified parent container.
	 *
	 * @param parent
	 *            the component to be laid out
	 * @see #minimumLayoutSize
	 */
	public Dimension preferredLayoutSize(Container parent) {
		int maxWidth = 0;
		int maxHeight = 0;
		for (java.util.Enumeration<Component> e = constraints.keys(); e.hasMoreElements();) {
			Component comp = e.nextElement();
			AbsoluteConstraints ac = (AbsoluteConstraints) constraints.get(comp);
			Dimension size = comp.getPreferredSize();
			int width = ac.getWidth();
			if (width == -1)
				width = size.width;
			int height = ac.getHeight();
			if (height == -1)
				height = size.height;
			if (ac.x + width > maxWidth)
				maxWidth = ac.x + width;
			if (ac.y + height > maxHeight)
				maxHeight = ac.y + height;
		}
		return new Dimension(maxWidth, maxHeight);
	}

	/**
	 * Calculates the minimum dimension for the specified panel given the
	 * components in the specified parent container.
	 *
	 * @param parent
	 *            the component to be laid out
	 * @see #preferredLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent) {
		int maxWidth = 0;
		int maxHeight = 0;
		for (java.util.Enumeration<Component> e = constraints.keys(); e.hasMoreElements();) {
			Component comp = e.nextElement();
			AbsoluteConstraints ac = (AbsoluteConstraints) constraints.get(comp);
			Dimension size = comp.getMinimumSize();
			int width = ac.getWidth();
			if (width == -1)
				width = size.width;
			int height = ac.getHeight();
			if (height == -1)
				height = size.height;
			if (ac.x + width > maxWidth)
				maxWidth = ac.x + width;
			if (ac.y + height > maxHeight)
				maxHeight = ac.y + height;
		}
		return new Dimension(maxWidth, maxHeight);
	}

	/**
	 * Lays out the container in the specified panel.
	 *
	 * @param parent
	 *            the component which needs to be laid out
	 */
	public void layoutContainer(Container parent) {
		for (java.util.Enumeration<Component> e = constraints.keys(); e.hasMoreElements();) {
			Component comp = e.nextElement();
			AbsoluteConstraints ac = (AbsoluteConstraints) constraints.get(comp);
			Dimension size = comp.getPreferredSize();
			int width = ac.getWidth();
			if (width == -1)
				width = size.width;
			int height = ac.getHeight();
			if (height == -1)
				height = size.height;
			comp.setBounds(ac.x, ac.y, width, height);
		}
	}

	/**
	 * Adds the specified component to the layout, using the specified
	 * constraint object.
	 *
	 * @param comp
	 *            the component to be added
	 * @param constr
	 *            where/how the component is added to the layout.
	 */
	public void addLayoutComponent(Component comp, Object constr) {
		if (!(constr instanceof AbsoluteConstraints))
			throw new IllegalArgumentException();
		constraints.put(comp, constr);
	}

	/**
	 * Returns the maximum size of this component.
	 *
	 * @see java.awt.Component#getMinimumSize()
	 * @see java.awt.Component#getPreferredSize()
	 * @see LayoutManager
	 */
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Returns the alignment along the x axis. This specifies how the component
	 * would like to be aligned relative to other components. The value should
	 * be a number between 0 and 1 where 0 represents alignment along the
	 * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
	 * etc.
	 */
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * Returns the alignment along the y axis. This specifies how the component
	 * would like to be aligned relative to other components. The value should
	 * be a number between 0 and 1 where 0 represents alignment along the
	 * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
	 * etc.
	 */
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * Invalidates the layout, indicating that if the layout manager has cached
	 * information it should be discarded.
	 */
	public void invalidateLayout(Container target) {
	}

	/**
	 * A mapping <Component, AbsoluteConstraints>
	 */
	protected java.util.Hashtable<Component, Object> constraints = new java.util.Hashtable<Component, Object>();
}

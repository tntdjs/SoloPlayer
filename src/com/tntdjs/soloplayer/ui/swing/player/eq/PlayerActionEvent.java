/*
 * PlayerActionEvent.
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

import java.awt.event.ActionEvent;

public class PlayerActionEvent extends ActionEvent {
	private static final long serialVersionUID = 8756466368659731805L;

	public static final String ACPREVIOUS = "Previous";
	public static final String ACPLAY = "Play";
	public static final String ACPAUSE = "Pause";
	public static final String ACSTOP = "Stop";
	public static final String ACNEXT = "Next";
	public static final String ACEJECT = "Eject";
	public static final String ACEQUALIZER = "EqualizerUI";
	public static final String ACPLAYLIST = "Playlist";
	public static final String ACSHUFFLE = "Shuffle";
	public static final String ACREPEAT = "Repeat";
	public static final String ACVOLUME = "Volume";
	public static final String ACBALANCE = "Balance";
	public static final String ACTITLEBAR = "TitleBar";
	public static final String ACEXIT = "Exit";
	public static final String ACMINIMIZE = "Minimize";
	public static final String ACPOSBAR = "Seek";
	public static final String MIPLAYFILE = "PlayFileMI";
	public static final String MIPLAYLOCATION = "PlayLocationMI";
	public static final String MIPLAYLIST = "PlaylistMI";
	public static final String MIEQUALIZER = "EqualizerMI";
	public static final String MIPREFERENCES = "PreferencesMI";
	public static final String MISKINBROWSER = "SkinBrowserMI";
	public static final String MILOADSKIN = "LoadSkinMI";
	public static final String MIJUMPFILE = "JumpFileMI";
	public static final String MISTOP = "StopMI";
	public static final String EQSLIDER = "SliderEQ";
	public static final String ACEQPRESETS = "PresetsEQ";
	public static final String ACEQONOFF = "OnOffEQ";
	public static final String ACEQAUTO = "AutoEQ";
	public static final String ACPLUP = "ScrollUpPL";
	public static final String ACPLDOWN = "ScrollDownPL";
	public static final String ACPLINFO = "InfoPL";
	public static final String ACPLPLAY = "PlayPL";
	public static final String ACPLREMOVE = "RemovePL";
	public static final String ACPLADDPOPUP = "AddPopupPL";
	public static final String ACPLADDFILE = "AddFilePL";
	public static final String ACPLADDDIR = "AddDirPL";
	public static final String ACPLADDURL = "AddURLPL";
	public static final String ACPLREMOVEPOPUP = "RemovePopupPL";
	public static final String ACPLREMOVEMISC = "RemoveMiscPL";
	public static final String ACPLREMOVESEL = "RemoveSelPL";
	public static final String ACPLREMOVEALL = "RemoveAllPL";
	public static final String ACPLREMOVECROP = "RemoveCropPL";
	public static final String ACPLSELPOPUP = "SelectPopupPL";
	public static final String ACPLSELALL = "SelectAllPL";
	public static final String ACPLSELZERO = "SelectZeroPL";
	public static final String ACPLSELINV = "SelectInvPL";
	public static final String ACPLMISCPOPUP = "MiscPopupPL";
	public static final String ACPLMISCOPTS = "MiscOptsPL";
	public static final String ACPLMISCFILE = "MiscFilePL";
	public static final String ACPLMISCSORT = "MiscSortPL";
	public static final String ACPLLISTPOPUP = "ListPopupPL";
	public static final String ACPLLISTLOAD = "ListLoadPL";
	public static final String ACPLLISTSAVE = "ListSavePL";
	public static final String ACPLLISTNEW = "ListNewPL";

	public PlayerActionEvent(Object source, int id, String command) {
		super(source, id, command);
	}

	public PlayerActionEvent(Object source, int id, String command, int modifiers) {
		super(source, id, command, modifiers);
	}

	public PlayerActionEvent(Object source, int id, String command, long when, int modifiers) {
		super(source, id, command, when, modifiers);
	}
}

package com.tntdjs.soloplayer.ui.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.tntdjs.utils.i18n.TranslationMgr;
import com.tntdjs.soloplayer.RootAppMgr;
import com.tntdjs.soloplayer.SoloPlayerSwingAppMain;
import com.tntdjs.soloplayer.playlist.editor.PlaylistEditHelper;

/**
 * 
 * @author tsenausk
 *
 */
public class SPMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 822975412846316343L;
	private static SPMenuBar INSTANCE;
	
	private JMenu FILE_MENU;
	private JMenuItem closeMenuItem;
	private JMenuItem exitMenuItem;

	private JMenu HELP_MENU;
	private JMenuItem aboutMenuItem;

	private JMenu PLAYLIST_MENU;
	private JMenuItem playPLMenuItem;
	private JMenuItem addPLMenuItem;
	private JMenuItem editPLMenuItem;
	private JMenuItem deletePLMenuItem;
	
	private JMenu PLAYER_MENU;
	private JMenuItem playerAutoPlayMenuItem;
	private JMenuItem playerEQMenuItem;
	private JMenuItem playerPlayMenuItem;
	
	public static SPMenuBar getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new SPMenuBar();
		}
		return INSTANCE;
	}
	
	/**
	 * Constructor 
	 */
	public SPMenuBar() {
		//Build the File menu.
		FILE_MENU = new JMenu(TranslationMgr.getInstance().getText("File"));
		FILE_MENU.setMnemonic(KeyEvent.VK_F);
		FILE_MENU.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("File Menu"));
		add(FILE_MENU);
				
			//a group of JMenuItems
			closeMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("Close"), KeyEvent.VK_C);
			closeMenuItem.setActionCommand("CLOSE");
			closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
			closeMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("CloseAccessible"));
			closeMenuItem.addActionListener(this);
			closeMenuItem.setEnabled(false);
			FILE_MENU.add(closeMenuItem);
			
			FILE_MENU.addSeparator();
			
			//a group of JMenuItems
			exitMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("Exit"), KeyEvent.VK_X);
			exitMenuItem.setActionCommand("EXIT");
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
			exitMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("ExitAccessible"));
			exitMenuItem.addActionListener(this);
			FILE_MENU.add(exitMenuItem);

		//Build the Playlist menu.
		PLAYLIST_MENU = new JMenu(TranslationMgr.getInstance().getText("Playlist"));
		PLAYLIST_MENU.setMnemonic(KeyEvent.VK_L);
		PLAYLIST_MENU.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlaylistAccessible"));
		add(PLAYLIST_MENU);
		
			//a group of JMenuItems
			playPLMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("PlaylistAccessible"), KeyEvent.VK_O);
			playPLMenuItem.setActionCommand("PL-PLAY");
			playPLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
			playPLMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlaylistAccessible"));
			playPLMenuItem.addActionListener(this);
			playPLMenuItem.setEnabled(true);
			PLAYLIST_MENU.add(playPLMenuItem);

			PLAYLIST_MENU.addSeparator();
			
			addPLMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("PlaylistAdd"), KeyEvent.VK_O);
			addPLMenuItem.setActionCommand("PL-ADD");
			addPLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
			addPLMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlaylistAddAccessible"));
			addPLMenuItem.addActionListener(this);
			addPLMenuItem.setEnabled(true);
			PLAYLIST_MENU.add(addPLMenuItem);
			
			editPLMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("PlaylistEdit"), KeyEvent.VK_O);
			editPLMenuItem.setActionCommand("PL-EDIT");
			editPLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
			editPLMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlaylistEditAccessible"));
			editPLMenuItem.addActionListener(this);
			editPLMenuItem.setEnabled(true);
			PLAYLIST_MENU.add(editPLMenuItem);
			
			deletePLMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("PlaylistDelete"), KeyEvent.VK_O);
			deletePLMenuItem.setActionCommand("PL-DELETE");
			deletePLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
			deletePLMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlaylistDeleteAccessible"));
			deletePLMenuItem.addActionListener(this);
			deletePLMenuItem.setEnabled(true);
			PLAYLIST_MENU.add(deletePLMenuItem);
			
		//Build the Playlist menu.
		PLAYER_MENU = new JMenu(TranslationMgr.getInstance().getText("Player"));
		PLAYER_MENU.setMnemonic(KeyEvent.VK_R);
		PLAYER_MENU.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlayerAccessible"));
		add(PLAYER_MENU);

			//a group of JMenuItems
			playerPlayMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("PlayerPlayAccessible"), KeyEvent.VK_P);
			playerPlayMenuItem.setActionCommand("P-Play");
			playerPlayMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
			playerPlayMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlayerPlay"));
			playerPlayMenuItem.addActionListener(this);
			playerPlayMenuItem.setEnabled(true);
			PLAYER_MENU.add(playerPlayMenuItem);

			PLAYER_MENU.addSeparator();

			//a group of JMenuItems
			playerAutoPlayMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("PlayerAutoPlayAccessible"), KeyEvent.VK_A);
			playerAutoPlayMenuItem.setActionCommand("P-AutoPlay");
			playerAutoPlayMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
			playerAutoPlayMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlayerAutoPlay"));
			playerAutoPlayMenuItem.addActionListener(this);
			playerAutoPlayMenuItem.setEnabled(true);
			PLAYER_MENU.add(playerAutoPlayMenuItem);
				
			//a group of JMenuItems
			playerEQMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("PlayerEQAccessible"), KeyEvent.VK_E);
			playerEQMenuItem.setActionCommand("P-EQ");
			playerEQMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
			playerEQMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("PlayerEQ"));
			playerEQMenuItem.addActionListener(this);
			playerEQMenuItem.setEnabled(false);
			PLAYER_MENU.add(playerEQMenuItem);
					
		PLAYER_MENU.setEnabled(false);
			
		//Build the Help menu.
		HELP_MENU = new JMenu(TranslationMgr.getInstance().getText("Help"));
		HELP_MENU.setMnemonic(KeyEvent.VK_H);
		HELP_MENU.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("HelpAccessible"));
		add(HELP_MENU);
		
			//a group of JMenuItems
			aboutMenuItem = new JMenuItem(TranslationMgr.getInstance().getText("About"), KeyEvent.VK_B);
			aboutMenuItem.setActionCommand("ABOUT");
			aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
			aboutMenuItem.getAccessibleContext().setAccessibleDescription(TranslationMgr.getInstance().getText("AboutAccessible"));
			aboutMenuItem.addActionListener(this);
			HELP_MENU.add(aboutMenuItem);

		INSTANCE = this;
	}

	/**
	 * Handle all Menu option clicks here
	 */
    public void actionPerformed(ActionEvent menuAction) {    	
        switch (menuAction.getActionCommand()) {
            case "CLOSE":
        		close();
                break;
            case "EXIT":
            	SoloPlayerSwingAppMain.getInstance().CloseApplication();
            	break;
            case "ABOUT":
            	displayAboutMsg();
            	break;
            case "PL-PLAY":
	    		showPlayer(false);
	            break;
            case "PL-ADD":
            	RootAppMgr.getInstance().showPlaylistEditorPanelAdd();
            	break;
            case "PL-EDIT":
            	RootAppMgr.getInstance().showPlaylistEditorPanelEdit();
            	break;
            case "PL-DELETE":
            	PlaylistEditHelper.getInstance().deletePlaylist();
            	break;            	
            case "P-EQ":
            	showEQView();
            	break;
            case "P-AutoPlay":
            	RootAppMgr.getInstance().getPlayerView().toggleAutoPlay();
            	break;
            default:
                throw new IllegalArgumentException("Invalid menu action: " + menuAction + " - " + menuAction.getActionCommand());
        }
    }

    private void close() {
    	if (RootAppMgr.getInstance().getCurrentView().equals(RootAppMgr.PLAYER_PANEL)) {
    		hidePlayer();
    	} else if (RootAppMgr.getInstance().getCurrentView().equals(RootAppMgr.PLAYER_EQ_PANEL)) {
    		showPlayer(true);
    	}
    }
    
    /**
     * 
     */
    public void showPlayer(boolean direction) {
    	closeMenuItem.setEnabled(true);
    	setPlaylistMenuVisible(false);
    	PLAYER_MENU.setEnabled(true);
    	playerEQMenuItem.setEnabled(true);
    	RootAppMgr.getInstance().showPlayerPanel();
    }

    public void setPlaylistMenuVisible(boolean visible) {
    	PLAYLIST_MENU.setEnabled(visible);
    }

    /**
     * 
     */
    public void hidePlayer() {
    	closeMenuItem.setEnabled(false);
    	setPlaylistMenuVisible(true);
    	PLAYER_MENU.setEnabled(false);
    	RootAppMgr.getInstance().showPlaylistPanel();
    }

    public void showEQView() {
    	closeMenuItem.setEnabled(true);
    	PLAYER_MENU.setEnabled(false);
    	RootAppMgr.getInstance().showPlayerEQPanel();
    }

    public void displayAboutMsg() {
    	JOptionPane.showMessageDialog(null,    			 
    			TranslationMgr.getInstance().getText("CopyrightStatement"), 
    			TranslationMgr.getInstance().getText("About") + " - " + TranslationMgr.getInstance().getAppTitle(),
    			JOptionPane.OK_OPTION);
    }
}

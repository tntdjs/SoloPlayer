package com.tntdjs.soloplayer;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import com.tntdjs.mediaplayer.IMediaPlayerController;
import com.tntdjs.soloplayer.data.config.objects.Playlist;
import com.tntdjs.soloplayer.media.player.MediaPlayerController;
import com.tntdjs.soloplayer.ui.swing.menu.SPMenuBar;
import com.tntdjs.soloplayer.ui.swing.player.PlayerView;
import com.tntdjs.soloplayer.ui.swing.player.eq.EqualizerView;
import com.tntdjs.soloplayer.ui.swing.playlist.PlaylistView;
import com.tntdjs.soloplayer.ui.swing.playlist.editor.PlaylistEditor;



/**
 * 
 * @author tsenausk
 *
 */
public class RootAppMgr {
	
	private static RootAppMgr INSTANCE;
	private JPanel CARD_PANEL = new JPanel(new CardLayout());
	
	public static final String PLAYLIST_PANEL = "PLAYLIST_PANEL";
	public static final String PLAYER_PANEL = "PLAYER_PANEL";
	public static final String PLAYER_EQ_PANEL = "PLAYER_EQ_PANEL";
	public static final String PLAYLIST_EDITOR = "PLAYLIST_EDITOR";
	
	public static final String PLAYER_CONTROLLER = "PLAYER_CONTROLLER"; 
	public static final String PLAYLIST_CONTROLLER = "PLAYLIST_CONTROLLER";

	public final IMediaPlayerController MEDIA_PLAYER_CONTROLLER = new MediaPlayerController();
	
	private String CURRENT_VIEW = PLAYLIST_PANEL;
	
	private PlaylistView PLAYLISTVIEW = new PlaylistView();
	private PlayerView PLAYERVIEW = new PlayerView();
	private EqualizerView PLAYEREQVIEW = new EqualizerView();
	private PlaylistEditor PLAYLISTEDITOR = new PlaylistEditor();
	
	/**
	 * Constructor
	 */
	public RootAppMgr() {
		JPanel playlistPanel = new JPanel();
		JPanel playerPanel = new JPanel();
		JPanel eqPanel = new JPanel();
		JPanel playlistEditorPanel = new JPanel();
				
		CARD_PANEL.add(playlistPanel, PLAYLIST_PANEL);
		playlistPanel.setLayout(new BorderLayout());
		playlistPanel.add(PLAYLISTVIEW, BorderLayout.CENTER);
		
		CARD_PANEL.add(playerPanel, PLAYER_PANEL);
		playerPanel.setLayout(new BorderLayout());
		playerPanel.add(PLAYERVIEW, BorderLayout.CENTER);
		
		CARD_PANEL.add(eqPanel, PLAYER_EQ_PANEL);
		eqPanel.setLayout(new BorderLayout());
		eqPanel.add(PLAYEREQVIEW, BorderLayout.CENTER);
		
		CARD_PANEL.add(playlistEditorPanel, PLAYLIST_EDITOR);
		playlistEditorPanel.setLayout(new BorderLayout());
		playlistEditorPanel.add(PLAYLISTEDITOR, BorderLayout.CENTER);
					
	}
	
	/**
	 * showPlayerPanel
	 * 
	 */
	public void showPlayerPanel() {
		CardLayout cl = (CardLayout)(CARD_PANEL.getLayout());
		setCurrentView(PLAYER_PANEL);
	    cl.show(CARD_PANEL, PLAYER_PANEL);
	}
	
	/**
	 * showPlaylistPanel
	 * 
	 */
	public void showPlaylistPanel() {
		setCurrentView(PLAYLIST_PANEL);
		((CardLayout)(CARD_PANEL.getLayout())).show(CARD_PANEL, PLAYLIST_PANEL);
	}
	
	/**
	 * showPlayerEQPanel
	 * 
	 */
	public void showPlayerEQPanel() {
		setCurrentView(PLAYER_EQ_PANEL);
		((CardLayout)(CARD_PANEL.getLayout())).show(CARD_PANEL, PLAYER_EQ_PANEL);
	}
	
	public void showPlaylistEditorPanelAdd() {		
		PLAYLISTEDITOR.setcurrentPlaylist(new Playlist(), true);
		SPMenuBar.getInstance().setPlaylistMenuVisible(false);
		setCurrentView(PLAYLIST_EDITOR);
		((CardLayout)(CARD_PANEL.getLayout())).show(CARD_PANEL, PLAYLIST_EDITOR); //, true);
	}
	
	public void showPlaylistEditorPanelEdit() {
		PLAYLISTEDITOR.setcurrentPlaylist(getPlayListview().getCurrentPlaylist(), false);
		SPMenuBar.getInstance().setPlaylistMenuVisible(false);
		setCurrentView(PLAYLIST_EDITOR);
		((CardLayout)(CARD_PANEL.getLayout())).show(CARD_PANEL, PLAYLIST_EDITOR); //, true);
	}
	
	public JPanel getRootPanel() {
		return CARD_PANEL;
	}
	
	public static RootAppMgr getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new RootAppMgr();
		}
		return INSTANCE;
	}
	
	public PlaylistView getPlayListview() {
		return PLAYLISTVIEW;
	}

	public PlayerView getPlayerView() {
		return PLAYERVIEW;
	}

	public String getCurrentView() {
		return CURRENT_VIEW;
	}

	private void setCurrentView(String cURRENT_VIEW) {
		CURRENT_VIEW = cURRENT_VIEW;
	}
	
	public IMediaPlayerController getSoloPlayerController(String requestedController) {
		if (PLAYER_CONTROLLER.equalsIgnoreCase(requestedController)) {
			return MEDIA_PLAYER_CONTROLLER;
		} else if (PLAYLIST_CONTROLLER.equalsIgnoreCase(requestedController)) {
			return null;
		}
		return null;
	}

}

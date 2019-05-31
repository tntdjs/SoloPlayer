package com.tntdjs.soloplayer.ui.swing.nowplayingfooter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tntdjs.utils.i18n.TranslationMgr;
import com.tntdjs.soloplayer.ui.swing.player.PlayerStatus;

/**
 * 
 * @author tsenausk
 *
 */
public class NowPlayingFooterView {
	private JPanel FOOTER_PANEL;
	private JLabel STATUS_FOOTER_LABEL = new JLabel("...");
	private JLabel PLAYLIST_FOOTER_LABEL = new JLabel("...");
	private JLabel NOW_PLAYING_FOOTER_LABEL = new JLabel("...");
	
	private Map<PlayerStatus, String> STATUS_LIST = new HashMap<PlayerStatus, String>();
	
	public NowPlayingFooterView() {
		STATUS_LIST.put(PlayerStatus.PLAYING_STATUS, TranslationMgr.getInstance().getText("StatusListPlaying"));
		STATUS_LIST.put(PlayerStatus.PAUSED_STATUS, TranslationMgr.getInstance().getText("StatusListPaused"));
		STATUS_LIST.put(PlayerStatus.STOPPED_STATUS, TranslationMgr.getInstance().getText("StatusListStopped"));

	}
	
	public void updateData() {
	}
	
	/**
	 * getFooterPanel
	 * @return JPanel
	 */
	public JPanel getFooterPanel() {
		FOOTER_PANEL = new JPanel(new BorderLayout());
		
		JPanel FOOTERPANEL_CENTER = new JPanel(new FlowLayout());
		FOOTERPANEL_CENTER.add(new JLabel(TranslationMgr.getInstance().getText("PlaylistFooterLabel" ) + " "));
		FOOTERPANEL_CENTER.add(PLAYLIST_FOOTER_LABEL);
		
		JPanel FOOTERPANEL_WEST = new JPanel(new FlowLayout());
		FOOTERPANEL_WEST.add(new JLabel(TranslationMgr.getInstance().getText("StatusFooterLabel" ) + " "));
		FOOTERPANEL_WEST.add(STATUS_FOOTER_LABEL);
		setStatus(PlayerStatus.STOPPED_STATUS);
		
		JPanel FOOTERPANEL_EAST = new JPanel(new FlowLayout());
		FOOTERPANEL_EAST.add(new JLabel(TranslationMgr.getInstance().getText("SongPlayingFooterLabel") + " "));
		FOOTERPANEL_EAST.add(NOW_PLAYING_FOOTER_LABEL);
		
		FOOTER_PANEL.add(FOOTERPANEL_CENTER, BorderLayout.CENTER);
		FOOTER_PANEL.add(FOOTERPANEL_WEST, BorderLayout.WEST);
		FOOTER_PANEL.add(FOOTERPANEL_EAST, BorderLayout.EAST);
		return FOOTER_PANEL;
	}
	
	/**
	 * Sets the status text in the footer of the playlist view
	 * @param PlayerStatus<enum> of valid status enumerations
	 * 
	 */
	public void setStatus(PlayerStatus status) {
		STATUS_FOOTER_LABEL.setText(STATUS_LIST.get(status));
	}
	
	/**
	 * Sets the playlist name in the footer
	 * @param playlistName
	 */
	public void setPlaylistNameFooter(String playlistName) {
		this.PLAYLIST_FOOTER_LABEL.setText(playlistName);
	}

	/**
	 * Sets the now playing song text in the footer of the playlist view
	 * @param nowPlayingSong
	 */
	public void setNowPlayingSong(String nowPlayingSong) {
		NOW_PLAYING_FOOTER_LABEL.setText(nowPlayingSong);
	}

}
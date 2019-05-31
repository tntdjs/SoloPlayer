package com.tntdjs.soloplayer.ui.swing.playlist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.SystemPropertyMgr;
import com.tntdjs.utils.i18n.TranslationMgr;
import com.tntdjs.soloplayer.RootAppMgr;
import com.tntdjs.soloplayer.SoloPlayerSwingAppMain;
import com.tntdjs.soloplayer.data.PlayListModel;
import com.tntdjs.soloplayer.data.PlaylistMgr;
import com.tntdjs.soloplayer.data.config.objects.Playlist;
import com.tntdjs.soloplayer.playlist.editor.PlaylistEditHelper;
import com.tntdjs.soloplayer.ui.swing.menu.SPMenuBar;
import com.tntdjs.soloplayer.ui.swing.nowplayingfooter.NowPlayingFooterView;
import com.tntdjs.soloplayer.ui.swing.player.PlayerStatus;

/**
 * Playlist main view
 * @author tsenausk
 *
 */
public class PlaylistView extends JPanel implements ListSelectionListener, MouseListener {
	private static Logger LOGGER = LogManager.getRootLogger();
	private static final long serialVersionUID = -3634550822557319379L;
	private JScrollPane playListScrollPane;
	private JList<Playlist> playJList;
	private NowPlayingFooterView FOOTER_VIEW = new NowPlayingFooterView();

	private int userGestureMouseStartX = 0;
	private int userGestureMouseEndX = 0;
	
	private JButton playListButton;
	private JButton currentPLButton;
    	
	/**
	 * Constructor
	 */
	public PlaylistView() {
		setLayout(new BorderLayout());
		setDoubleBuffered(true);		
						
		add(getNorth(), BorderLayout.NORTH);
		add(getCenter(), BorderLayout.CENTER);
		add(getSouth(), BorderLayout.SOUTH);
		
		playJList.addMouseListener(this);
		playListScrollPane.requestFocus();
	}
	
	private JPanel getNorth() {
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		playListButton = new JButton(TranslationMgr.getInstance().getText("PlaylistAccessible"));
		playListButton.setPreferredSize(new Dimension(150, 28));
		playListButton.setMaximumSize(new Dimension(150, 28));
		playListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        		LoadPlaylistInPlayer();
            }
        });		
		buttonPanel.add(playListButton);
		
		
		currentPLButton = new JButton(TranslationMgr.getInstance().getText("PlaylistCurrentAccessible"));			
		currentPLButton.setPreferredSize(new Dimension(150, 28));
		currentPLButton.setMaximumSize(new Dimension(150, 28));
		currentPLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	RootAppMgr.getInstance().showPlayerPanel();
            }
        });		
		currentPLButton.setEnabled(false);
		buttonPanel.add(currentPLButton);
		
		return buttonPanel;
	}
	
	private JScrollPane getCenter() {
        //Create the list and put it in a scroll pane.
        playJList = new JList<Playlist>(PlaylistMgr.getInstance().getPlayListModel());
        playJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playJList.setSelectedIndex(0);
        playJList.addListSelectionListener(this);
        playJList.setVisibleRowCount(4);
        playJList.setFixedCellHeight(74);
        UIManager.put("ScrollBar.width", Integer.parseInt(SystemPropertyMgr.getInstance().getString("app.scrollBar.width")));
        playListScrollPane = new JScrollPane(playJList);
		
		return playListScrollPane;
	}
	
	private JPanel getSouth() {
		return FOOTER_VIEW.getFooterPanel();
	}
	
	/**
	 * Sets the status text in the footer of the playlist view
	 * @param PlayerStatus<enum> of valid status enumerations
	 * 
	 */
	public void setStatus(PlayerStatus status) {
		FOOTER_VIEW.setStatus(status);
	}

	/**
	 * Sets the playlist name in the footer
	 * @param playlistName
	 */
	public void setPlaylistNameFooter(String playlistName) {
		currentPLButton.setEnabled(true);
		FOOTER_VIEW.setPlaylistNameFooter(playlistName);
	}
	
	/**
	 * Sets the now playing song text in the footer of the playlist view
	 * @param nowPlayingSong
	 */
	public void setNowPlayingSong(String nowPlayingSong) {
		FOOTER_VIEW.setNowPlayingSong(nowPlayingSong);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) { }
	
	/**
	 * deleteCurrentPlaylist
	 * @return DefaultListModel<Playlist>
	 */
	public PlayListModel deleteCurrentPlaylist() {
		String deletePlaylist = playJList.getSelectedValue().getPlName();
		Object selectedValue = JOptionPane.showConfirmDialog(SoloPlayerSwingAppMain.getInstance().getFrame(), 
			TranslationMgr.getInstance().getText("DeletePLAreYouSure") + "\n" + deletePlaylist, 
			TranslationMgr.getInstance().getAppTitle(),
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (selectedValue.equals(JOptionPane.YES_OPTION)) {
			PlaylistMgr.getInstance().getPlayListModel().remove(playJList.getSelectedIndex());
			playJList.setSelectedIndex(0);
			playJList.ensureIndexIsVisible(0);		
			LOGGER.info(deletePlaylist+" - Playlist deleted");
			return PlaylistMgr.getInstance().getPlayListModel();	
		}
		return null;
	}
	
    public Playlist getCurrentPlaylist() {
		return playJList.getSelectedValue();
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		userGestureMouseStartX = mouseEvent.getX();
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		userGestureMouseEndX = mouseEvent.getX(); 
		
		LOGGER.debug("Playlist Gesture user swiped :: " + new Integer(userGestureMouseStartX - userGestureMouseEndX));
		if (userGestureMouseStartX - userGestureMouseEndX < 0 && userGestureMouseStartX - userGestureMouseEndX != 0) {
			LoadPlaylistInPlayer();			
		} else if (userGestureMouseStartX - userGestureMouseEndX > 0 && userGestureMouseStartX - userGestureMouseEndX != 0) {
			PlaylistEditHelper.getInstance().deletePlaylist();
		}
	}

	private void LoadPlaylistInPlayer() {
		if (RootAppMgr.getInstance().getPlayerView().setPlaylist(getCurrentPlaylist())) {	
			setPlaylistNameFooter(playJList.getSelectedValue().getPlName());
			SPMenuBar.getInstance().showPlayer(false);
		}
	}
	
}

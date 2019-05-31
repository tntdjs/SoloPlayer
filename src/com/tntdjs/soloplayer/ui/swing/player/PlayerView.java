package com.tntdjs.soloplayer.ui.swing.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.SystemPropertyMgr;
import com.tntdjs.utils.i18n.TranslationMgr;
import com.tntdjs.soloplayer.RootAppMgr;
import com.tntdjs.soloplayer.data.config.objects.Playlist;
import com.tntdjs.soloplayer.data.config.objects.Song;
import com.tntdjs.soloplayer.ui.swing.clock.MediaPlayerSpinnerAnimation;
import com.tntdjs.soloplayer.ui.swing.menu.SPMenuBar;
import com.tntdjs.soloplayer.ui.swing.nowplayingfooter.NowPlayingFooterView;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import layout.TableLayout;

/**
 * Media Player main view
 * 
 * @author tsenausk
 */
public class PlayerView extends JPanel
		implements ActionListener, ListSelectionListener, BasicPlayerListener, ChangeListener, DocumentListener {
	private static Logger LOGGER = LogManager.getRootLogger();

	private static final long serialVersionUID = 327941017696121289L;

	private static final String UI_PREVIOUS = "UI_PREVIOUS";
	private static final String UI_REWIND = "UI_REWIND";
	private static final String UI_STOP = "UI_STOP";
	private static final String UI_PAUSE = "UI_PAUSE";
	private static final String UI_PLAY = "UI_PLAY";
	private static final String UI_FFORWARD = "UI_FFORWARD";
	private static final String UI_NEXT = "UI_NEXT";

	private NowPlayingFooterView FOOTER_VIEW = new NowPlayingFooterView();
	private MediaPlayerSpinnerAnimation MEDIA_PLAYER_SPINNER = new MediaPlayerSpinnerAnimation();
	private JSlider MEDIA_PLAYER_PROGRESS_SLIDER;
	private JSlider MEDIA_PLAYER_VOLUME_SLIDER;
		
	private JList<Song> songJList;

	private JScrollPane rightSplitListScrollPane;
	private DefaultListModel<Song> songListModel = new DefaultListModel<Song>();

	private JToggleButton autoPlayButton;

	private JLabel songtitleNameLabel = new JLabel(TranslationMgr.getInstance().getText("SongTitleNameLabel"));
	private JLabel songTitleValueLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel artistNameLabel = new JLabel(TranslationMgr.getInstance().getText("ArtistNameLabel"));
	private JLabel artistValueLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel bpmNameLabel = new JLabel(TranslationMgr.getInstance().getText("BPMNameLabel"));
	private JLabel bpmValueLabel = new JLabel();
	private JLabel keyNameLabel = new JLabel(TranslationMgr.getInstance().getText("KeyNameLabel"));
	private JLabel keyValueLabel = new JLabel();
	private JLabel volumeNameLabel = new JLabel(TranslationMgr.getInstance().getText("Volume"), SwingConstants.CENTER);
	private JLabel songTimeTrackerValueLabel = new JLabel("0:00", SwingConstants.CENTER);

	private JTextField searchField = new JTextField(20);

	private boolean autoPlay = false;
	private int selectedSong = 0;
	private LocalDateTime songStartTime;
	private String songDuration;
	private Double songTotalSeconds;

	// private SpectrumTimeAnalyzer analyzer = null;

	/**
	 * Constructor
	 */
	public PlayerView() {
		LOGGER.info("Create Player View");
		setDoubleBuffered(true);
		
		setLayout(new BorderLayout());
		add(getNorthView(), BorderLayout.NORTH);
		add(getCenterView(), BorderLayout.CENTER);
		add(getSouthView(), BorderLayout.SOUTH);

//		String gain = SystemPropertyMgr.getInstance().getString("audio.volume.level");
//		MEDIA_PLAYER_VOLUME_SLIDER.setValue(Integer.parseInt(gain));
		// RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).setVolume(Integer.parseInt(gain));
		
//		RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).setCurrentVolume(Integer.valueOf(SystemPropertyMgr.getInstance().getString("audio.volume.level")));
		MEDIA_PLAYER_VOLUME_SLIDER.setValue(Integer.valueOf(SystemPropertyMgr.getInstance().getString("audio.volume.level")));
		MEDIA_PLAYER_VOLUME_SLIDER.addChangeListener(this);
	}

	public boolean setPlaylist(Playlist playlist) {
		if (playlist.getAutoPlay().contentEquals("true")) {
			autoPlay = true;
			autoPlayButton.setSelected(true);
		} else {
			autoPlay = false;
			autoPlayButton.setSelected(false);
		}

		List<Song> songList = null;
		try {
			songList = playlist.getSetlist().getSongs();
			if (songList.size() > 0) {
				Collections.sort(songList, new Comparator<Song>() {
					@Override
					public int compare(final Song object1, final Song object2) {
						return object1.getSongIndex().compareTo(object2.getSongIndex());
					}
				});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					TranslationMgr.getInstance().getText("Playlist Error; " + e.getMessage()),
					TranslationMgr.getInstance().getText("Playlist Error") + " - "
							+ TranslationMgr.getInstance().getAppTitle(),
					JOptionPane.OK_OPTION);
			return false;
		}
		
		Iterator<Song> it = songList.iterator();
		songListModel.clear();
		while (it.hasNext()) {
			Song song = it.next();
			songListModel.addElement(song);
		}

		if (songJList.getVisibleRowCount() > 0) {
			songJList.setSelectedIndex(0);
			songJList.ensureIndexIsVisible(0);
		}
		
		FOOTER_VIEW.setPlaylistNameFooter(playlist.getPlName());
		return true;
	}

	private JComponent getCenterView() {
		JPanel leftSplitPane = new JPanel(new GridBagLayout());
		songJList = new JList<Song>(songListModel);
		songJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		songJList.setSelectedIndex(0);
		songJList.addListSelectionListener(this);
		songJList.setVisibleRowCount(3);
		songJList.setFixedCellHeight(58);
		// songJList.setCellRenderer(new SongListCellRenderer());

		double leftSplitPaneTableLayout[][] = { // column/row
				{ 0.03, 0.15, 0.10, 0.10, TableLayout.FILL, 0.10, 0.10, 0.10, 0.10, 0.03 },
				{ 0.05, 0.10, 0.10, 0.10, TableLayout.FILL, 0.10, 0.10, 0.10, 0.12, 0.03 } };
		leftSplitPane.setLayout(new TableLayout(leftSplitPaneTableLayout));

		// col/row
		leftSplitPane.add(songtitleNameLabel, "1,1");
		leftSplitPane.add(songTitleValueLabel, "2,1, 6,1");
		leftSplitPane.add(artistNameLabel, "1,2");
		leftSplitPane.add(artistValueLabel, "2,2, 6,2");
		leftSplitPane.add(bpmNameLabel, "7,1");
		leftSplitPane.add(bpmValueLabel, "8,1");
		leftSplitPane.add(keyNameLabel, "7,2");
		leftSplitPane.add(keyValueLabel, "8,2");
		leftSplitPane.add(songTimeTrackerValueLabel, "2,7, 4,7");
		leftSplitPane.add(MEDIA_PLAYER_SPINNER, "2,3, 5,8");

		// analyzer = new SpectrumTimeAnalyzer();
		// analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SCOPE);
		// analyzer.setSpectrumAnalyserBandCount(19);
		// analyzer.setSize(50,50);
		// analyzer.setSpectrumAnalyserDecay(0.05f);
		// int fps = SpectrumTimeAnalyzer.DEFAULT_FPS;
		// analyzer.setFps(fps);
		// analyzer.setPeakDelay((int) (fps *
		// SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY_FPS_RATIO));
		// analyzer.setVisible(true);
		// analyzer.setBorder(new LineBorder(Color.RED));
		// leftSplitPane.add(analyzer, "2,3, 5,8");

		Font f = songTimeTrackerValueLabel.getFont();
		songTimeTrackerValueLabel.setFont(new Font(f.getFontName(), Font.PLAIN, f.getSize() + 2));

		// JSlider settings for media progress
		final int PROGRESS_MIN = 0;
		final int PROGRESS_MAX = 10;
		final int PROGRESS_INIT = 0;

		MEDIA_PLAYER_VOLUME_SLIDER = new JSlider(JSlider.VERTICAL, PROGRESS_MIN, PROGRESS_MAX, PROGRESS_INIT);
		MEDIA_PLAYER_VOLUME_SLIDER.setMaximum(100);
		MEDIA_PLAYER_VOLUME_SLIDER.setMinimum(0);
		MEDIA_PLAYER_VOLUME_SLIDER.setMajorTickSpacing(25);
		MEDIA_PLAYER_VOLUME_SLIDER.setMinorTickSpacing(5);
		MEDIA_PLAYER_VOLUME_SLIDER.setPaintTicks(true);
		MEDIA_PLAYER_VOLUME_SLIDER.setPaintLabels(false);
		MEDIA_PLAYER_VOLUME_SLIDER.setOpaque(false);
		leftSplitPane.add(MEDIA_PLAYER_VOLUME_SLIDER, "7,3, 8,7");
		leftSplitPane.add(volumeNameLabel, "7,8, 8,8");

		UIManager.put("ScrollBar.width", Integer.parseInt(SystemPropertyMgr.getInstance().getString("app.scrollBar.width")));
		rightSplitListScrollPane = new JScrollPane(songJList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Player BG Panel
		JPanel leftBG = new JPanel();
		leftBG.setBackground(Color.WHITE);
		leftBG.setBorder(new LineBorder(Color.BLACK));
		leftSplitPane.add(leftBG, "0,0,9,9");

		double rootTwoPanesTableLayout[][] = { { .01, .49, .01, .48, .01 }, { 0.01, TableLayout.FILL, 0.01 } }; // column/row
		JPanel rootTwoPanes = new JPanel(new TableLayout(rootTwoPanesTableLayout));
		rootTwoPanes.add(leftSplitPane, "1,1"); // r/c
		rootTwoPanes.add(rightSplitListScrollPane, "3,1"); // r/c

		return rootTwoPanes;
	}

	private JPanel getNorthView() {
		JPanel NORTH_VIEW = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton backButton;
		try {
			backButton = new JButton(new ImageIcon(ImageIO.read(new File("res/images/close.png"))));
			backButton.setText(TranslationMgr.getInstance().getText("BackButton"));
			backButton.setPreferredSize(new Dimension(120, 28)); //67, 29
			backButton.setMaximumSize(new Dimension(120, 28));
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SPMenuBar.getInstance().hidePlayer();
				}
			});
			buttonPanel.add(backButton);
		} catch (IOException e1) {
			LOGGER.debug(e1);
		}

//		autoPlayButton = new JToggleButton(TranslationMgr.getInstance().getText("AutoPlay"));
		try {
			autoPlayButton = new JToggleButton(new ImageIcon(ImageIO.read(new File("res/images/autoplay.png"))));
			autoPlayButton.setText(TranslationMgr.getInstance().getText("AutoPlay"));
			autoPlayButton.setPreferredSize(new Dimension(120, 28)); //67, 29
			autoPlayButton.setMaximumSize(new Dimension(120, 28));
		} catch (IOException e1) {
			LOGGER.debug(e1);
		}
		
		ItemListener autoPlayItemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					autoPlay = true;
				} else {
					autoPlay = false;
				}
			}
		};
		autoPlayButton.addItemListener(autoPlayItemListener);
		buttonPanel.add(autoPlayButton);
		NORTH_VIEW.add(buttonPanel, BorderLayout.WEST);

		JPanel searchPanel = new JPanel();
		JLabel searchLabel = new JLabel(TranslationMgr.getInstance().getText("PlayerSearchLabel"));
		searchField.getDocument().addDocumentListener(this);

//		JButton searchButton = new JButton(TranslationMgr.getInstance().getText("PlayerSearchFindNextButton"));
//		searchButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				searchPlaylist(true);
//			}
//		});
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
//		searchPanel.add(searchButton);

		NORTH_VIEW.add(searchPanel, BorderLayout.EAST);

		return NORTH_VIEW;
	}

	private void searchPlaylist(boolean next) {
		String s = searchField.getText();
		if (s.length() <= 0) {
			return;
		}

		ListModel<Song> songs = getSONG_JLIST().getModel();
		int start = 0;
		if (next) {
			start = getSONG_JLIST().getSelectedIndex();
		}
		for (int i = start; i < songs.getSize(); i++) {
			Song song = songs.getElementAt(i);
			if (song.getSongName().toLowerCase().contains(s.toLowerCase())) {
				getSONG_JLIST().setSelectedIndex(i);
				getSONG_JLIST().ensureIndexIsVisible(i);
			}
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		searchPlaylist(false);
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		searchPlaylist(false);
	}

	private JPanel getSouthView() {
		double SouthPanelTableLayout[][] = { { .05, TableLayout.FILL, .05 }, { .03, 45, 80, 26, .02 } }; // column/row
		JPanel SOUTH_VIEW = new JPanel(new TableLayout(SouthPanelTableLayout));

		MEDIA_PLAYER_PROGRESS_SLIDER = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		MEDIA_PLAYER_PROGRESS_SLIDER.setMajorTickSpacing(25);
		MEDIA_PLAYER_PROGRESS_SLIDER.setMinorTickSpacing(5);
		MEDIA_PLAYER_PROGRESS_SLIDER.setPaintTicks(false);
		MEDIA_PLAYER_PROGRESS_SLIDER.setPaintLabels(false);
		SOUTH_VIEW.add(MEDIA_PLAYER_PROGRESS_SLIDER, "1,1"); // c/r

		// media player control buttons
		double mediaPlayerControlsTableLayout[][] = { // column/row
				{ TableLayout.FILL, 80, 80, 80, 80, 80, 80, 80, TableLayout.FILL }, { 0.01, 80, 0.01 } };
		JPanel mediaControlPanel = new JPanel(new TableLayout(mediaPlayerControlsTableLayout));

		try {
			JButton mpPrevious = new JButton(new ImageIcon(ImageIO.read(new File("res/images/mp/mp-previous.png"))));
			mpPrevious.addActionListener(this);
			mpPrevious.setActionCommand(UI_PREVIOUS);
			mpPrevious.setPreferredSize(new Dimension(73, 73));
			mpPrevious.setMaximumSize(new Dimension(73, 73));
			mpPrevious.setFocusable(false);
			mediaControlPanel.add(mpPrevious, "1,1");

			JButton mpRewind = new JButton(new ImageIcon(ImageIO.read(new File("res/images/mp/mp-rewind.png"))));
			mpRewind.addActionListener(this);
			mpRewind.setActionCommand(UI_REWIND);
			mpRewind.setPreferredSize(new Dimension(73, 73));
			mpRewind.setFocusable(false);
			mediaControlPanel.add(mpRewind, "2,1");

			JButton mpStop = new JButton(new ImageIcon(ImageIO.read(new File("res/images/mp/mp-stop.png"))));
			mpStop.setActionCommand(UI_STOP);
			mpStop.addActionListener(this);
			mpStop.setPreferredSize(new Dimension(73, 73));
			mpStop.setFocusable(false);
			mediaControlPanel.add(mpStop, "3,1");

			JButton mpPause = new JButton(new ImageIcon(ImageIO.read(new File("res/images/mp/mp-pause.png"))));
			mpPause.setActionCommand(UI_PAUSE);
			mpPause.addActionListener(this);
			mpPause.setPreferredSize(new Dimension(73, 73));
			mpPause.setFocusable(false);
			mediaControlPanel.add(mpPause, "4,1");

			JButton mpPlay = new JButton(new ImageIcon(ImageIO.read(new File("res/images/mp/mp-play.png"))));
			mpPlay.setActionCommand(UI_PLAY);
			mpPlay.addActionListener(this);
			mpPlay.setPreferredSize(new Dimension(73, 73));
			mpPlay.setFocusable(false);
			mediaControlPanel.add(mpPlay, "5,1");

			JButton mpForward = new JButton(new ImageIcon(ImageIO.read(new File("res/images/mp/mp-forward.png"))));
			mpForward.setActionCommand(UI_FFORWARD);
			mpForward.addActionListener(this);
			mpForward.setPreferredSize(new Dimension(73, 73));
			mpForward.setFocusable(false);
			mediaControlPanel.add(mpForward, "6,1");

			JButton mpNext = new JButton(new ImageIcon(ImageIO.read(new File("res/images/mp/mp-next.png"))));
			mpNext.setActionCommand(UI_NEXT);
			mpNext.addActionListener(this);
			mpNext.setPreferredSize(new Dimension(73, 73));
			mpNext.setFocusable(false);
			mediaControlPanel.add(mpNext, "7,1");

		} catch (IOException e) {
			LOGGER.error("Error creating player controls; play, pause etc... likely an image resource", e);
		}
		SOUTH_VIEW.add(mediaControlPanel, "1,2");
 		SOUTH_VIEW.add(FOOTER_VIEW.getFooterPanel(), "0,3, 2,3");
		
		return SOUTH_VIEW;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		switch (actionEvent.getActionCommand()) {
		case UI_PREVIOUS:
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).previous();
			break;
		case UI_REWIND:
			break;
		case UI_STOP:
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).stop();
			break;
		case UI_PAUSE:
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).pause();
			break;
		case UI_PLAY:
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).play();
			break;
		case UI_FFORWARD:
			break;
		case UI_NEXT:
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).next();
			break;
		default:
			throw new IllegalArgumentException(
					"Invalid Media Player action: " + actionEvent + " - " + actionEvent.getActionCommand());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent listSelEvnt) {
		if (listSelEvnt.getValueIsAdjusting() == false) {
			setSelectedSong(songJList.getSelectedIndex());
		}
	}

	@Override
	public void opened(Object arg0, @SuppressWarnings("rawtypes") Map arg1) {
		// System.err.println("OPENED:: " + arg0 + " - " + arg1);

	}

	@Override
	public void progress(int arg0, long arg1, byte[] pcmdata, @SuppressWarnings("rawtypes") Map arg3) {
		LocalDateTime currentTime = LocalDateTime.now();
		
		int mins = 0;
		int secs = 0;
		String minutes = "00";
		String seconds = "00";
		
		int currentMinute = currentTime.getMinute();
		int startMinute = songStartTime.getMinute();
		
		if (currentMinute>=startMinute) {
			mins = currentTime.getMinute() - songStartTime.getMinute();
		} else {
			mins = (60 + currentTime.getMinute()) - songStartTime.getMinute();
		}
		
		if (mins < 1) {
			secs = currentTime.getSecond() - songStartTime.getSecond();
		} else {
			secs = currentTime.getSecond();
		}

		if (mins < 10) {
			minutes = "0" + mins;
		} else {
			minutes = mins + "";
		}

		if (secs < 10) {
			seconds = "0" + secs;
		} else {
			seconds = secs + "";
		}
		songTimeTrackerValueLabel.setText(minutes + ":" + seconds+ " / " +songDuration);
				
		int slidertot = songTotalSeconds.intValue()*60;
		int slidercur = ((mins*60)+secs);
		MEDIA_PLAYER_PROGRESS_SLIDER.setMaximum(slidertot);
		MEDIA_PLAYER_PROGRESS_SLIDER.setValue(slidercur);
		
//		System.err.println(slidertot + " - " + slidercur);
		
//		Duration durationTracker = Duration.between(currentTime, Time.valueOf(songDuration).toInstant());
//		long mill = durationTracker.toMillis();
//		MEDIA_PLAYER_PROGRESS_SLIDER.setValue(1);
					
		// if (audioInfo.containsKey("basicplayer.sourcedataline")) {
		// Spectrum/time analyzer
		// if (analyzer != null) {
		// analyzer.writeDSP(pcmdata);
		// }
		// }
	}

	@Override
	public void stateUpdated(BasicPlayerEvent newStatus) {
		// if (newStatus.getCode() == BasicPlayerEvent.PLAYING) {
		// System.err.println("Status Updated:: " + newStatus.getCode() + " - "
		// + newStatus.getDescription() + " - " + newStatus.getPosition());
		// }

		if (newStatus.getCode() == BasicPlayerEvent.EOM && autoPlay) { // keep playing
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).stop();
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).next();
		
		} else if (newStatus.getCode() == BasicPlayerEvent.EOM ) { // keep playing
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).stop();
		}
	}

	/**
	 * InnerClass List Cell Renderer
	 * 
	 * @author tsenausk
	 *
	 */
	@SuppressWarnings("unused")
	private static class SongListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = -2084668903226944208L;

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			// if ( index % 2 == 0 ) {
			// c.setBackground( Color.LIGHT_GRAY );
			// }
			// else {
			// c.setBackground( Color.white );
			// }
			return c;
			// return new JLabel(((Song)value).getSongName());
		}
	}

	@Override
	public void setController(BasicController arg0) {
	}

	public int getSelectedSong() {
		return selectedSong;
	}

	public void setSelectedSong(int selectedSong) {
		this.selectedSong = selectedSong;
	}

	public JList<Song> getSONG_JLIST() {
		return songJList;
	}

	public LocalDateTime getSongStartTime() {
		return songStartTime;
	}

	public void setSongStartTime(LocalDateTime songStartTime) {
		this.songStartTime = songStartTime;
	}

	public void setSongTitleValue(String song) {
		songTitleValueLabel.setText(song);
	}

	public void setArtistValue(String artist) {
		artistValueLabel.setText(artist);
	}

	public void setBPMValue(String bpm) {
		bpmValueLabel.setText(bpm);
	}

	public void setKeyValue(String key) {
		keyValueLabel.setText(key);
	}

	public void previous() {
		if (RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getSelectedIndex() > 0) {
			RootAppMgr.getInstance().getPlayerView().getSONG_JLIST()
					.setSelectedIndex(RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getSelectedIndex() - 1);
		} else if (RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getSelectedIndex() == 0) {
			RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().setSelectedIndex(
					RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getModel().getSize() - 1);
		}
	}

	public boolean next() {
		int newSongIndex = 0;
		if (RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getSelectedIndex() 
				<= RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getModel().getSize()) {
			newSongIndex = RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getSelectedIndex() + 1;

			LOGGER.info("autoplay songindex=" + newSongIndex);
			RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().setSelectedIndex(newSongIndex);
			RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().ensureIndexIsVisible(newSongIndex);
			return true;
		}
		return false;
	}

	public void updateSongTagsUI(Song song, int currentMediaSpinnerSeconds) {
		RootAppMgr.getInstance().getPlayListview().setNowPlayingSong(song.getId3FileInfo().getFileTagAttribList().get("TIT2"));
		FOOTER_VIEW.setNowPlayingSong(song.getId3FileInfo().getFileTagAttribList().get("TIT2"));
		setSongTitleValue(song.getId3FileInfo().getFileTagAttribList().get("TIT2"));
		setArtistValue(song.getId3FileInfo().getFileTagAttribList().get("TPE1"));
		setBPMValue(song.getId3FileInfo().getFileTagAttribList().get("TBPM"));
		setKeyValue("");
		setSongStartTime(LocalDateTime.now());
		
		Double minutes = new Double(0);
		Double wholeMinutes = new Double(0);
		Double seconds = new Double(0);		
		songDuration = song.getId3FileInfo().getFileTagAttribList().get("Track Length");
		if (null != songDuration) {
			songTotalSeconds = new Double(songDuration);
			minutes = songTotalSeconds/60;
			wholeMinutes = minutes - minutes.intValue();
			seconds = 60 * wholeMinutes;
		} 
		String formattedSeconds = "";
		
		//if seconds are less then 10 then we need to pad a zero prefix
		if (seconds.intValue() < 10) {
			formattedSeconds = "0"+seconds.intValue();
		} else {
			formattedSeconds = seconds.intValue()+"";
		}
		songDuration = minutes.intValue() + ":" + formattedSeconds;
		this.startMediaPlayerSpinner(currentMediaSpinnerSeconds);
	}

	public void startMediaPlayerSpinner(int startSecond) {
		MEDIA_PLAYER_SPINNER.start(startSecond);
	}

	public int stopMediaPlayerSpinner() {
		return MEDIA_PLAYER_SPINNER.stop();
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		JSlider source = (JSlider) event.getSource();
		if (!source.getValueIsAdjusting()) {
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).setCurrentVolume((int) source.getValue());
			RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).setVolume();
		}
	}

	public void setVolume(int value) {
		MEDIA_PLAYER_VOLUME_SLIDER.setValue(value);
	}

	public void toggleAutoPlay() {
		autoPlayButton.doClick();
	}

}

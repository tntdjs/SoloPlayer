package com.tntdjs.soloplayer.ui.swing.playlist.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.i18n.TranslationMgr;
import com.tntdjs.soloplayer.SoloPlayerSwingAppMain;
import com.tntdjs.soloplayer.data.PlaylistMgr;
import com.tntdjs.soloplayer.data.config.objects.Playlist;
import com.tntdjs.soloplayer.data.config.objects.Setlist;
import com.tntdjs.soloplayer.data.config.objects.Song;
import com.tntdjs.soloplayer.playlist.editor.PlaylistEditHelper;
import com.tntdjs.soloplayer.ui.swing.menu.SPMenuBar;

import layout.TableLayout;

/**
 * PlaylistEditor
 * @author tsenausk
 *
 */
public class PlaylistEditor extends JPanel implements ActionListener, DocumentListener, ItemListener {
	private static final long serialVersionUID = -8048040295368535657L;
	private static Logger LOGGER = LogManager.getRootLogger();

	private Playlist PLAYLIST;
	
	private JPanel TOOLBAR_PANEL = new JPanel();
	private JPanel BODY_PANEL = new JPanel();
	private JPanel PLAYLIST_FILES_PANEL = new JPanel();
	private JPanel FOOTER_PANEL = new JPanel();

	private JLabel plIDValue = new JLabel();
	private JTextField plName = new JTextField(20);
	private JTextField plIndex = new JTextField(3);
	private JCheckBox plAutoPlay = new JCheckBox();
	private JLabel plPathLabel = new JLabel(TranslationMgr.getInstance().getText("PlaylistPathLabel"));
	private JTextField plPath = new JTextField();
	private JButton setFolderButton;

	private JButton saveButton;
	private JRadioButton folderRB;
	private JRadioButton fileRB;
	
	private TitledBorder plSonglistTitledBorder = new TitledBorder(TranslationMgr.getInstance().getText("PlaylistSongList"));
	private JPanel songListButtonPanel;
	private JButton addSongButton;
	private JButton removeSongButton;
	private JList<Object> playlistFiles;
	private DefaultListModel<Object> songListModel = new DefaultListModel<>();
	
	private boolean isDataNew = false;
	private boolean isDataDirty = false;
	
	/**
	 * Constructor
	 */
	public PlaylistEditor() {
		double rootTableLayout[][] = { // column/row
		{ 0.01, TableLayout.FILL, 2, .50, 0.01 },
		{ .10, TableLayout.FILL, .05, 0.03 } };
		setLayout(new TableLayout(rootTableLayout));
						
		add(getToolbarPanel(), "1,0, 4,0");
		add(getBodyPanel(), "1,1");
		add(getPlaylistFilesPanel(), "3,1");
		add(getFooterPanel(), "1,2, 4,2");
		
		plName.getDocument().addDocumentListener(this);
		plIndex.getDocument().addDocumentListener(this);
		plPath.getDocument().addDocumentListener(this);
//		plAutoPlay.addChangeListener(this);
		plAutoPlay.addItemListener(this);
		saveButton.setEnabled(false);
		
		disableSonglistEditor();
	}
	
	/**
	 * updateData
	 * refresh the screen with current data
	 */
	public void updateData() {
		songListModel.removeAllElements();
		plPath.setText("");
		
		if (isDataNew) {
			plIDValue.setText(System.currentTimeMillis()+"");
		} else {
			plIDValue.setText(PLAYLIST.getPlId()+"");
		}
		
		plName.setText(PLAYLIST.getPlName());
		Byte b = PLAYLIST.getPlIndex();
		if (null == b) {
			b = 1;
		}
		plIndex.setText(b.toString());
		if (Boolean.parseBoolean(PLAYLIST.getAutoPlay())) {
			plAutoPlay.setSelected(true);	
		} else {
			plAutoPlay.setSelected(false);
		}
		if (!isDataNew) {
			if (null == PLAYLIST.getLocation() || PLAYLIST.getLocation().isEmpty()) {
				enableSonglistEditor();
				fileRB.setSelected(true);			
				try {				 
					 List<Song> songs = PLAYLIST.getSetlist().getSongs(); 
					 for(int i=0;i<songs.size();i++) {
						 songListModel.addElement(songs.get(i));
					 }
							 
					playlistFiles.setModel(songListModel);
				} catch (Exception e) {
					LOGGER.error("Error setting playlist editor, song list", e);
				}
				
			} else {
				disableSonglistEditor();
				fileRB.setSelected(false);
				plPath.setText(PLAYLIST.getLocation());
			}
		} else {
			disableSonglistEditor();
			fileRB.setSelected(false);			
		}
		
		saveButton.setEnabled(false);
	}
	
	/**
	 * saveData
	 * save field data to xml file
	 */
	public void saveData() {
		PLAYLIST.setPlId(Long.valueOf(plIDValue.getText()));
		PLAYLIST.setPlName(plName.getText());
		PLAYLIST.setAutoPlay(plAutoPlay.isSelected()+"");
		PLAYLIST.setFilemask("*.*");
		PLAYLIST.setLocation(plPath.getText());
		PLAYLIST.setPlIndex(Byte.valueOf(plIndex.getText()));
		
		if (fileRB.isSelected()) {
			Setlist sl = new Setlist();
			PLAYLIST.setSetlist(sl);
			sl.setAutoPlay("false");
			sl.setSlIndex((byte)1);
			sl.setSlName("Set1");
			List<Song> newSonglist = new ArrayList<Song>();
			for (int i=0;i<songListModel.size();i++) {
				Song song = (Song)songListModel.getElementAt(i);
				newSonglist.add(song);
			}
			sl.setSongs(newSonglist);
		}
		PLAYLIST.setUpdateDatetime(LocalDateTime.now().toString());
		
		if (isDataNew) {
			isDataNew = false;
			PlaylistMgr.getInstance().addPlaylist(PLAYLIST);
		}
		
		PlaylistMgr.getInstance().savePlaylist();
		isDataDirty = false;
		saveButton.setEnabled(false);
    	JOptionPane.showMessageDialog(null,    			     			 
    			TranslationMgr.getInstance().getText("PlaylistSaved") + " (" + plName.getText() + ")",
    			TranslationMgr.getInstance().getText("PlaylistEditorTitle"),
    			JOptionPane.OK_OPTION);
	}
		
	/**
	 * getToolbarPanel
	 * @return JPanel
	 */
	private JPanel getToolbarPanel() {
		TOOLBAR_PANEL.setLayout(new FlowLayout());
		TOOLBAR_PANEL.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton backButton;
		try {
			backButton = new JButton(new ImageIcon(ImageIO.read(new File("res/images/close.png"))));
			backButton.setText(TranslationMgr.getInstance().getText("BackButton"));
			backButton.setPreferredSize(new Dimension(120, 28)); //67, 29
			backButton.setMaximumSize(new Dimension(120, 28));
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (isDataDirty()) {
						Object selectedValue = JOptionPane.showConfirmDialog(SoloPlayerSwingAppMain.getInstance().getFrame(), 
						TranslationMgr.getInstance().getText("ExitPlaylistEditorDataDirtyPrompt"), 
						TranslationMgr.getInstance().getAppTitle(),
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);							
						if (!selectedValue.equals(JOptionPane.YES_OPTION)) {
							return;
						}						
					} 					
					SPMenuBar.getInstance().hidePlayer();
				}
			});
			TOOLBAR_PANEL.add(backButton);
		} catch (IOException e1) {
			LOGGER.debug(e1);
		}
		
		try {
			saveButton = new JButton(new ImageIcon(ImageIO.read(new File("res/images/save.png"))));
			saveButton.setPreferredSize(new Dimension(140, 28));
			saveButton.setMaximumSize(new Dimension(140, 28));
			saveButton.setText(TranslationMgr.getInstance().getText("SaveButton"));
			saveButton.setActionCommand("plSAVE");
			saveButton.addActionListener(this);
			isDataDirty = false;
			TOOLBAR_PANEL.add(saveButton);
		} catch (IOException e1) {
			LOGGER.debug(e1);
		}
		
		return TOOLBAR_PANEL;
	}
	
	/**
	 * getFooterPanel
	 * @return JPanel
	 */
	private JPanel getFooterPanel() {
		FOOTER_PANEL.setLayout(new FlowLayout());
		FOOTER_PANEL.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel id = new JLabel(TranslationMgr.getInstance().getText("PlaylistIDLabel") + " ");
		FOOTER_PANEL.add(id);
		FOOTER_PANEL.add(plIDValue);
		return FOOTER_PANEL;
	}
	
	/**
	 * getBodyPanel
	 * @return JPanel
	 */
	private JPanel getBodyPanel() {
		double westTableLayout[][] = { // column/row
		{ 0.02, 0.45, 0.45, .06, 0.02 },
		{ 0.02, 2, 2, 0.10, 2, 0.10, 2, 0.10, 2, 0.10, 2, 0.10, 2, 0.10, 2, .10, 2, .10,  2, .10, 0.03 } };
		BODY_PANEL.setLayout(new TableLayout(westTableLayout));
		BODY_PANEL.setBorder(new TitledBorder(TranslationMgr.getInstance().getText("PlaylistEditorTitle")));
				
//		BODY_PANEL.add(new JLabel(TranslationMgr.getInstance().getText("PlaylistIDLabel")), "1,1"); // c/r
//		BODY_PANEL.add(plIDValue, "2,1"); // c/r
		
		BODY_PANEL.add(new JLabel(TranslationMgr.getInstance().getText("PlaylistNameLabel")), "1,3"); // c/r
		BODY_PANEL.add(plName, "2,3"); // c/r
				
		BODY_PANEL.add(new JLabel(TranslationMgr.getInstance().getText("PlaylistIndexLabel")), "1,5"); // c/r
		BODY_PANEL.add(plIndex, "2,5"); // c/r
				
		BODY_PANEL.add(new JLabel(TranslationMgr.getInstance().getText("PlaylistAutoPlayLabel")), "1,7"); // c/r
		BODY_PANEL.add(plAutoPlay, "2,7"); // c/r
		
		BODY_PANEL.add(new JLabel(TranslationMgr.getInstance().getText("PlaylistFolderFileLabel")), "1,9"); // c/r
		
			folderRB = new JRadioButton(TranslationMgr.getInstance().getText("PlaylistRBFolderLabel"));
			folderRB.setActionCommand("FOLDER");
			folderRB.setSelected(true);
			folderRB.addActionListener(this);
			
			fileRB = new JRadioButton(TranslationMgr.getInstance().getText("PlaylistRBFileLabel"));
			fileRB.setActionCommand("FILE");
			fileRB.setSelected(false);	
			fileRB.addActionListener(this);
			
			ButtonGroup group = new ButtonGroup();
			group.add(folderRB);
			group.add(fileRB);
			
			JPanel radioButtonPanel = new JPanel(new FlowLayout());
			radioButtonPanel.add(folderRB);
			radioButtonPanel.add(fileRB);
			
		BODY_PANEL.add(radioButtonPanel, "2,9"); // c/r				
		BODY_PANEL.add(plPathLabel, "1,11"); // c/r
		BODY_PANEL.add(plPath, "1,13, 2,13"); // c/r
				
		setFolderButton = new JButton("TEST");
		setFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newFolder = PlaylistEditHelper.getInstance().getPlaylistFolder(SoloPlayerSwingAppMain.getInstance().getFrame());
				if (!newFolder.equalsIgnoreCase("")) {
					plPath.setText(newFolder);
				}
			}
		});		
		BODY_PANEL.add(setFolderButton, "3,13"); // c/r
				
		return BODY_PANEL;
	}
	
	private void enableSonglistEditor() {
		addSongButton.setEnabled(true);
		removeSongButton.setEnabled(true);
		plSonglistTitledBorder.setTitleColor(Color.BLACK);
		playlistFiles.setEnabled(true);
		plPathLabel.setEnabled(false);
		plPath.setEnabled(false);
		setFolderButton.setEnabled(false);
		repaint();
	}
	private void disableSonglistEditor() {
		addSongButton.setEnabled(false);
		removeSongButton.setEnabled(false);
		plSonglistTitledBorder.setTitleColor(Color.GRAY);
		playlistFiles.setEnabled(false);
		plPathLabel.setEnabled(true);
		plPath.setEnabled(true);
		setFolderButton.setEnabled(true);
		repaint();
	}
	
	/**
	 * getPlaylistFilesPanel
	 * @return JPanel
	 */
	private JPanel getPlaylistFilesPanel() {
		double playlistTableLayout[][] = { // column/row
		{ 0.02, TableLayout.FILL, 0.02 },
		{ 0.02, 0.12, .01, TableLayout.FILL, 0.03 } };
		PLAYLIST_FILES_PANEL.setLayout(new TableLayout(playlistTableLayout));		
		PLAYLIST_FILES_PANEL.setBorder(plSonglistTitledBorder);
		
		playlistFiles = new JList<Object>(new DefaultListModel<Object>());		
		PLAYLIST_FILES_PANEL.add(playlistFiles, "1,3");
		
		songListButtonPanel = new JPanel();
		try {
			addSongButton = new JButton(new ImageIcon(ImageIO.read(new File("res/images/add.png"))));
		} catch (IOException e1) {
			LOGGER.debug(e1);
		}
		addSongButton.setPreferredSize(new Dimension(140, 28));
		addSongButton.setMaximumSize(new Dimension(140, 28));
		addSongButton.setText(TranslationMgr.getInstance().getText("AddSongButton"));
		addSongButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newFile = PlaylistEditHelper.getInstance().getPlaylistFile(SoloPlayerSwingAppMain.getInstance().getFrame());
				if (!newFile.equalsIgnoreCase("")) {
					Song song = new Song();
					song.setLocation(newFile);
					song.setSongIndex(((DefaultListModel<Object>)playlistFiles.getModel()).size()+1);
					song.setSongName(newFile);
					((DefaultListModel<Object>)playlistFiles.getModel()).addElement(song);
					isDataDirty = true;
					saveButton.setEnabled(true);
					LOGGER.debug(song.getSongName()+" - Song added");
				}
			}
		});
		songListButtonPanel.add(addSongButton);
				
		try {
			removeSongButton = new JButton(new ImageIcon(ImageIO.read(new File("res/images/minus.png"))));
		} catch (IOException e1) {
			LOGGER.debug(e1);
		}
		removeSongButton.setPreferredSize(new Dimension(140, 28));
		removeSongButton.setMaximumSize(new Dimension(140, 28));
		removeSongButton.setText(TranslationMgr.getInstance().getText("RemoveSongButton"));
		removeSongButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Song deletesong = (Song)playlistFiles.getSelectedValue();
				Object selectedValue = JOptionPane.showConfirmDialog(SoloPlayerSwingAppMain.getInstance().getFrame(), 
					TranslationMgr.getInstance().getText("DeleteSongAreYouSure") + "\n" + deletesong.getSongName(), 
					TranslationMgr.getInstance().getAppTitle(),
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if (selectedValue.equals(JOptionPane.YES_OPTION)) {
					((DefaultListModel<Object>)playlistFiles.getModel()).remove(playlistFiles.getSelectedIndex());
					playlistFiles.setSelectedIndex(0);
					playlistFiles.ensureIndexIsVisible(0);	
					isDataDirty = true;
					saveButton.setEnabled(true);
					LOGGER.debug(deletesong.getSongName()+" - Song deleted");
				}				
			}
		});
		songListButtonPanel.add(removeSongButton);
		PLAYLIST_FILES_PANEL.add(songListButtonPanel, "1,1");
		
		return PLAYLIST_FILES_PANEL;
	}

	@Override
	public void actionPerformed(ActionEvent command) {
		if (command.getActionCommand().equalsIgnoreCase("FOLDER")) {
			disableSonglistEditor();
		} else if (command.getActionCommand().equalsIgnoreCase("FILE")) {
			enableSonglistEditor();
		} else if (command.getActionCommand().equalsIgnoreCase("plSAVE")) {
			saveData();
		}
	}

	public void setcurrentPlaylist(Playlist playlist, boolean isNew) {
		isDataNew = isNew;
		PLAYLIST = playlist;
		updateData();
	}

	@Override
	public void changedUpdate(DocumentEvent documentEvent) {
		isDataDirty = true;
		saveButton.setEnabled(true);
	}

	@Override
	public void insertUpdate(DocumentEvent insertUpdate) {
		isDataDirty = true;
		saveButton.setEnabled(true);
	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent) {
		isDataDirty = true;
		saveButton.setEnabled(true);
	}

//	@Override
//	public void stateChanged(ChangeEvent changeEvent) {
//		Object obj = changeEvent.getSource();
//		if (obj instanceof JCheckBox) {
//			boolean selected = ((JCheckBox)obj).isSelected();
//			
//			
//		}
//		isDataDirty = true;
//		saveButton.setEnabled(true);
//	}

	public boolean isDataDirty() {
		return isDataDirty;
	}

	public void setDataDirty(boolean isDataDirty) {
		this.isDataDirty = isDataDirty;
	}

	/**
	 * track changes to the auto play jcheckbox
	 */
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		isDataDirty = true;
		saveButton.setEnabled(true);		
	}

}

package com.tntdjs.soloplayer.playlist.editor;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.soloplayer.RootAppMgr;
import com.tntdjs.soloplayer.ui.swing.playlist.editor.AudioFilesFilter;
import com.tntdjs.soloplayer.ui.swing.playlist.editor.MP3FileFilter;

/**
 * PlaylistEditHelper
 * @author tsenausk
 *
 */
public class PlaylistEditHelper {
	private static Logger LOGGER = LogManager.getRootLogger();
	private static PlaylistEditHelper INSTANCE;
	private JFileChooser fc;
	
	public PlaylistEditHelper() {
		fc = new JFileChooser();
	}
	
	public static PlaylistEditHelper getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new PlaylistEditHelper();
		}
		return INSTANCE;
	}
	
	/**
	 * getPlaylistFile
	 * @param parent
	 * @return String the file selected or empty if cancelled
	 */
	public String getPlaylistFile(JFrame parent) {
		
		fc.addChoosableFileFilter(new MP3FileFilter());
		fc.addChoosableFileFilter(new AudioFilesFilter());
		int returnVal = fc.showDialog(parent, "Add");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// This is where a real application would open the file.
			LOGGER.info("Opening: " + file.getName() + ".");
			return file.getAbsolutePath();
		} else {
			LOGGER.info("Open command cancelled by user.");
			return "";
		}
//		log.setCaretPosition(log.getDocument().getLength());
	}

	/**
	 * getPlaylistFolder
	 * @param parent
	 * @return String of the folder selected or empty if cancelled
	 */
	public String getPlaylistFolder(JFrame parent) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose a playlist folder");
		
		// Uncomment one of the following lines to try a different
		// file selection mode. The first allows just directories
		// to be selected (and, at least in the Java look and feel,
		// shown). The second allows both files and directories
		// to be selected. If you leave these lines commented out,
		// then the default mode (FILES_ONLY) will be used.
		//
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// disable the "All files" option.
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			LOGGER.info("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			LOGGER.info("getSelectedFile() : " + chooser.getSelectedFile());
			return chooser.getSelectedFile().toString();
		} else {
			LOGGER.info("No Selection ");
			return "";
		}
	}
	
	public boolean deletePlaylist() {
		RootAppMgr.getInstance().getPlayListview().deleteCurrentPlaylist();
		return true;
	}
}

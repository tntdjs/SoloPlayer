package com.tms.soloplayer.ui.swing.playlist.editor;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.tntdjs.soloplayer.playlist.editor.PlaylistEditHelper;

public class TestPlaylistEditor extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;	
	private JButton openFileButton, openFolderButton;
	private JTextArea log;
	private static JFrame frame = new JFrame("FileChooserDemo");
	
	public TestPlaylistEditor() {
		super(new BorderLayout());

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Uncomment one of the following lines to try a different
		// file selection mode. The first allows just directories
		// to be selected (and, at least in the Java look and feel,
		// shown). The second allows both files and directories
		// to be selected. If you leave these lines commented out,
		// then the default mode (FILES_ONLY) will be used.
		//
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create the open button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		openFileButton = new JButton("Open a File...");
		openFileButton.addActionListener(this);

		// Create the save button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		openFolderButton = new JButton("Open a Folder...");
		openFolderButton.addActionListener(this);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(openFileButton);
		buttonPanel.add(openFolderButton);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		// Handle open file button action.
		if (e.getSource() == openFileButton) {
			System.out.println(PlaylistEditHelper.getInstance().getPlaylistFile(frame));

			// Handle open folder button action.
		} else if (e.getSource() == openFolderButton) {
			System.out.println(PlaylistEditHelper.getInstance().getPlaylistFolder(frame));

		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new TestPlaylistEditor());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}

}

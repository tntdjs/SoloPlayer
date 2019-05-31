package com.tntdjs.soloplayer.playlist.editor;
import java.io.File;
import javax.swing.ImageIcon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
 
/* Utils.java is used by FileChooserDemo2.java. */
public class Utils {
	private static Logger LOGGER = LogManager.getRootLogger();
	
    public final static String mp3 = "mp3";
    public final static String mp4 = "mp4";
    public final static String wav = "wav";
    public final static String ogg = "ogg";
    public final static String mpeg = "mpeg";
    public final static String mpeg2 = "mpeg2";
 
    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
 
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
        	LOGGER.error("Couldn't find file: " + path);
            return null;
        }
    }
}
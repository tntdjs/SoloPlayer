package com.tntdjs.soloplayer.data;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.FilePathUtils;
import com.tntdjs.utils.SystemPropertyMgr;
import com.tntdjs.soloplayer.data.config.objects.ListPlayLists;
import com.tntdjs.soloplayer.data.config.objects.Playlist;
import com.tntdjs.soloplayer.data.config.objects.Setlist;
import com.tntdjs.soloplayer.data.config.objects.Song;
import com.tntdjs.soloplayer.media.tag.id3tag.ID3FileInfo;

/**
 * PlaylistXMLHelper
 * Copyright (c) 2017, Todd M. Senauskas and/or its affiliates. All rights reserved.
 * @author tsenauskas
 *
 */
public class PlaylistXMLUnmarshaller {
	private static PlaylistXMLUnmarshaller INSTANCE;
	private static Logger LOGGER = LogManager.getRootLogger();
	private JAXBContext jaxbContext;
	private Unmarshaller jaxbUnmarshaller;
	private boolean dirty = false;
	private ListPlayLists PLAYLISTS;
		
	/**
	 * Provides a reference to the application.
	 * @return the AppMain instance
	 */
	public static PlaylistXMLUnmarshaller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlaylistXMLUnmarshaller();
		}
		return INSTANCE;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	/**
	 * Constructor
	 */
	public PlaylistXMLUnmarshaller() {
		super();
		File file = new File(getXMLFileName());

		try {
			jaxbContext = JAXBContext.newInstance(ListPlayLists.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			PLAYLISTS = (ListPlayLists) jaxbUnmarshaller.unmarshal(file);
			
		} catch (JAXBException e) {
			LOGGER.fatal("Error parsing XML file (" + getXMLFileName() + ")", e);
		}
	}
	
	/**
	 * Provide a list containing ALL playlist for general display/selection
	 * @return
	 */
	public List<Playlist> getPlayLists() {
		return PLAYLISTS.getPlaylist();
	}

	/**
	 * Provide the requested playlist for display/selection
	 * @param SongListID
	 * @return
	 */
	public Playlist getPlayList(String songListID) {		
		Iterator<Playlist> it = getPlayLists().iterator();
		while (it.hasNext()) {
			Playlist playList = it.next();
			if (playList.getPlId().toString().equalsIgnoreCase(songListID)) {
				return playList;
			}
		}
		
		return null;
	}
	
	public String getXMLFileName() {
		return SystemPropertyMgr.getInstance().getString("playlist.filename.current");
	}

	/**
	 * Generate a set list when we have a playlist that is only a folder definition with no songs in the setlist.
	 * @param list
	 */
	public Setlist getFolderSetList(Setlist setList, String location) throws Exception {
		File[] flist = new File(FilePathUtils.cleanFilePath(location)).listFiles();
		
		setList.setSlIndex(Integer.valueOf(0).byteValue());
		setList.setSlName("Set 1");
		
		if (null != flist && flist.length >0) {
			for (int i=0; i<flist.length; i++) {			
				Song song = new Song();
				song.setSongIndex(Integer.valueOf(i));
				song.setSongName(flist[i].getName());
				String absPath = flist[i].getAbsolutePath();
	
	//			int indexOfColon = absPath.indexOf(":");
	//			if (indexOfColon > 0) {
	//				absPath = absPath.substring(indexOfColon+1, absPath.length());
	//			}
				absPath = absPath.replace("\\", "/");
				song.setLocation(absPath);
				song.setSongIndex(i);
				song.setId3FileInfo(new ID3FileInfo(new File(song.getLocation())));
				setList.getSongs().add(song);
			}
		} else {
			LOGGER.error("No Songs for setlist - " + setList.getSlName());
		}
		return setList;		
	}

}

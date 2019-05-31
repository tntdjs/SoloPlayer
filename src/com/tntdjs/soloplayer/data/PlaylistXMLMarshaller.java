package com.tntdjs.soloplayer.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.SystemPropertyMgr;
import com.tntdjs.soloplayer.data.config.objects.ListPlayLists;
import com.tntdjs.soloplayer.data.config.objects.Playlist;

/**
 * PlaylistXMLMarshaller
 * 
 * @author tsenausk
 *
 */
public class PlaylistXMLMarshaller {
	private static Logger LOGGER = LogManager.getRootLogger();
	private static PlaylistXMLMarshaller INSTANCE;
	// JAXBContext is thread safe and can be created once
	private JAXBContext jaxbContext;

	public boolean marshall(List<Playlist> playlist) {
		try {
			// create context with ":" separated list of packages that
			// contain your JAXB ObjectFactory classes
			jaxbContext = JAXBContext.newInstance(ListPlayLists.class);
		} catch (Exception e) {
			LOGGER.error("Error getting JAXB Context", e);
		}
		
		try {
			// Marshallers are not thread-safe. Create a new one every time.
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "playList.xsd");
			
			ListPlayLists p = new ListPlayLists();
			p.setDtype("Default");
			p.getPlaylist().addAll(playlist);
	
			backupPlaylistXML();
		    			
			File savefile = new File(getXMLFileName());
			FileWriter fWriter = new FileWriter(savefile);
			LOGGER.debug("Save Playlist XML file to : " + savefile.getAbsolutePath());
			marshaller.marshal(p, fWriter);
		} catch (Exception e) {
			LOGGER.error("Illegal State JaxB Marshaller", e);
		}
		return true;
	}

	/**
	 * backupPlaylistXML
	 * Backup the playlist xml file before writing changes to it
	 * @throws IOException
	 */
	private void backupPlaylistXML() throws IOException {
		Path FROM = Paths.get(getXMLFileName());
		Path TO = Paths.get(getXMLFileNameBackup());
		//overwrite existing file, if exists
		CopyOption[] options = new CopyOption[]{
		  StandardCopyOption.REPLACE_EXISTING,
		  StandardCopyOption.COPY_ATTRIBUTES
		}; 
		Files.copy(FROM, TO, options);
	}

	public static PlaylistXMLMarshaller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlaylistXMLMarshaller();
		}
		return INSTANCE;
	}

	public String getXMLFileName() {
		return SystemPropertyMgr.getInstance().getString("playlist.filename.current");
	}
	public String getXMLFileNameBackup() {
		return SystemPropertyMgr.getInstance().getString("playlist.filename.backup");
	}
	
}

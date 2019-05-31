package com.tntdjs.soloplayer.data;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.soloplayer.data.config.objects.Playlist;

/**
 * PlaylistMgr
 * @author tsenausk
 *
 */
public class PlaylistMgr {
	private static Logger LOGGER = LogManager.getRootLogger();
	private static PlaylistMgr INSTANCE;
	
	private PlayListModel playListModel = new PlayListModel();
	private List<Playlist> playListData;
	
	public PlaylistMgr() {
		loadPlaylist();
	}
	
	public static PlaylistMgr getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new PlaylistMgr();
		}
		return INSTANCE;
	}

	public boolean addPlaylist(Playlist playlist) {
		playListModel.addElement(playlist);
		return playListData.add(playlist);
		
	}
	
	public PlayListModel getPlayListModel() {
		return playListModel;
	}

	public void setPlayListModel(PlayListModel playListModel) {
		this.playListModel = playListModel;
	}

	public List<Playlist> getPlayListData() {
		return playListData;
	}

	public void setPlayListData(List<Playlist> playListData) {
		this.playListData = playListData;
	}

	public void savePlaylist() {
		PlaylistXMLMarshaller.getInstance().marshall(playListData);
	}
	
	public void loadPlaylist() {
		playListData = PlaylistXMLUnmarshaller.getInstance().getPlayLists();
		
        LOGGER.info("Get Playlist :: PlaylistXMLHelper");
        Iterator<Playlist> it = getPlayListData().iterator();
        
        while (it.hasNext()) {
        	Playlist li = it.next();
        	getPlayListModel().addElement(li);        	
        }
	}
}
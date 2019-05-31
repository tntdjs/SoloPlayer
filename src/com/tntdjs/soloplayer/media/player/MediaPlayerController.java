package com.tntdjs.soloplayer.media.player;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.mediaplayer.AbstractMediaPlayerController;
import com.tntdjs.mediaplayer.MediaPlayerState;
import com.tntdjs.utils.SystemPropertyMgr;
import com.tntdjs.soloplayer.RootAppMgr;
import com.tntdjs.soloplayer.data.config.objects.Song;

import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * MediaPlayerController
 * 
 * Contains one of either 2 alternate players; BasicPlayer or AdvancedPlayer which is 
 * 		new AdvancedPlayer(new BufferedInputStream(fis));
 * 
 * @author tsenausk
 *
 */
public class MediaPlayerController extends AbstractMediaPlayerController {
	private static Logger LOGGER = LogManager.getLogger(MediaPlayerController.class.getName());
	private int currentMediaSpinnerSeconds = 0;
	
	public MediaPlayerController() {
		super();
		setCurrentVolume(Integer.valueOf(SystemPropertyMgr.getInstance().getString("audio.volume.level")));
	}

	@Override
	protected void openFile() {
		try {
//			CURRENT_SONG = filename;
//			FileInputStream fis = new FileInputStream(filename);
//			MEDIA_PLAYER.open(new BufferedInputStream(fis));
			MEDIA_CONTROLLER.open(new File(CURRENT_SONG)); //new BufferedInputStream(fis));

			List<?> mixers = MEDIA_PLAYER.getMixers();
			if (mixers != null) {
				Iterator<?> it = mixers.iterator();
				String mixer = SystemPropertyMgr.getInstance().getString("audio.device");
				boolean mixerFound = false;
				if ((mixer != null) && (mixer.length() > 0)) {
					// Check if mixer is valid.
					while (it.hasNext()) {
						if (((String) it.next()).equals(mixer)) {
							MEDIA_PLAYER.setMixerName(mixer);
							mixerFound = true;
							break;
						}
					}
				}
				if (mixerFound == false) {
					// Attempt to assign the first mixer found.
					it = mixers.iterator();
					if (it.hasNext()) {
						mixer = (String) it.next();
						MEDIA_PLAYER.setMixerName(mixer);
					}
				}
			}			
			MEDIA_PLAYER.addBasicPlayerListener(RootAppMgr.getInstance().getPlayerView());
			RootAppMgr.getInstance().getPlayerView().setController(MEDIA_PLAYER);
						
		} catch (Exception e) {
			LOGGER.error("Problem playing file - " + CURRENT_SONG, e);
		}

	}
		
	public void pause() {
		if (getMediaPlayerStatus() == MediaPlayerState.PAUSE) {
			RootAppMgr.getInstance().getPlayerView().startMediaPlayerSpinner(currentMediaSpinnerSeconds);
		}
		currentMediaSpinnerSeconds = RootAppMgr.getInstance().getPlayerView().stopMediaPlayerSpinner();

		if (MEDIA_PLAYER_STATUS == MediaPlayerState.PLAY) {
			try {
				MEDIA_PLAYER.pause();
			} catch (BasicPlayerException e) {
				LOGGER.error("Cannot pause", e);
			}
			MEDIA_PLAYER_STATUS = MediaPlayerState.PAUSE;
		} else if (MEDIA_PLAYER_STATUS == MediaPlayerState.PAUSE) {
			try {
				MEDIA_PLAYER.resume();
			} catch (BasicPlayerException e) {
				LOGGER.info("Cannot resume", e);
			}
			MEDIA_PLAYER_STATUS = MediaPlayerState.PLAY;
		}	
	}
	
	public void stop() {
		currentMediaSpinnerSeconds = RootAppMgr.getInstance().getPlayerView().stopMediaPlayerSpinner();
		currentMediaSpinnerSeconds = 0;

		if ((MEDIA_PLAYER_STATUS == MediaPlayerState.PAUSE) || (MEDIA_PLAYER_STATUS == MediaPlayerState.PLAY)) {
			try {
				if (null != MEDIA_PLAYER) {
					MEDIA_PLAYER.stop();					
				}
			} catch (BasicPlayerException e) {
				LOGGER.info("Cannot stop", e);
			}
			MEDIA_PLAYER_STATUS = MediaPlayerState.STOP;
		}
	}
	
	public void play() {
		RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().setSelectedIndex(RootAppMgr.getInstance().getPlayerView().getSelectedSong());
		Song song = (Song) RootAppMgr.getInstance().getPlayerView().getSONG_JLIST().getSelectedValue();
		RootAppMgr.getInstance().getPlayerView().updateSongTagsUI(song, currentMediaSpinnerSeconds);
		play(song.getLocation());
	}
	
	/**
	 * Play
	 * @param filename
	 */
	public void play(String filename) {		
		if (MEDIA_PLAYER_STATUS == MediaPlayerState.PAUSE) {
			try {
				MEDIA_PLAYER.resume();
			} catch (BasicPlayerException e) {
				LOGGER.error("Cannot resume", e);
			}
		}
		
		openFile(filename);
		try {
			MEDIA_PLAYER.play();
		} catch (BasicPlayerException e) {
			LOGGER.debug(e);
		}
		setVolume();
		MEDIA_PLAYER_STATUS = MediaPlayerState.PLAY;
	}

	public void previous() {
		if ((getMediaPlayerStatus() == MediaPlayerState.PLAY)) {
			stop();
		}
		RootAppMgr.getInstance().getPlayerView().previous();
		play();
	}

	public void next() {		
		stop();
		if (RootAppMgr.getInstance().getPlayerView().next()) {
			play();
		}
	}

	public void setVolume() {
		try {
			MEDIA_PLAYER.setGain(((double) CURRENT_VOLUME / (double) 100));
			RootAppMgr.getInstance().getPlayerView().setVolume(CURRENT_VOLUME);
			SystemPropertyMgr.getInstance().setString("audio.volume.level", CURRENT_VOLUME+"");
			SystemPropertyMgr.getInstance().saveProperties();
		} catch (BasicPlayerException e) {
			LOGGER.error(e);
		}
	}

}

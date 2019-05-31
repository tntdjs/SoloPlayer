package com.tntdjs.soloplayer.media.tag.mp3tag;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 * 
 * @author tsenausk
 *
 */
public class MP3FileInfo implements IMP3FileInfo {
	private static Logger LOGGER = LogManager.getLogger(MP3FileInfo.class.getName());
	
	private Map<?, ?> tAudioFileFormatProperties;
	private Map<?, ?> tAudioFormatProperties;
	
	public static void main(String[] args) {
		new MP3FileInfo(args[0]);
	}
	
	/**
	 * MP3FileInfo Constructor
	 * @param mp3File
	 */
	public MP3FileInfo(String mp3File) {
		File file = new File(mp3File);
		AudioFileFormat baseFileFormat = null;
		AudioFormat baseFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			LOGGER.error("Error reading MP3 file info!!!", e);
		}
		baseFormat = baseFileFormat.getFormat();
		// TAudioFileFormat properties
		if (baseFileFormat instanceof TAudioFileFormat) {
			settAudioFileFormatProperties(((TAudioFileFormat) baseFileFormat).properties());
//			String key = "author";
//			String val = (String) properties.get(key);
//			key = "mp3.id3tag.v2";
//			InputStream tag = (InputStream) properties.get(key);
		}
		// TAudioFormat properties
		if (baseFormat instanceof TAudioFormat) {
			settAudioFormatProperties(((TAudioFormat) baseFormat).properties());
//			String key = "bitrate";
//			Integer val = (Integer) properties.get(key);
		}
	}

	/* (non-Javadoc)
	 * @see com.tms.soloplayer.ui.swing.examples.IMP3FileInfo#gettAudioFileFormatProperties()
	 */
	@Override
	public Map<?, ?> gettAudioFileFormatProperties() {
		return tAudioFileFormatProperties;
	}

	public void settAudioFileFormatProperties(Map<?, ?> tAudioFileFormatProperties) {
		this.tAudioFileFormatProperties = tAudioFileFormatProperties;
	}

	/* (non-Javadoc)
	 * @see com.tms.soloplayer.ui.swing.examples.IMP3FileInfo#gettAudioFormatProperties()
	 */
	@Override
	public Map<?, ?> gettAudioFormatProperties() {
		return tAudioFormatProperties;
	}

	public void settAudioFormatProperties(Map<?, ?> tAudioFormatProperties) {
		this.tAudioFormatProperties = tAudioFormatProperties;
	}
	
//	Standard parameters :
		public static final String DURATION = "duration"; // : [Long], duration in microseconds. 
		public static final String TITLE = "title"; // : [String], Title of the stream. 
		public static final String AUTHOR = "author"; //  : [String], Name of the artist of the stream. 
		public static final String ALBUM = "album"; //  : [String], Name of the album of the stream. 
		public static final String DATE = "date"; //  : [String], The date (year) of the recording or release of the stream. 
		public static final String COPYRIGHT = "copyright"; //  : [String], Copyright message of the stream. 
		public static final String COMMENT = "comment"; //  : [String], Comment of the stream. 
//		 Extended MP3 parameters :
		public static final String mp3versionmpeg = "mp3.version.mpeg"; //  : [String], mpeg version : 1,2 or 2.5 
		public static final String mp3versionlayer = "mp3.version.layer"; //  : [String], layer version 1, 2 or 3 
		public static final String mp3versionencodin = "mp3.version.encodin"; // g : [String], mpeg encoding : MPEG1, MPEG2-LSF, MPEG2.5-LSF 
		public static final String mp3channels = "mp3.channels"; //  : [Integer], number of channels 1 : mono, 2 : stereo. 
		public static final String mp3frequencyhz = "mp3.frequency.hz"; //  : [Integer], sampling rate in hz. 
		public static final String mp3bitratenominalbps = "mp3.bitrate.nominal.bps"; //  : [Integer], nominal bitrate in bps. 
		public static final String mp3lengthbytes = "mp3.length.bytes"; //  : [Integer], length in bytes. 
		public static final String mp3lengthframes = "mp3.length.frames"; //  : [Integer], length in frames. 
		public static final String mp3framesizebytes = "mp3.framesize.bytes"; //  : [Integer], framesize of the first frame. framesize is not constant for VBR streams. 
		public static final String mp3frameratefps = "mp3.framerate.fps"; //  : [Float], framerate in frames per seconds. 
		public static final String mp3headerpos = "mp3.header.pos"; //  : [Integer], position of first audio header (or ID3v2 size). 
		public static final String mp3vbr = "mp3.vbr"; //  : [Boolean], vbr flag. 
		public static final String mp3vbrscale = "mp3.vbr.scale"; //  : [Integer], vbr scale. 
		public static final String mp3crc = "mp3.crc"; //  : [Boolean], crc flag. 
		public static final String mp3original = "mp3.original"; //  : [Boolean], original flag. 
		public static final String mp3copyright = "mp3.copyright"; //  : [Boolean], copyright flag. 
		public static final String mp3padding = "mp3.padding"; //  : [Boolean], padding flag. 
		public static final String mp3mode = "mp3.mode"; //  : [Integer], mode 0:STEREO 1:JOINT_STEREO 2:DUAL_CHANNEL 3:SINGLE_CHANNEL 
		public static final String mp3id3taggenre = "mp3.id3tag.genre"; //  : [String], ID3 tag (v1 or v2) genre. 
		public static final String mp3id3tagtrack = "mp3.id3tag.track"; //  : [String], ID3 tag (v1 or v2) track info. 
		public static final String mp3id3tagv2 = "mp3.id3tag.v2"; //  : [InputStream], ID3v2 frames. 
		public static final String mp3shoutcastmetadatakey = "mp3.shoutcast.metadata.key"; //  : [String], Shoutcast meta key with matching value. 
//		    For instance : 
//		      mp3.shoutcast.metadata.icy-irc=#shoutcast 
//		      mp3.shoutcast.metadata.icy-metaint=8192 
//		      mp3.shoutcast.metadata.icy-genre=Trance Techno Dance 
//		      mp3.shoutcast.metadata.icy-url=http://www.di.fm
	
}

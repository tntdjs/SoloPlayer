package com.tntdjs.soloplayer.media.tag.id3tag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotReadVideoException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;

/**
 * 
 * @author tsenauskas
 *
 */
public class ID3FileInfo {
	private static Logger LOGGER = LogManager.getRootLogger();
	private ID3FileTypeMMedia id3FileInfo;
	private Map<String, String> fileTagAttribList = new HashMap<String, String>();
	
	/**
	 * Constructor Create a file lister array
	 * 
	 * @param initPath
	 */
	public ID3FileInfo(File file) {
		LOGGER.debug("ID3 Tag Info for - " + file.getName());
		id3FileInfo = new ID3FileTypeMMedia(file.getName());
		
		if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("mp4")) {
			try {
				id3FileInfo.setCopyrighted(false);
				id3FileInfo.setCheckSumCRC32(FileUtils.checksumCRC32(file));
				id3FileInfo.setSizeFormatted(FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(file)));
				id3FileInfo.setISFile(true);
				id3FileInfo.setFileBaseName(FilenameUtils.getBaseName(file.getName()));
				id3FileInfo.setFileExtension(FilenameUtils.getExtension(file.getName()));
				id3FileInfo.setFilePath(FilenameUtils.getPath(file.getName()));

				AudioFile f = null;
				try {
					f = AudioFileIO.read(file);
					AudioHeader ah = f.getAudioHeader();
					Iterator<TagField> ifields = f.getTag().getFields();
					
					while (ifields.hasNext()) {
						TagField tagField = ifields.next();						
						String tagValue = tagField.toString().replace("Text=", "");
						tagValue = tagValue.replace("Description=", "");
						tagValue = tagValue.replaceAll("\"", "");
						tagValue = tagValue.replaceAll(";", "");
						fileTagAttribList.put(tagField.getId(), tagValue);
					}
					
					fileTagAttribList.put("Bitrate", ah.getBitRate());
//					fileTagAttribList.put("Bits p/sample", ah.getBitsPerSample()+"");
					fileTagAttribList.put("Channels", ah.getChannels());
					fileTagAttribList.put("Encoding", ah.getEncodingType());
					fileTagAttribList.put("Format", ah.getFormat());
					fileTagAttribList.put("Track Precise Length", ah.getTrackLength()+"");
					fileTagAttribList.put("Sample Rate", ah.getSampleRate());
					fileTagAttribList.put("Sample Rate#", ah.getSampleRateAsNumber()+"");
					fileTagAttribList.put("Track Length", ah.getTrackLength()+"");
//					fileTagAttribList.put("Is Lossless", ah.isLossless()+"");
					fileTagAttribList.put("Is Variable Bitrate", ah.isVariableBitRate()+"");
										
				} catch (CannotReadVideoException crve) {
					LOGGER.debug(crve);
				} catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
					LOGGER.debug(e);
				}
			} catch (IOException e) {
				LOGGER.debug(e);
			}
		}
		LOGGER.debug("ID3 Tag Info - " + id3FileInfo);
	}
	
	/**
	 * getFileTagAttribList
	 * @return List of pairs of tag/values
	 */
	public Map<String, String> getFileTagAttribList() {
		return fileTagAttribList;
	}
}

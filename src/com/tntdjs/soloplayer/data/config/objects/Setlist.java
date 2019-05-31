package com.tntdjs.soloplayer.data.config.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content
 * contained within this class.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "song" })
public class Setlist {

	protected List<Song> song;
	@XmlAttribute(name = "sl-name")
	protected String slName;
	@XmlAttribute(name = "sl-index")
	protected Byte slIndex;
	@XmlAttribute(name = "auto-play")
	protected String autoPlay;

	@Override
	public String toString() {
		return slName + " - "+ slIndex;
	}

	public String getAutoPlay() {
		if (null == autoPlay) {
			return "false";
		}
		return autoPlay;
	}

	public void setAutoPlay(String autoPlay) {
		this.autoPlay = autoPlay;
	}

	/**
	 * Gets the value of the song property. This accessor method returns
	 * a reference to the live list, not a snapshot. Therefore any
	 * modification you make to the returned list will be present inside
	 * the JAXB object. This is why there is not a <CODE>set</CODE>
	 * method for the song property.
	 */
	public List<Song> getSongs() {
		if (song == null) {
			song = new ArrayList<Song>();
		}
		
//		Collections.sort(songs, new Comparator<Song>() {
//			@Override
//			public int compare(Song songlist1, Song songlist2) {
//				// -1 - less than, 1 - greater than, 
//				//  0 - equal, all inversed for descending		
//				return songlist2.getSongIndex() > songlist1.getSongIndex() ? -1 : 
//					(songlist2.getSongIndex() < songlist1.getSongIndex()) ? 1 : 0;
//			}
//        });
		
		return song;
	}

	public void setSongs(List<Song> songlist) {
		this.song = songlist;
	}

	/**
	 * Gets the value of the slName property.
	 */
	public String getSlName() {
		return slName;
	}

	/**
	 * Sets the value of the slName property.
	 */
	public void setSlName(String value) {
		this.slName = value;
	}

	/**
	 * Gets the value of the slIndex property.
	 */
	public Byte getSlIndex() {
		return slIndex;
	}

	/**
	 * Sets the value of the slIndex property.
	 */
	public void setSlIndex(Byte value) {
		this.slIndex = value;
	}
}
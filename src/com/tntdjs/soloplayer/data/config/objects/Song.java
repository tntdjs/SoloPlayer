package com.tntdjs.soloplayer.data.config.objects;

import java.io.File;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.tntdjs.soloplayer.media.tag.id3tag.ID3FileInfo;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content
 * contained within this class.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "location" })
public class Song {

	@XmlElement(required = true)
	protected String location;
	@XmlAttribute(name = "song-name")
	protected String songName;
	@XmlAttribute(name = "song-index")
	protected Integer songIndex;

	@XmlTransient
	private ID3FileInfo id3FileInfo;
	
	@Override
	public String toString() {
		return songName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String value) {
		this.location = value;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String value) {
		this.songName = value;
	}

	public Integer getSongIndex() {
		return songIndex;
	}

	public void setSongIndex(Integer value) {
		this.songIndex = value;
	}

	public ID3FileInfo getId3FileInfo() {
		return id3FileInfo;
	}

	public void setId3FileInfo(ID3FileInfo id3FileInfo) {
		this.id3FileInfo = id3FileInfo;
	}
	
	/**
	 * 
	 * @param u
	 * @param parent
	 */
	public void afterUnmarshal(Unmarshaller u, Object parent) {		
		setId3FileInfo(new ID3FileInfo(new File(getLocation())));
	}
}
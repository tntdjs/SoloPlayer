package com.tntdjs.soloplayer.data.config.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.tntdjs.soloplayer.data.PlaylistXMLUnmarshaller;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained
 * within this class.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "setlist" })
public class Playlist {

	@XmlElement(required = true)
	protected Setlist setlist;
	@XmlAttribute(name = "pl-id")
	protected Long plId;
	@XmlAttribute(name = "pl-name")
	protected String plName;
	@XmlAttribute(name = "create-datetime")
	protected String createDatetime;
	@XmlAttribute(name = "update-datetime")
	protected String updateDatetime;
	@XmlAttribute(name = "pl-index")
	protected Byte plIndex;
	@XmlAttribute(name = "location")
	protected String location;
	@XmlAttribute(name = "filemask")
	protected String filemask;
	@XmlAttribute(name = "auto-play")
	protected String autoPlay;

	@Override
	public String toString() {
		return plName + " (" + plId + ")";
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
	 * Gets the value of the setlist property.
	 */
	public Setlist getSetlist() throws Exception {
		if (null != location && !location.isEmpty()) {
			setlist = PlaylistXMLUnmarshaller.getInstance().getFolderSetList(new Setlist(), location);
		}
		
//		Collections.sort(setlist, new Comparator<PlayLists.Playlist.Setlist>() {
//			@Override
//			public int compare(PlayLists.Playlist.Setlist setlist1, PlayLists.Playlist.Setlist setlist2) {
//				// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending		
//				return setlist2.getSlIndex() > setlist1.getSlIndex() ? -1 : (setlist2.getSlIndex() < setlist1.getSlIndex()) ? 1 : 0;
//			}
//        });
		
		return setlist;
	}

	/**
	 * Sets the value of the setlist property.
	 */
	public void setSetlist(Setlist value) {
		this.setlist = value;
	}

	/**
	 * Gets the value of the plId property.
	 */
	public Long getPlId() {
		return plId;
	}

	/**
	 * Sets the value of the plId property.
	 */
	public void setPlId(Long value) {
		this.plId = value;
	}

	/**
	 * Gets the value of the plName property.
	 */
	public String getPlName() {
		return plName;
	}

	/**
	 * Sets the value of the plName property.
	 */
	public void setPlName(String value) {
		this.plName = value;
	}

	/**
	 * Gets the value of the createDatetime property.
	 */
	public String getCreateDatetime() {
		return createDatetime;
	}

	/**
	 * Sets the value of the createDatetime property.
	 */
	public void setCreateDatetime(String value) {
		this.createDatetime = value;
	}

	/**
	 * Gets the value of the updateDatetime property.
	 */
	public String getUpdateDatetime() {
		return updateDatetime;
	}

	/**
	 * Sets the value of the updateDatetime property.
	 */
	public void setUpdateDatetime(String value) {
		this.updateDatetime = value;
	}

	/**
	 * Gets the value of the plIndex property.
	 */
	public Byte getPlIndex() {
		return plIndex;
	}

	/**
	 * Sets the value of the plIndex property.
	 */
	public void setPlIndex(Byte value) {
		this.plIndex = value;
	}

	/**
	 * Gets the value of the location property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the value of the location property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLocation(String value) {
		this.location = value;
	}

	/**
	 * Gets the value of the filemask property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFilemask() {
		return filemask;
	}

	/**
	 * Sets the value of the filemask property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFilemask(String value) {
		this.filemask = value;
	}

}
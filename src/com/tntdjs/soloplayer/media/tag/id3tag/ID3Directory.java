package com.tntdjs.soloplayer.media.tag.id3tag;

import com.tntdjs.utils.StringUtils;

/**
 * 
 * @author tsenauskas
 *
 */
public class ID3Directory implements IID3 {
	private String longName = new String();
	private String sizeFormatted = "";

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public ID3Directory(String name) {
		super();
		this.setLongName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tms.mc.util.ITreeLeafDirectory#getSizeFormatted()
	 */
	@Override
	public String getSizeFormatted() {
		return sizeFormatted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tms.mc.util.ITreeLeafDirectory#setSizeFormatted(java.lang.String)
	 */
	@Override
	public void setSizeFormatted(String sizeFormatted) {
		this.sizeFormatted = sizeFormatted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tms.mc.util.ITreeLeafDirectory#getLongName()
	 */
	@Override
	public String getLongName() {
		return longName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tms.mc.util.ITreeLeafDirectory#setLongName(java.lang.String)
	 */
	@Override
	public void setLongName(String longName) {
		this.longName = longName;
	}

	@Override
	public String toString() {
		String output = "Name: " + this.getLongName();
		if (!getSizeFormatted().isEmpty()) {
			output += StringUtils.newLine() + StringUtils.tab() 
				+ " {" + "Size: " + this.getSizeFormatted() + "}";
		}
		return output;
	}
}
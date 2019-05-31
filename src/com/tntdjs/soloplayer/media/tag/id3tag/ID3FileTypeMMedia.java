package com.tntdjs.soloplayer.media.tag.id3tag;

/**
 * A Multi-media TreeLeafFile subclass
 * 
 * @author tsenauskas
 *
 */
public class ID3FileTypeMMedia extends ID3File {

	// ID3TAGS
	private String TIT2 = ""; // e.g. song title
	private String TKEY = "";
	private String TBPM = "";
	private long bitrate = 0;
	private String trackLength = "";
	private String version = "";
	private String layer = "";
	private String channelMode = "";
	private long noOfSamples = 0;
	private long samplingRate = 0;
	private boolean isCopyrighted = true;
	private boolean isOriginal = true;
	private boolean isVariableBitRatefalse = false;
	private long fileSize = 0;
	private String encoder = "";

	/**
	 * 
	 * @param name
	 */
	public ID3FileTypeMMedia(String name) {
		super(name);
	}

	public String getTIT2() {
		return TIT2;
	}

	public void setTIT2(String tIT2) {
		TIT2 = tIT2;
	}

	public String getTKEY() {
		return TKEY;
	}

	public void setTKEY(String tKEY) {
		TKEY = tKEY;
	}

	public String getTBPM() {
		return TBPM;
	}

	public void setTBPM(String tBPM) {
		TBPM = tBPM;
	}

	public long getBitrate() {
		return bitrate;
	}

	public void setBitrate(long bitrate) {
		this.bitrate = bitrate;
	}

	public String getTrackLength() {
		return trackLength;
	}

	public void setTrackLength(String trackLength) {
		this.trackLength = trackLength;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getChannelMode() {
		return channelMode;
	}

	public void setChannelMode(String channelMode) {
		this.channelMode = channelMode;
	}

	public long getNoOfSamples() {
		return noOfSamples;
	}

	public void setNoOfSamples(long noOfSamples) {
		this.noOfSamples = noOfSamples;
	}

	public long getSamplingRate() {
		return samplingRate;
	}

	public void setSamplingRate(long samplingRate) {
		this.samplingRate = samplingRate;
	}

	public boolean isCopyrighted() {
		return isCopyrighted;
	}

	public void setCopyrighted(boolean isCopyrighted) {
		this.isCopyrighted = isCopyrighted;
	}

	public boolean isOriginal() {
		return isOriginal;
	}

	public void setOriginal(boolean isOriginal) {
		this.isOriginal = isOriginal;
	}

	public boolean isVariableBitRatefalse() {
		return isVariableBitRatefalse;
	}

	public void setVariableBitRatefalse(boolean isVariableBitRatefalse) {
		this.isVariableBitRatefalse = isVariableBitRatefalse;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getEncoder() {
		return encoder;
	}

	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

}

package com.tntdjs.soloplayer.media.tag.id3tag;

import com.tntdjs.utils.StringUtils;

/**
 * 
 * @author tsenauskas
 *
 */
public class ID3File extends ID3Directory {

	// File specific attributes
	private boolean isFile = false;
	private long fileCheckSumCRC32 = 0;
	private String fileBaseName = "";
	private String fileExtension = "";
	private String filePath = "";

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public ID3File(String name) {
		super(name);
	}

	@Override
	public String toString() {
		String output = super.toString();
		if (this.isFile) {
			output += StringUtils.newLine() + StringUtils.tab() + " {" + (isFile ? "isFile: " + isFile : "")
					+ (fileCheckSumCRC32 != 0 ? " | CRC32: " + fileCheckSumCRC32 : "")
					+ (!fileBaseName.isEmpty() ? " | Base Name: " + fileBaseName : "")
					+ (!fileExtension.isEmpty() ? " | Extension: " + fileExtension : "")
					+ (!filePath.isEmpty() ? " | File Path: " + filePath : "") + "}";
		}

		return output;
	}

	public long getCheckSumCRC32() {
		return fileCheckSumCRC32;
	}

	public void setCheckSumCRC32(long checkSumCRC32) {
		this.fileCheckSumCRC32 = checkSumCRC32;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setISFile(boolean isFile) {
		this.isFile = isFile;
	}

	public String getFileBaseName() {
		return fileBaseName;
	}

	public void setFileBaseName(String fileBaseName) {
		this.fileBaseName = fileBaseName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExt) {
		this.fileExtension = fileExt;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

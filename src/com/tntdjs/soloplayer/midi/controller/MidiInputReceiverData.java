package com.tntdjs.soloplayer.midi.controller;

/**
 * 
 * @author tsenauskas
 *
 */
public class MidiInputReceiverData {
	public String receiverData;

	public MidiInputReceiverData() {
		
	}
	
	public MidiInputReceiverData(String argReceiverData) {
		super();
		setReceiverData(argReceiverData);
	}
	
	public void setReceiverData(String argReceiverData) {
		receiverData = argReceiverData;
	}
	
	public String getReceiverData() {
		return receiverData;
	}
}
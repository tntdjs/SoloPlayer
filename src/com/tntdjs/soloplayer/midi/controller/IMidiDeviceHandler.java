package com.tntdjs.soloplayer.midi.controller;

/**
 * 
 * @author tsenausk
 *
 */
public interface IMidiDeviceHandler {	
	public void deInitMidi();

	public void initMidi();

	public boolean isInitialized();

	public void configure();
}
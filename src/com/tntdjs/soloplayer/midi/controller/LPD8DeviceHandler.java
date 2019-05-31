package com.tntdjs.soloplayer.midi.controller;

import com.tntdjs.utils.SystemPropertyMgr;

/**
 * 
 * @author tsenauskas
 *
 */
public final class LPD8DeviceHandler extends AbstractMidiDeviceHandler {

	/**
	 * getMidiInputReceiver()
	 * return Device Input Receiver class
	 */
	public IReceiver getMidiInputReceiver() {
		return new LPD8InputReceiver();
	}
	
	/**
	 * getMidiDeviceName()
	 * return configurable device name e.g. system.properties
	 */
	protected String getMidiDeviceName() {		
		return SystemPropertyMgr.getInstance().getString("midi.device");
	}
	
	
}

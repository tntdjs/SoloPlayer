package com.tntdjs.soloplayer.midi.controller;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author tsenauskas
 *
 */
public abstract class AbstractMidiDeviceHandler implements IMidiDeviceHandler {
	private static Logger LOGGER = LogManager.getRootLogger();
	
	private String DEVICE_NAME;
	private MidiDevice MIDI_DEVICE;
	private IReceiver MIDI_RECEIVER;
	private Transmitter MIDI_TRANSMITTER;

	private boolean initialized = false;
	
	/**
	 * 
	 * @TODO maybe needs a new MidiInputReceiver Interface
	 * @return
	 */
	public abstract IReceiver getMidiInputReceiver();

	/**
	 * 
	 * @return
	 */
	protected abstract String getMidiDeviceName();
	
	@Override
	public void deInitMidi() {
		if (null != MIDI_DEVICE && isInitialized()) {
			MIDI_DEVICE.close();
			setInitialized(false);
			LOGGER.info("Midi Controller De-initialized");
		}
	}

	@Override
	public void initMidi() {
		LOGGER.info("Midi Controller Initializing...");
		setInitialized(false);

		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		// for (int i = 0; i < infos.length; i++) {
		for (MidiDevice.Info info : infos) {
			LOGGER.info("Midi Ctlrs(" + info + ")" + info);
			
			try {
				// if (DEVICE_NAME.equals(infos[i].getName())) {
				// device = MidiSystem.getMidiDevice(infos[i]);
				if (DEVICE_NAME.equals(info.getName())) {
					MIDI_DEVICE = MidiSystem.getMidiDevice(info);
					// does the device have any transmitters?
					// if it does, add it to the device list

					
					// get all transmitters
					MIDI_RECEIVER = getMidiInputReceiver();
					MIDI_TRANSMITTER = MIDI_DEVICE.getTransmitter();
					MIDI_TRANSMITTER.setReceiver((Receiver) MIDI_RECEIVER);
					
					// open each device
					MIDI_DEVICE.open();
					// if code gets this far without throwing an exception
					// print a success message
					LOGGER.info(MIDI_DEVICE.getDeviceInfo() + " Was Opened");
					setInitialized(true);
				}
			} catch (MidiUnavailableException e) {
				// this is not severe it is just an unmappable device
				LOGGER.info("Error Aquiring Midi Device", e);
			}
		}
	}

	public AbstractMidiDeviceHandler() {
		super();
		DEVICE_NAME = getMidiDeviceName();
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	protected void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void configure() {

	}

	public IReceiver getReceiver() {
		return MIDI_RECEIVER;
	}

	public void setReceiver(IReceiver ireceiver) {
		this.MIDI_RECEIVER = ireceiver;
	}

	public Transmitter getTransmitter() {
		return MIDI_TRANSMITTER;
	}

	public void setTransmitter(Transmitter trans) {
		this.MIDI_TRANSMITTER = trans;
	}
	
}
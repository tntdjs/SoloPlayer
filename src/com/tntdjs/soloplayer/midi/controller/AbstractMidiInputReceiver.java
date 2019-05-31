package com.tntdjs.soloplayer.midi.controller;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * 
 * @author tsenausk
 *
 */
public abstract class AbstractMidiInputReceiver implements Receiver, IReceiver {
	private MidiInputReceiverData midiInputReceiverData = new MidiInputReceiverData();

	public AbstractMidiInputReceiver(String receiverData) {
		super();
		midiInputReceiverData.receiverData = receiverData;
	}
	
	public AbstractMidiInputReceiver() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.tms.midi.IReceiver#setMidiInputReciever(java.lang.String)
	 */
	@Override
	public void setMidiInputReciever(String receiverData) {
		midiInputReceiverData.receiverData = receiverData;
	}
	
	public abstract void send(MidiMessage msg, long timeStamp);
	
	/**
	 * Pass through to the Java FX Controllers executer
	 * @param midiNote
	 */
	protected abstract void executer(String midiNote); 

	/**
	 * @ TODO understand better how midi devices get closed on a Receiver
	 */
	public void close() {
		
	}
	
	protected abstract boolean isButtonEnabled(int MidiNote);

}
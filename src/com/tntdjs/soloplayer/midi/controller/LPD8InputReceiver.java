package com.tntdjs.soloplayer.midi.controller;

import javax.sound.midi.MidiMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.soloplayer.RootAppMgr;



/**
 * 
 * @author tsenauskas
 *
 */
public class LPD8InputReceiver extends AbstractMidiInputReceiver {
	private static Logger LOGGER = LogManager.getRootLogger();
	
	/**
	 * Pass through to the Java FX Controllers executer
	 * @param midiNote
	 */
	@Override
	protected void executer(String midiNote) {
//	    Platform.runLater(new Runnable() {
//	        @Override public void run() {
//	    		if (SoloPlayerAppMain.PLAYER_SCREEN.equalsIgnoreCase(
//	    				SoloPlayerAppMain.getInstance().getFXSceneManager().getCurrentScene())) {
//	    			System.out.println("Trigger Player");		
//	    			((IMidiControl)SoloPlayerAppMain.getInstance().getFXSceneManager().getFXML_LOADER().getController()).midiControllerTrigger(midiNote);			    			
//	    			
//	    		}
//	        }
//	      });
	}

	/**
	 * @ TODO understand better how midi devices get closed on a Receiver
	 */
	public void close() {
		
	}
	
	public void send(MidiMessage msg, long timeStamp) {
		String outMsg = "";
		int offSet1 = -112; //LPD8 Bank 1 Pads in "PAD" mode 		//LPD8XMLHelper.getInstance().getButtonSets().get(1).getBank();
		int offSet2 = -64;  //LPD8 Bank 2 Pads in "PROG CHNG" mode 	//LPD8XMLHelper.getInstance().getButtonSets().get(0).getBank();
		
		int offSet3 = -80; //LPD8 dials k1-k8

		if (msg.getLength() > 0) {
//			for (int i=0;i<msg.getMessage().length;i++) {
//				System.out.println(" Status: " + msg.getStatus() + " - i="+i + " " + msg.getMessage()[i]);
//			}
			
			if (Integer.valueOf(msg.getMessage()[0]).equals(offSet1)) {
				// upper bank of button set user in PAD mode
				switch (msg.getMessage()[1]) {
				case 36:
					executer("bank1-pad1");
					RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).previous();
					break;
				case 38:
					executer("bank1-pad2");
					RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).stop();
					break;
				case 40:
					executer("bank1-pad3");
					RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).play();
					break;
				case 41:
					executer("bank1-pad4");
					RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).next();
					break;
				case 43:
					executer("bank1-pad5");
					break;
				case 45:
					executer("bank1-pad6");
					break;
				case 46:
					executer("bank1-pad7");
					break;
				case 47:
					executer("bank1-pad8");
					break;
				case 48:
					executer("bank1-pad9");
					break;

				default:
					outMsg = "No Msg Found!";
					break;
				}
			} else if (Integer.valueOf(msg.getMessage()[0]).equals(offSet2)) {
				// lower bank of button set user in prog chng mode
				switch (msg.getMessage()[1]) {
				case 0:
					executer("bank2-pad1");
					break;
				case 1:
					executer("bank2-pad2");
					break;
				case 2:
					executer("bank2-pad3");
					break;
				case 3:
					executer("bank2-pad4");
					break;
				case 4:
					executer("bank2-pad5");
					break;
				case 5:
					executer("bank2-pad6");
					break;
				case 6:
					executer("bank2-pad7");
					break;
				case 7:
					executer("bank2-pad8");
					break;

				default:
					outMsg = "No Msg Found!";
					break;
				}
			} else if (Integer.valueOf(msg.getMessage()[0]).equals(offSet3)) {
				// set of dials on LPD8
				// Dial values range from +1 to +127 e.g. value in msg.getMessage()[2]
				switch (msg.getMessage()[1]) {
				case 1:
					executer("bank3-dial1");
					int gain = msg.getMessage()[2];

					gain = (int)((double)gain - ((double)gain *.21));
//					or
//					if (gain > 100) {
//						gain = 100;
//					}
					
					LOGGER.info("Midi Gain: " + gain);
					RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).setCurrentVolume(gain);
					RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).setVolume();
					LOGGER.debug("Volume=" + msg.getMessage()[2]);
					break;
				case 2:
					executer("bank3-dial2");
//					System.out.println("Gain=" + msg.getMessage()[2]);
					break;
				case 3:
					executer("bank3-dial3");
					
					break;
				case 4:
					executer("bank3-dial4");
					
					break;
				case 5:
					executer("bank3-dial5");
					break;
				case 6:
					executer("bank3-dial6");
					break;
				case 7:
					executer("bank3-dial7");
					break;
				case 8:
					executer("bank3-dial8");
					break;
				case 9:
					executer("bank1-pad9");
					break;

				default:
					outMsg = "No Msg Found!";
					break;
				}
			}
			if (!outMsg.isEmpty()) {
//				LOGGER.info("Input Receiver: " + outMsg);
				outMsg = "";
			}
		}

	}

@Override
protected boolean isButtonEnabled(int MidiNote) {
	// TODO Auto-generated method stub
	return false;
}

}
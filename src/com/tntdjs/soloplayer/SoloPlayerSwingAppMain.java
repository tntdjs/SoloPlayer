package com.tntdjs.soloplayer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.SystemPropertyMgr;
import com.tntdjs.utils.i18n.TranslationMgr;
import com.tntdjs.soloplayer.midi.controller.LPD8DeviceHandler;
import com.tntdjs.soloplayer.ui.swing.SoloPlayerJFrame;
import com.tntdjs.soloplayer.voice.VoiceHandler;

/**
 * SoloPlayerSwingAppMain
 * @author tsenausk
 *
 */
public class SoloPlayerSwingAppMain {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static SoloPlayerSwingAppMain INSTANCE;
	private SoloPlayerJFrame FRAME;
    private LPD8DeviceHandler lpd8;
    private VoiceHandler VOICE_HANDLER;
    
	/**
     * Application Main
     * @param args
     */
	public static void main(String[] args) {
		new SoloPlayerSwingAppMain();
	}
    
    /**
	 * Constructor
	 */
	public SoloPlayerSwingAppMain() {
		LOGGER.info("Application Starting...");
		initMidi();   
        initUI();

        SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	FRAME.setVisible(true);
	        }
	    });
	    
	    setInstance(this);
	    
	    if (SystemPropertyMgr.getInstance().getString("voice.enabled").equalsIgnoreCase("true")) {
	       	VOICE_HANDLER = new VoiceHandler();
	       	VOICE_HANDLER.run();
	    }
	}

	private void initUI() {
		LOGGER.info("InitUI");
		RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER);
		setFrame(new SoloPlayerJFrame());
	}

	private void initMidi() {
		LOGGER.info("InitMidi");
		lpd8 = new LPD8DeviceHandler();
        lpd8.initMidi();
	}

	/**
	 * Close the application
	 */
	public void CloseApplication() {
		Object selectedValue = JOptionPane.showConfirmDialog(FRAME, 
			TranslationMgr.getInstance().getText("ExitAreYouSure"), 
			TranslationMgr.getInstance().getAppTitle(),
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (selectedValue.equals(JOptionPane.YES_OPTION)) {
			LOGGER.info("Application Exited Safely");
			System.exit(0);
		}
	}

	public static SoloPlayerSwingAppMain getInstance() {
		return INSTANCE;
	}

	private void setInstance(SoloPlayerSwingAppMain argInstance) {
		INSTANCE = argInstance;
	}

	public SoloPlayerJFrame getFrame() {
		return FRAME;
	}


	private void setFrame(SoloPlayerJFrame fRAME) {
		FRAME = fRAME;
	}

    public VoiceHandler getVOICE_HANDLER() {
		return VOICE_HANDLER;
	}
}

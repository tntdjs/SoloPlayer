package com.tntdjs.soloplayer.voice;

import java.io.IOException;

import javax.swing.SwingUtilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.soloplayer.RootAppMgr;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

/**
 * VoiceHandler
 * 
 * @author tsenauskas
 */
public class VoiceHandler implements Runnable {
	private static Logger LOGGER = LogManager.getRootLogger();
	private LiveSpeechRecognizer RECOGNIZE;
	private SpeechResult RESULT;

	// public static void main(String[] args) throws IOException {
	// new VoiceHandler();
	// }

	public VoiceHandler() {
		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("file:config/voice/9990/9990.dic");
		configuration.setLanguageModelPath("file:config/voice/9990/9990.lm");
		// configuration.setGrammarPath("file:config/voice/");
		// configuration.setGrammarName("player");
		// configuration.setUseGrammar(false);
		// configuration.setSampleRate(44000);

		// Recognizer Object, Pass the Configuration object
		try {
			RECOGNIZE = new LiveSpeechRecognizer(configuration);
		} catch (IOException e) {
			LOGGER.fatal("Error loading voice handler and its configuration files, system should exit...", e);
			System.exit(-1);
		}

		// Start Recognition (The parm clears the previous cache if true)
		RECOGNIZE.startRecognition(true);
	}

	public void run() {

		// Checking if recognizer has recognized the speech
		while (true) {

			if ((RESULT = RECOGNIZE.getResult()) != null) {
				LOGGER.warn(RESULT);

				// Get the recognize speech
				RESULT.getWords();
				LOGGER.warn(RESULT.getWords());
				String command = RESULT.getHypothesis();
				LOGGER.warn(command);
				System.out.println(command);
				
				RECOGNIZE.stopRecognition();
				// Match recognized speech with our commands
				if (command.equalsIgnoreCase("song") || command.equalsIgnoreCase("play song")) { // play
					LOGGER.info("play!");
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).play();
						}
					});

				} else if (command.equalsIgnoreCase("pause") || command.equalsIgnoreCase("pause song")) { // pause
					LOGGER.info("pause");
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).pause();
						}
					});
				} else if (command.equalsIgnoreCase("next")) {
					LOGGER.info("next");
				} else if (command.equalsIgnoreCase("previous")) {
					LOGGER.info("previous");
				} else if (command.equalsIgnoreCase("stop")) {
					LOGGER.info("stop");
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							RootAppMgr.getInstance().getSoloPlayerController(RootAppMgr.PLAYER_CONTROLLER).stop();
						}
					});
				}
				RECOGNIZE.startRecognition(true);
			}
		}
		// RECOGNIZE.stopRecognition();
	}

}
package com.tntdjs.soloplayer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.SystemPropertyMgr;
import com.tntdjs.utils.i18n.TranslationMgr;
import com.tntdjs.soloplayer.RootAppMgr;
import com.tntdjs.soloplayer.SoloPlayerSwingAppMain;
import com.tntdjs.soloplayer.ui.swing.menu.SPMenuBar;

/**
 * SoloPlayerJFrame
 * @author tsenausk
 *
 */
public class SoloPlayerJFrame extends JFrame {
	private static Logger LOGGER = LogManager.getRootLogger();
	private static final long serialVersionUID = 1L;
	private JPanel ROOT_PANEL = new JPanel();
	
	public SoloPlayerJFrame() {
		LOGGER.info("Create JFrame");
		setTitle(TranslationMgr.getInstance().getAppTitle());
		setBackground(Color.WHITE);
		
		try {
			ImageIcon im = new ImageIcon(ImageIO.read(new File("res/images/app-icon-trans.png")));
			this.setIconImage(im.getImage());
		} catch (IOException e) {
			LOGGER.error("Error loading app-icon", e);
		}

		if (SystemPropertyMgr.getInstance().getString("app.frame.decorated").equalsIgnoreCase("false")) {
			setUndecorated(true);
		}
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    addWindowListener( new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	        	SoloPlayerSwingAppMain.getInstance().CloseApplication();
	        }
	    } );
		
		this.setSize(new Dimension(Integer.parseInt(SystemPropertyMgr.getInstance().getString("app.width")),
				Integer.parseInt(SystemPropertyMgr.getInstance().getString("app.height"))));

		if (SystemPropertyMgr.getInstance().getString("app.maximize").equalsIgnoreCase("true")) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		
		ROOT_PANEL.setLayout(new BorderLayout());
		ROOT_PANEL.add(RootAppMgr.getInstance().getRootPanel(), BorderLayout.CENTER);				

		setJMenuBar(SPMenuBar.getInstance());
		getContentPane().add(ROOT_PANEL);        
//        pack();
//        setVisible(true);		
	}
	
}

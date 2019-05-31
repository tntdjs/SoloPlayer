Soloplayer (readme.txt)
	Version 1.0
	Copyright (c) 2017, Todd M. Senauskas and/or its affiliates. All rights reserved.

#Initial configurations
	#Linux
		1. Make the sp.sh script executable
			sudo chmod +x * -R
		2. Update system.properties to reference the Linux Midi configuration
			include=midi-config-linux.properties
		3. Switch to the Linux playlist file in system.properties
			playlist.filename=/home/pi/Public/playlist/playlist.xml
		4. Set screen to maximize in system.properties app.maximize=false
		5. set frame decorated false to remove min/max/close title buttons system.properties frame.decorated=true
	
	#Windows
		1. Update system.properties to reference the Windows Midi configuration
			include=midi-config-windows.properties
		2. Switch to the Windows playlist file
			copy playlist-windows.xml to playlist.xml
			
#Launch - How to launch the application
	#Linux
		ch home/pi/Public/soloplayer-1.0.01
		./sp.sh
		
		or execute the shortcut shell script on desktop
		
	#Windows
		cd soloplayer-1.0.01
		run sp.bat

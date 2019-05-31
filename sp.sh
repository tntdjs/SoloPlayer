#!/bin/sh

PATH=/home/pi/Public/jfx/jre/lib:/home/pi/Public/jfx/jre/lib/arm
PATH=$PATH:/home/pi/Public/soloplayer-1.0.01
PATH=$PATH:/home/pi/Public/soloplayer-1.0.01/lib
PATH=$PATH:/home/pi/Public/soloplayer-1.0.01/lib/ext
PATH=$PATH:/home/pi/Public/soloplayer-1.0.01/lib/config
PATH=$PATH:/home/pi/Public/soloplayer-1.0.01/res
PATH=$PATH:/home/pi/Public/soloplayer-1.0.01/res/i18n
PATH=$PATH:/home/pi/Public/soloplayer-1.0.01/config/playlist
export PATH

MYCP=/home/pi/Public/soloplayer-1.0.01/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/apache-log4j-2.8.1-bin/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/commons-beanutils-1.9.3/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/commons-configuration2-2.1.1/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/commons-io-2.5/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/commons-lang3-3.5/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/commons-logging-1.2/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/javazoom/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/oracle-java-tablelayout/*
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/ext/jaudiotagger-2.2.6-SNAPSHOT.jar
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/config
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/config/playlist
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/res
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/res/i18n
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/res/images
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/res/images/mp
MYCP=$MYCP:/home/pi/Public/soloplayer-1.0.01/lib/patch/*

JAVAFX_DEBUG=1
/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/bin/java -version
/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/bin/java -cp "$MYCP" com.tms.soloplayer.SoloPlayerSwingAppMain

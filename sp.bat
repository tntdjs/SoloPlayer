@echo off
cls

rem set java_path="C:/Program Files/Java/jre1.8.0_121"
set java_path="C:\Program Files\Java\jdk1.8.0_121"
set sp_path=%cd%

set path=%path%;%sp_path%/res;%sp_path%/res/i18n;%sp_path%/config

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;%sp_path%/res
set CLASSPATH=%CLASSPATH%;%sp_path%/res/i18n
set CLASSPATH=%CLASSPATH%;%sp_path%/res/images
set CLASSPATH=%CLASSPATH%;%sp_path%/res/images/filebrowser
set CLASSPATH=%CLASSPATH%;%sp_path%/res/images/mp
set CLASSPATH=%CLASSPATH%;%sp_path%/config
set CLASSPATH=%CLASSPATH%;%sp_path%/config/playlist
set CLASSPATH=%CLASSPATH%;%sp_path%/config/soloplayer.application
set CLASSPATH=%CLASSPATH%;%sp_path%/config/soloplayer.player

set CLASSPATH=%CLASSPATH%;%sp_path%/lib/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/apache-log4j-2.8.1-bin/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/commons-beanutils-1.9.3/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/commons-configuration2-2.1.1/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/commons-io-2.5/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/commons-lang3-3.5/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/commons-logging-1.2/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/javazoom/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/oracle-java-tablelayout/*
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/ext/jaudiotagger-2.2.6-SNAPSHOT.jar
set CLASSPATH=%CLASSPATH%;%sp_path%/config
set CLASSPATH=%CLASSPATH%;%sp_path%/config/playlist
set CLASSPATH=%CLASSPATH%;%sp_path%/res
set CLASSPATH=%CLASSPATH%;%sp_path%/res/i18n
set CLASSPATH=%CLASSPATH%;%sp_path%/res/images
set CLASSPATH=%CLASSPATH%;%sp_path%/res/images/mp
set CLASSPATH=%CLASSPATH%;%sp_path%/lib/patch/*

%java_path%"/bin/java -version"
%java_path%"/bin/java" com.tms.soloplayer.SoloPlayerSwingAppMain
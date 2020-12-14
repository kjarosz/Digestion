package com.kjarosz.digestion.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.LineUnavailableException;

public class SoundSystem {
    private static Mixer mMixer;
	 private static boolean mInitialized;

    // Program must call this function before any sound is available
    public static boolean Init() {
      Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		if(mixers.length == 0) {
			mInitialized = false;
			return false;
		}
		mMixer = AudioSystem.getMixer(mixers[0]);
		
		mInitialized = true;
		return true;
	}
	
	public static boolean isInitialized() {
		return mInitialized;
	}

   // Prints the currently selected mixer
	public static void PrintMixer() {
		System.out.println(mMixer.toString());
	}

	public static void PrintMaxLines(AudioFormat format) {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		int maxLines = mMixer.getMaxLines(info);

		System.out.println(maxLines);
	}

    // Receives new source data line with suggested AudioFormat
    public static SourceDataLine getSourceLine(AudioFormat format) {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		if(mMixer.isLineSupported(info)) {
			System.out.println("Line unsupported");
			return null;
		}

		SourceDataLine line = null;
		try {
			 line = (SourceDataLine)mMixer.getLine(info);
			 line.open();
		} catch (LineUnavailableException e) {
			 System.out.println("Line Unavailable");
		}        
		return line;
    }

    // Receives new target data line
    public static TargetDataLine getTargetLine(AudioFormat format) {
        return null;
    }
}

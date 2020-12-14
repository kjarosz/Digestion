package com.kjarosz.digestion.sound;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundObject extends Thread {
    private SourceDataLine mOutputLine;
    private AudioInputStream mInputStream;
    private int mBufferSize;
    private byte[] mBuffer;
    private boolean mPlaying;

    // Constructor
    public SoundObject(String filename) {
      mOutputLine = null;
      mInputStream = null;
		mPlaying = false;
		
		if(!SoundSystem.isInitialized()) {
			return;
		}

		// Get file format
      AudioFileFormat format = null;
      try {
			format = AudioSystem.getAudioFileFormat(new File(filename));
      } catch (UnsupportedAudioFileException e) {
			System.out.println("Unsupported audio file");
		} catch (IOException e) {
			System.out.println("getAudioFileFormat IOException");
		}


		if (format != null) {
			// Get Source line and allocate buffer
			mOutputLine = SoundSystem.getSourceLine(format.getFormat());

			if(!(mOutputLine == null) && mOutputLine.isOpen()) {
				mBufferSize = mOutputLine.getBufferSize();
				mBuffer = new byte[mBufferSize];
			} else {
				System.out.println("Output line null or closed");
			}

		//SoundSystem.PrintMaxLines(format.getFormat());

		// Load in a file stream
			try {
				 mInputStream = AudioSystem.getAudioInputStream(new File(filename));
			} catch (UnsupportedAudioFileException e) {
				 System.out.println("Unsupported audio file");
			} catch (IOException e) {
				 System.out.println("getAudioFileFormat IOException");
			}
			
		} else {
			System.out.println("SoundObject: format == null");
		}
		
		start();
   }

    // Check if the object is ready to play. If OutputLine exists and is open.
    public boolean isReady() {
        if(mOutputLine == null || !mOutputLine.isOpen() || mInputStream == null) {
            System.out.println("Not ready");
            return false;
        }
        return true;
    }

    @Override
    // This function keeps pumping the file's bits into the buffer
    public synchronized void run() {		 
		try {
			wait();
			while(true) {
				try {
					int bytesread = mInputStream.read(mBuffer, 0, mBufferSize);
					if(!(bytesread < 0)) {
						mOutputLine.write(mBuffer, 0, bytesread);
					} else {
						mPlaying = false;
						wait();
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
					wait();
				}				
			}
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
	}

	// Start playing the file
	public synchronized boolean Start() {
		if(isReady()) {
			if(!mPlaying) {
				mPlaying = true;
				notify();
				mOutputLine.start();
			}
		} else {
			return false;
		}
		return true;
	}

    // Stop playing the file
	public synchronized boolean Pause() {		
		if(isReady()) {
			if(mPlaying) {
				mPlaying = false;
				mOutputLine.stop();
			}
		}
		return true;
	}

    @Override
    // Destructor
    protected void finalize() throws Throwable {
		 super.finalize();
		 if(isReady())
           mOutputLine.close();
    }
}

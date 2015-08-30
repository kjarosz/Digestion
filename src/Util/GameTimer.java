package Util;

public class GameTimer {
	private long mLastTime;
	
	private boolean mPaused;
	private long mPauseTime;
	
	private long mAccumulator;
	private long mTimestep;
	
	public GameTimer(long fixedTimestep) {
	   mTimestep = fixedTimestep;
	   mPaused = false;
	   reset();
	}

	public void reset() {
		mLastTime = System.nanoTime();
		mAccumulator = 0;
	}
	
	public void pause() {
	   if(!mPaused) {
	      mPauseTime = System.nanoTime();
	      mPaused = true;
	   }
	}
	
	public void start() {
	   if(mPaused) {
	      mAccumulator += mPauseTime - mLastTime;
	      mLastTime = System.nanoTime();
	      mPaused = false;
	   } 
	}
	
	public void updateFrame() {
	   if(!mPaused) {
	      long time = System.nanoTime();
	      mAccumulator += time - mLastTime;
	      mLastTime = time;
	   }
	}
	
	public boolean hasAccumulatedTime() {
	   if(mPaused) {
	      return false;
	   }
	   return mAccumulator > mTimestep;
	}
	
	public long stepTime() {
	   if(mPaused) {
	      return 0;
	   }

	   mAccumulator -= mTimestep;
	   return mTimestep;
	}
	
	public double stepMillisTime() {
	   long nanoTime = stepTime();
	   return nanoToSeconds(nanoTime);
	}
	
	public static double nanoToSeconds(long nanoTime) {
	   return (double)nanoTime/1000000000.0;
	}
	
	public static long secondsToNano(double seconds) {
		return (long)(seconds*1000000000.0);
	}
}

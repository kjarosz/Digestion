package Util;

public class GameTimer {
	private long mLastTime;
	
	private long mTimeInterval;
	private long mIntervalCounter;
	
	
	public GameTimer() {
		mLastTime = System.nanoTime();
	}
	
	public long getLastTime() {
		return mLastTime;
	}
	
	public void setTimeInterval(long milliseconds) {
		mTimeInterval = milliseconds;
	}
	
	public boolean hasTimeIntervalPassed() {
		mIntervalCounter += getElapsedTime();
		
		return mIntervalCounter >= mTimeInterval;
	}
	
	public void reset() {
		mLastTime = System.nanoTime();
		mIntervalCounter = 0;
	}
	
	public long getElapsedTime() {
		long currentTime = System.nanoTime();
		long elapsedTime = currentTime - mLastTime;
		mLastTime = currentTime;
		return elapsedTime;
	}
	
	public static long milliToNano(long milliUnits) {
		return milliUnits/1000000;
	}
}

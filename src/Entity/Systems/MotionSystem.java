package Entity.Systems;

import Level.Level;
import Util.GameTimer;

public class MotionSystem {
	// BitMasks for sensors
	public final static long STAGE         = 1; // Does fixture represent a part of the static stage.
	public final static long AGENT         = 2; // Does the fixture belong to a moving agent like a player or enemy.
	public final static long GROUND_SENSOR = 4;
	
	public final static long NANO_TIMESTEP = (long)(1.0/60.0*1000000000);

	private GameTimer mTimer;

	public MotionSystem() {
	   mTimer = new GameTimer(NANO_TIMESTEP);
	}
	
	public void resetTimer() {
	   mTimer.reset();
	}
	
	public void pauseTimer() {
	   mTimer.pause();
	}
	
	public void startTimer() {
	   mTimer.start();
	}
	   
   public void move(Level level) {
      mTimer.updateFrame();
      while(mTimer.hasAccumulatedTime()) {
         double ms_timestep = mTimer.stepMillisTime();
      }
   }
}

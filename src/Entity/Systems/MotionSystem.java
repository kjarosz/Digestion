package Entity.Systems;

import org.jbox2d.dynamics.World;

import Level.EntityContainer;

public class MotionSystem {
	private static long sLastTime = 0;
   
   public static void move(World box2DWorld, EntityContainer world) {
      
      box2DWorld.step((float)nanoToSeconds(getElapsedTime()), 6, 2);
   }
   
   private static long getElapsedTime() {
   	long elapsedTime = System.nanoTime() - sLastTime;
   	sLastTime += elapsedTime;
   	return elapsedTime;
   }
   
   public static void resetTimer() {
   	sLastTime = System.nanoTime();
   }
   
   private static double nanoToSeconds(double nano) {
   	return nano / 1000000000.0;
   }
}

package Entity.Systems;

import org.jbox2d.dynamics.World;

import Level.EntityContainer;

public class MotionSystem {
   private static final float TIME_STEP = 1.0f / 60.0f;
   private static float sLastTime = 0;
   
   public static void move(World box2DWorld, EntityContainer world) {
      
      float timeStep;
      while((timeStep = getTimeStep()) > 0.0f)
         box2DWorld.step(timeStep, 6, 2);
   }
   
   private static float getTimeStep() {
      if((System.currentTimeMillis() - sLastTime) > TIME_STEP) {
         sLastTime += TIME_STEP;
         return sLastTime;
      } else {
         return 0.0f;
      }
   }
   
   public static void resetTimer() {
      sLastTime = System.currentTimeMillis();
   }
}

package com.kjarosz.digestion.util;

public class RKIntegrator {
   public static Vector2D integrate(double maxVelocity, Vector2D velocity, Vector2D acceleration, double elapsedTime) {
      Vector2D k[] = calculateIntervals(maxVelocity, velocity, acceleration, elapsedTime);

      velocity.set(k[3]);
      
      Vector2D positionShift = new Vector2D(0.0, 0.0);
      return positionShift
            .addLocal(k[0])
            .addLocal(k[1].mul(2))
            .addLocal(k[2].mul(2))
            .addLocal(k[3])
            .mulLocal(elapsedTime)
            .divLocal(6);
   }
   
   private static Vector2D[] calculateIntervals(double maxVelocity, 
                 Vector2D velocity, Vector2D acceleration, double elapsedTime) {
      Vector2D intervals[] = new Vector2D[4];

      intervals[0] = new Vector2D(velocity);
      
      intervals[1] = new Vector2D(velocity);
      intervals[1].addLocal(acceleration.mul(elapsedTime)
                                        .mul(0.5));
      
      intervals[2] = new Vector2D(intervals[1]);
      
      intervals[3] = new Vector2D(velocity);
      intervals[3].addLocal(acceleration.mul(elapsedTime));
      
      for(int i = 0; i < 4; i++)
         clipVector(intervals[i], maxVelocity);
      
      return intervals;
   }
   
   private static void clipVector(Vector2D vector, double maxVelocity) {
      if(Math.abs(vector.x) > maxVelocity)
         vector.x = maxVelocity*(vector.x/Math.abs(vector.x));
      
      if(Math.abs(vector.y) > maxVelocity)
         vector.y = maxVelocity*(vector.y/Math.abs(vector.y));
   }

}

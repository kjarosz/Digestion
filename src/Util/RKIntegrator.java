package Util;

public class RKIntegrator {
   public static Vector2D integrateVelocity(double maxVelocity, Vector2D velocity, Vector2D acceleration, double elapsedTime) {
      Vector2D k[] = new Vector2D[4];
      for(int i = 0; i < 4; i++)
         k[i] = new Vector2D();
      
      calculateIntervals(k, maxVelocity, velocity, acceleration, elapsedTime);
      
      Vector2D positionShift = new Vector2D(0.0, 0.0);
      positionShift.add(k[0]);
      positionShift.add(k[1].multiply(2));
      positionShift.add(k[2].multiply(2));
      positionShift.add(k[3]);
      positionShift.multiply(elapsedTime);
      positionShift.divide(6);
      return positionShift;
   }
   
   private static void calculateIntervals(Vector2D intervals[], double maxVelocity, Vector2D velocity, Vector2D acceleration, double elapsedTime) {
      Vector2D accelCopy = new Vector2D(acceleration);
      
      intervals[0].set(velocity);
      
      intervals[1].set(velocity);
      intervals[1].add(accelCopy.multiply(elapsedTime).multiply(0.5));
      accelCopy.set(acceleration);
      
      intervals[2].set(intervals[1]);
      
      intervals[3].set(velocity);
      intervals[3].add(accelCopy.multiply(elapsedTime));
      
      for(int i = 0; i < 4; i++)
         clipVector(intervals[i], maxVelocity);
      
      velocity.set(intervals[3]);
   }
   
   private static void clipVector(Vector2D vector, double maxVelocity) {
      if(Math.abs(vector.x) > maxVelocity)
         vector.x = maxVelocity*(vector.x/Math.abs(vector.x));
      
      if(Math.abs(vector.y) > maxVelocity)
         vector.y = maxVelocity*(vector.y/Math.abs(vector.y));
   }

}

package Entity.Components;

import Util.Vector2D;

public class Movable {
   public final static double GRAVITY = 1.9;
   
   public double maximumSpeed;
   public Vector2D velocity;
   public Vector2D acceleration;
   public long lastTime;
   
   public Movable() {
      maximumSpeed = -1;
      velocity = new Vector2D(0.0, 0.0);
      acceleration = new Vector2D(0.0, 0.0);
      lastTime = System.currentTimeMillis();
   }
}

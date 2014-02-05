package Entity.Components;

import Util.Vector2D;

public class Movable {
   public final static double GRAVITY = 500;
   
   public boolean jumped;
   public boolean doubleJumped;
   
   public double maximumSpeed;
   public Vector2D velocity;
   public Vector2D acceleration;
   public double mass;
   public double frictionCoefficient;
   public long lastTime;
   
   public Movable() {
      jumped = false;
      doubleJumped = false;
      
      maximumSpeed = 0.0;
      velocity = new Vector2D(0.0, 0.0);
      acceleration = new Vector2D(0.0, 0.0);
      mass = 0.0;
      frictionCoefficient = 0.0;
      lastTime = System.currentTimeMillis();
   }
}

package Entity.Components;

import Util.Vector2D;

public class Movable {
   public Vector2D velocity;
   public Vector2D acceleration;
   public long last_time;
   
   public Movable() {
      velocity = new Vector2D(0.0, 0.0);
      acceleration = new Vector2D(0.0, 0.0);
      last_time = System.currentTimeMillis();
   }
}

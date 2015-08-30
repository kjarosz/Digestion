package Entity.Components;

import Util.Vector2D;

public class Movable {
	public Vector2D velocity;
	public Vector2D netForce;
	public double mass;
   
   public Movable() {
      velocity = new Vector2D(0.0, 0.0);
      netForce = new Vector2D(0.0, 0.0);
      mass = 1.0;
   }
}

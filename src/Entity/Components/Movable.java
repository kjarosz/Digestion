package Entity.Components;

import Util.Vector2D;


public class Movable {
	
	public Vector2D actingForces;
   
   public Movable() {
   	actingForces = new Vector2D(0.0f, 0.0f);
   }
}

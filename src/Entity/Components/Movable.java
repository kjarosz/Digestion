package Entity.Components;

import org.jbox2d.common.Vec2;

public class Movable {
	
	public Vec2 actingForces;
   
   public Movable() {
   	actingForces = new Vec2(0.0f, 0.0f);
   }
}

package Entity.Systems;

import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Level.EntityContainer;

public class MotionSystem {
	// BitMasks for sensors
	public final static long STAGE         = 1; // Does fixture represent a part of the static stage.
	public final static long AGENT         = 2; // Does the fixture belong to a moving agent like a player or enemy.
	public final static long GROUND_SENSOR = 4;
	
	private static long sLastTime = 0;
	   
   public static void move(World box2DWorld, EntityContainer world) {
      
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         if((world.getEntityMask(i) & EntityContainer.ENTITY_MOVABLE) != EntityContainer.ENTITY_MOVABLE)
            continue;
         
         EntityComponents components = world.accessComponents(i);
         components.body.applyForceToCenter(components.movable.actingForces);
      }
      
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

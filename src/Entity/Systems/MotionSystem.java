package Entity.Systems;

import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Level.EntityContainer;
import Level.Level;

public class MotionSystem {
	// BitMasks for sensors
	public final static long STAGE         = 1; // Does fixture represent a part of the static stage.
	public final static long AGENT         = 2; // Does the fixture belong to a moving agent like a player or enemy.
	public final static long GROUND_SENSOR = 4;
	
	private long mLastTime = 0;

	public MotionSystem() {
	   resetTimer();
	}
	   
   public void move(Level level) {
      World world = level.world;
      EntityContainer entityContainer = level.entityContainer;
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         if((entityContainer.getEntityMask(i) & EntityContainer.ENTITY_MOVABLE) != EntityContainer.ENTITY_MOVABLE)
            continue;
         
         EntityComponents components = entityContainer.accessComponents(i);
         components.body.applyForceToCenter(components.movable.actingForces);
      }
      
      world.step((float)nanoToSeconds(getElapsedTime()), 6, 2);
   }
   
   private long getElapsedTime() {
   	long elapsedTime = System.nanoTime() - mLastTime;
   	mLastTime += elapsedTime;
   	return elapsedTime;
   }
   
   public void resetTimer() {
   	mLastTime = System.nanoTime();
   }
   
   private double nanoToSeconds(double nano) {
   	return nano / 1000000000.0;
   }
}

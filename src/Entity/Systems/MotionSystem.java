package Entity.Systems;

import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Level.EntityContainer;
import Level.Level;
import Util.GameTimer;

public class MotionSystem {
	// BitMasks for sensors
	public final static long STAGE         = 1; // Does fixture represent a part of the static stage.
	public final static long AGENT         = 2; // Does the fixture belong to a moving agent like a player or enemy.
	public final static long GROUND_SENSOR = 4;
	
	public final static long NANO_TIMESTEP = (long)(1.0/60.0*1000000000);

	private GameTimer mTimer;

	public MotionSystem() {
	   mTimer = new GameTimer(NANO_TIMESTEP);
	}
	
	public void resetTimer() {
	   mTimer.reset();
	}
	
	public void pauseTimer() {
	   mTimer.pause();
	}
	
	public void startTimer() {
	   mTimer.start();
	}
	   
   public void move(Level level) {
      mTimer.updateFrame();
      while(mTimer.hasAccumulatedTime()) {
         World world = level.world;
         EntityContainer entityContainer = level.entityContainer;
         for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
            if((entityContainer.getEntityMask(i) & EntityContainer.ENTITY_MOVABLE) != EntityContainer.ENTITY_MOVABLE)
               continue;

            EntityComponents components = entityContainer.accessComponents(i);
            components.body.applyForceToCenter(components.movable.actingForces);
         }

         world.step((float)mTimer.stepMillisTime(), 6, 2);
      }
   }
}

package Entity.Systems;

import Entity.EntityComponents;
import Level.EntityContainer;
import Level.Level;
import Util.GameTimer;
import Util.RKIntegrator;
import Util.Vector2D;
import Util.VectorTransform;

public class MotionSystem {
	// BitMasks for sensors
	public final static long STAGE         = 1; // Does fixture represent a part of the static stage.
	public final static long AGENT         = 2; // Does the fixture belong to a moving agent like a player or enemy.
	public final static long GROUND_SENSOR = 4;
	
	public final static long NANO_TIMESTEP = (long)(1.0/60.0*1000000000);

	private GameTimer mTimer;
	
	private Vector2D mGravity;

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
	   
   private boolean isMovable(int mask) {
      return (mask & EntityContainer.ENTITY_MOVABLE) != 0;
   }

   public void move(Level level) {
      mTimer.updateFrame();
      while(mTimer.hasAccumulatedTime()) {
         double ms_timestep = mTimer.stepMillisTime();
         
         mGravity = level.gravity;
         for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
            int entity = level.entityContainer.getEntityMask(i);
            if(isMovable(entity)) {
               step(level.entityContainer, i, ms_timestep);
            }
         }
      }
   }
   
   private void step(EntityContainer container, int eID, double dt) {
      stepAlongAxis(container, eID, dt, (v) -> new Vector2D(v.x, 0.0));
      stepAlongAxis(container, eID, dt, (v) -> new Vector2D(0.0, v.y));
   }
   
   private void stepAlongAxis(EntityContainer container, int eID, double dt, VectorTransform axisFilter) {
      EntityComponents comps = container.accessComponents(eID);

      Vector2D acceleration = new Vector2D(comps.movable.netForce);
      acceleration.divLocal(comps.movable.mass).addLocal(mGravity);
      acceleration = axisFilter.filter(acceleration);
      Vector2D shift = RKIntegrator.integrate(Double.MAX_VALUE, 
            axisFilter.filter(comps.movable.velocity), 
            acceleration, 
            dt);
      comps.movable.velocity.addLocal(acceleration.mul(dt));
      comps.tangible.position.addLocal(shift);
      
   }
}

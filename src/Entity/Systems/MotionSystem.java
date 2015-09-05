package Entity.Systems;

import Entity.EntityComponents;
import Entity.Components.Body;
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
	   
	
	private final VectorTransform x_filter = (v) -> new Vector2D(v.x, 0.0);
	private final VectorTransform y_filter = (v) -> new Vector2D(0.0, v.y);
   
   
   private Vector2D mGravity;
   private EntityContainer mContainer;
   private EntityComponents mEntity;
   private EntityComponents mOtherEntity;
   
   private boolean isMovable(int eID) {
      return (mContainer.getEntityMask(eID) & EntityContainer.ENTITY_MOVABLE) != 0;
   }

   private boolean entityCollidable(int id) {
      return (mContainer.getEntityMask(id) & EntityContainer.ENTITY_COLLIDABLE) != 0; 
   }

   public void move(Level level) {
      mTimer.updateFrame();
      while(mTimer.hasAccumulatedTime()) {
         double ms_timestep = mTimer.stepMillisTime();
         
         mGravity = level.gravity;
         mContainer = level.entityContainer;
         for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
            if(isMovable(i)) {
               step(i, ms_timestep);
            }
         }
      }
   }
   
   private void step(int eID, double dt) {
      mEntity = mContainer.accessComponents(eID);
      stepAlongXAxis(eID, dt);
      stepAlongYAxis(eID, dt);
   }
   
   private void stepAlongXAxis(int eID, double dt) {
      Vector2D shift = RKIntegrator.integrate(mEntity.movable.terminalVelocity,
            x_filter.filter(mEntity.movable.velocity),
            Vector2D.ZERO_VECTOR,
            dt);
      mEntity.body.position.addLocal(shift);
      
      if(!shift.equals(Vector2D.ZERO_VECTOR) && entityCollidable(eID)) {
         resolveCollisions(eID, shift, x_filter, false);
      }
   }
   
   private void stepAlongYAxis(int eID, double dt) {
      Vector2D acceleration = new Vector2D(mEntity.movable.netForce);
      acceleration.divLocal(mEntity.movable.mass);
      if(!mEntity.movable.ignoreGravity) {
         acceleration.addLocal(mGravity);
      }
      acceleration = y_filter.filter(acceleration);
      Vector2D shift = RKIntegrator.integrate(mEntity.movable.terminalVelocity,
            y_filter.filter(mEntity.movable.velocity), 
            acceleration, 
            dt);
      mEntity.movable.velocity.addLocal(acceleration.mul(dt));
      mEntity.body.position.addLocal(shift);
      
      if(!shift.equals(Vector2D.ZERO_VECTOR) && entityCollidable(eID)) {
         resolveCollisions(eID, shift, y_filter, true);
      }
   }
   
   private void resolveCollisions(int eID, Vector2D dx, VectorTransform axisFilter, boolean clearVelocity) {
      boolean collisionFound;
      do {
         collisionFound = false;
         for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
            if(i == eID || !entityCollidable(i)) {
               continue;
            }

            mOtherEntity = mContainer.accessComponents(i);
            if(entitiesCollide()) {
               collisionFound = true;
               moveOutOfCollision(dx);
               if(clearVelocity) {
                  mEntity.movable.velocity.subLocal(
                        axisFilter.filter(mEntity.movable.velocity));
               }
            }
         }
      } while(collisionFound);
   }
   
   private boolean entitiesCollide() {
      Body entBody = mEntity.body;
      Body otherEntBody = mOtherEntity.body;
      if(entBody.position.x >= otherEntBody.position.x + otherEntBody.size.x) 
         return false;
      
      if(entBody.position.x + entBody.size.x <= otherEntBody.position.x)
         return false;
      
      if(entBody.position.y >= otherEntBody.position.y + otherEntBody.size.y)
         return false;
      
      if(entBody.position.y + entBody.size.y <= otherEntBody.position.y) 
         return false;
      
      return true;
   }
   
   private void moveOutOfCollision(Vector2D dx) {
      Body body = mEntity.body;
      Body oBody = mOtherEntity.body;
      if(dx.x > 0) {
         body.position.x = oBody.position.x - body.size.x;
      } else if(dx.x < 0) {
         body.position.x = oBody.position.x + oBody.size.x;
      }
      
      if(dx.y > 0) {
         body.position.y = oBody.position.y - body.size.y;
      } else if(dx.y < 0) {
         body.position.y = oBody.position.y + oBody.size.y;
      }
   }
}

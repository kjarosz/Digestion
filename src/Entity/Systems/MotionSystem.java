package Entity.Systems;

import java.awt.geom.Rectangle2D;

import Entity.EntityComponents;
import Entity.Components.Movable;
import Entity.Components.Position;
import Level.World;
import Util.Vector2D;

public class MotionSystem {
   private static final int PROJECT_LEFT = 0;
   private static final int PROJECT_RIGHT = 1;
   private static final int PROJECT_UP = 2;
   private static final int PROJECT_DOWN = 3;
   
   private static final double MAXIMUM_TIME_STEP = 1.0/60.0;
   
   public static void move(World world) {
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if(entityIsMovable(entity))
            moveEntity(i, world);
      }
   }
   
   private static boolean entityIsMovable(int entity) {
      return (entity & World.ENTITY_MOVABLE) != 0;
   }
   
   private static boolean entityIsCollidable(int entity) {
      return (entity & World.ENTITY_COLLIDABLE) != 0;
   }
   
   private static void moveEntity(int id, World world) {
      boolean collidable = entityIsCollidable(world.getEntityMask(id));
      
      Movable movable = world.accessComponents(id).movable;
      double deltaTime = getDeltaTime(movable);
      while(deltaTime > 0.0) {
         double timeStep = getTimeStep(deltaTime);
         deltaTime -= timeStep;

         Vector2D posShift = integrateVelocity(movable.maximumSpeed, movable.velocity, movable.acceleration, timeStep);
         
         if(collidable)
            performCollisionCheckingAndResponse(posShift, id, world);
         
         Position position = world.accessComponents(id).position;
         position.x += posShift.x;
         position.y += posShift.y;
      }
   }
   
   private static double getDeltaTime(Movable movable) {
      long time = System.currentTimeMillis();
      double deltaTime = ((double)(time - movable.lastTime))/1000.0;
      movable.lastTime = time;
      return deltaTime;
   }
   
   private static double getTimeStep(double timeFrame) {
      if(timeFrame > MAXIMUM_TIME_STEP) 
         return MAXIMUM_TIME_STEP;
      else 
         return timeFrame;
   }
   
   private static Vector2D integrateVelocity(double maxVelocity, Vector2D velocity, Vector2D acceleration, double elapsedTime) {
      Vector2D k[] = new Vector2D[4];
      for(int i = 0; i < 4; i++)
         k[i] = new Vector2D();
      
      calculateIntervals(k, maxVelocity, velocity, acceleration, elapsedTime);
      
      Vector2D positionShift = new Vector2D(0.0, 0.0);
      positionShift.add(k[0]);
      positionShift.add(k[1].multiply(2));
      positionShift.add(k[2].multiply(2));
      positionShift.add(k[3]);
      positionShift.multiply(elapsedTime);
      positionShift.divide(6);
      return positionShift;
   }
   
   private static void calculateIntervals(Vector2D intervals[], double maxVelocity, Vector2D velocity, Vector2D acceleration, double elapsedTime) {
      Vector2D accelCopy = new Vector2D(acceleration);
      
      intervals[0].set(velocity);
      
      intervals[1].set(velocity);
      intervals[1].add(accelCopy.multiply(elapsedTime).multiply(0.5));
      accelCopy.set(acceleration);
      
      intervals[2].set(intervals[1]);
      
      intervals[3].set(velocity);
      intervals[3].add(accelCopy.multiply(elapsedTime));
      
      for(int i = 0; i < 4; i++)
         clipVector(intervals[i], maxVelocity);
      
      velocity.set(intervals[3]);
   }
   
   private static void clipVector(Vector2D vector, double maxVelocity) {
      if(Math.abs(vector.x) > maxVelocity)
         vector.x = maxVelocity*(vector.x/Math.abs(vector.x));
      
      if(Math.abs(vector.y) > maxVelocity)
         vector.y = maxVelocity*(vector.y/Math.abs(vector.y));
   }
   
   private static void performCollisionCheckingAndResponse(Vector2D positionShift, int id, World world) {
      EntityComponents entityComponents = world.accessComponents(id);
      Rectangle2D.Double entity = createBoundingRectangle(entityComponents);
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int entityMask = world.getEntityMask(i);
         if(i == id || !entityIsCollidable(entityMask))
            continue;
         
         Rectangle2D.Double otherEntity = createBoundingRectangle(world.accessComponents(i));
         if(entity.intersects(otherEntity))
            respondToCollision(positionShift, entityComponents, world.accessComponents(i));
      }
      
      entityComponents.position.x += positionShift.x;
      entityComponents.position.y += positionShift.y;
   }

   private static Rectangle2D.Double createBoundingRectangle(EntityComponents components) {
      Rectangle2D.Double rect = new Rectangle2D.Double();
      rect.x = components.position.x;
      rect.y = components.position.y;
      if(components.collidable.bindToImageDimensions) {
         rect.width = components.drawable.image.getWidth();
         rect.height = components.drawable.image.getHeight();
      } else {
         rect.width = components.collidable.width;
         rect.height = components.collidable.height;
      }
      return rect;
   }
   
   private static void respondToCollision(Vector2D positionShift, EntityComponents entity, EntityComponents otherEntity) {
      Rectangle2D.Double mover = createBoundingRectangle(entity);
      int shortestProjection = projectOufOfCollision(mover, createBoundingRectangle(otherEntity));
      if(shortestProjection == PROJECT_LEFT || shortestProjection == PROJECT_RIGHT) {
         entity.movable.velocity.x = 0.0;
         positionShift.x = mover.x - entity.position.x;
      } else if(shortestProjection == PROJECT_UP || shortestProjection == PROJECT_DOWN) {
         entity.movable.velocity.y = 0.0;
         positionShift.y = mover.y - entity.position.y;
      }
   }
   
   private static int projectOufOfCollision(Rectangle2D.Double mover, Rectangle2D.Double obstacle) {
      double projectionDistances[] = new double[4];
      calculateProjectionDistances(projectionDistances, mover, obstacle);
      int shortestProjection = findShortestProjection(projectionDistances);
      projectInShortestDistance(mover, projectionDistances, shortestProjection);
      return shortestProjection;
   }
   
   private static void calculateProjectionDistances(double distances[], Rectangle2D.Double mover, Rectangle2D.Double obstacle) {
      distances[PROJECT_LEFT]   = Math.abs((mover.x + mover.width) - obstacle.x);
      distances[PROJECT_RIGHT]  = Math.abs(mover.x - (obstacle.x + obstacle.width));
      distances[PROJECT_UP]     = Math.abs((mover.y + mover.height) - obstacle.y);
      distances[PROJECT_DOWN]   = Math.abs(mover.y - (obstacle.y + obstacle.height));
   }
   
   private static int findShortestProjection(double projections[]) {
      int shortestProjection = 0;
      for(int i = 1; i < 4; i++) {
         if(projections[i] < projections[shortestProjection]) {
            shortestProjection = i;
         }
      }
      return shortestProjection;
   }
   
   private static void projectInShortestDistance(Rectangle2D.Double mover, double projections[], int shortestProjection) {
      switch(shortestProjection) {
         case PROJECT_LEFT:
            mover.x -= projections[shortestProjection];
            break;
         case PROJECT_RIGHT:  
            mover.x += projections[shortestProjection]; 
            break;
         case PROJECT_UP:
            mover.y -= projections[shortestProjection];
            break;
         case PROJECT_DOWN:   
            mover.y += projections[shortestProjection]; 
            break;
      }
   }
}

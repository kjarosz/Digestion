package Entity.Systems;

import java.awt.geom.Rectangle2D;

import Entity.EntityComponents;
import Entity.Components.Movable;
import Entity.Components.Position;
import Level.World;
import Util.BoundingRectangle;
import Util.RKIntegrator;
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
   
   private static void moveEntity(int id, World world) {
      Movable movable = world.accessComponents(id).movable;
      double deltaTime = getDeltaTime(movable);
      while(deltaTime > 0.0) {
         double timeStep = getTimeStep(deltaTime);
         deltaTime -= timeStep;

         Vector2D posShift = RKIntegrator.integrateVelocity(movable.maximumSpeed, movable.velocity, movable.acceleration, timeStep);
         
         if(entityIsCollidable(world.getEntityMask(id)))
            performCollisionCheckingAndResponse(posShift, id, world);
         
         Position position = world.accessComponents(id).position;
         position.x += posShift.x;
         position.y += posShift.y;
      }
   }
   
   private static boolean entityIsCollidable(int entity) {
      return (entity & World.ENTITY_COLLIDABLE) != 0;
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
   
   private static void performCollisionCheckingAndResponse(Vector2D positionShift, int id, World world) {
      BoundingRectangle sourceEntity = new BoundingRectangle(id, world);
      BoundingRectangle entity = new BoundingRectangle(id, world);
      applyPositionShift(sourceEntity, positionShift, entity);
      
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         if(i == id || !entityIsCollidable(world.getEntityMask(i)))
            continue;

         if(entitiesIntersect(entity, i, world)) {
            respondToCollision(positionShift, world.accessComponents(id), world.accessComponents(i));
            applyPositionShift(sourceEntity, positionShift, entity);
            i = 0;
         }
      }
   }
   
   private static void applyPositionShift(BoundingRectangle source, Vector2D positionShift, BoundingRectangle object) {
      object.x = source.x + positionShift.x;
      object.y = source.y + positionShift.y;
   }
   
   private static boolean entitiesIntersect(BoundingRectangle entity, int id2, World world) {
      EntityComponents otherEntityComponents = world.accessComponents(id2);
      BoundingRectangle otherEntity = new BoundingRectangle(otherEntityComponents);
      
      return entity.intersects(otherEntity);
   }
   
   private static void respondToCollision(Vector2D positionShift, EntityComponents entity, EntityComponents otherEntity) {
      BoundingRectangle projectedEntity = new BoundingRectangle(entity);
      BoundingRectangle otherEntityBox = new BoundingRectangle(otherEntity);
      
      int projection = projectOufOfCollision(projectedEntity, otherEntityBox);
      
      applyCollisionForces(projection, positionShift, entity, projectedEntity);
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
   
   private static void applyCollisionForces(int projection, Vector2D positionShift, EntityComponents entity, Rectangle2D.Double projectedEntity) {
      switch(projection) {
         case PROJECT_LEFT:
         case PROJECT_RIGHT:
            entity.movable.velocity.x = 0.0;
            positionShift.x = projectedEntity.x - entity.position.x;
            break;
         case PROJECT_UP:
         case PROJECT_DOWN:
            entity.movable.velocity.y = 0.0;
            positionShift.y = projectedEntity.y - entity.position.y;
            break;
      }
   }
}

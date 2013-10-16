package Entity.Systems;

import Entity.Components.Movable;
import Entity.EntityComponents;
import Level.World;
import Util.Vector2D;

public class MotionSystem {
   public static void move(World world) {
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if(entityIsMovable(entity))
            if(entityIsCollidable(entity))
               moveEntityWithCollision(world.accessComponents(i), world);
            else
               moveEntityWithoutCollision(world.accessComponents(i));
      }
   }
   
   private static boolean entityIsMovable(int entity) {
      return (entity & World.ENTITY_MOVABLE) != 0;
   }
   
   private static boolean entityIsCollidable(int entity) {
      return (entity & World.ENTITY_COLLIDABLE) != 0;
   }
   
   private static void moveEntityWithCollision(EntityComponents components, World world) {
      long deltaTime = getDeltaTime(components.movable);
      Vector2D posShift = integrateVelocity(components.movable, deltaTime);
      if(Math.abs(posShift.x) != 0.0)
         System.out.println("Done");
      components.position.x += posShift.x;
      components.position.y += posShift.y;
   }
   
   private static void moveEntityWithoutCollision(EntityComponents components) {
      long deltaTime = getDeltaTime(components.movable);
      Vector2D posShift = integrateVelocity(components.movable, deltaTime);
      components.position.x += posShift.x;
      components.position.y += posShift.y;
   }
   
   private static long getDeltaTime(Movable movable) {
      long time = System.currentTimeMillis();
      long deltaTime = time - movable.last_time;
      movable.last_time = time;
      return deltaTime;
   }
   
   private static Vector2D integrateAcceleration(Vector2D acceleration, long deltaTime) {
		return new Vector2D(acceleration.x*deltaTime, acceleration.y*deltaTime);
   }
   
   private static Vector2D integrateVelocity(Movable movable, long deltaTime) {
		// Using Runge Kutta 4 (I hope)		
		Vector2D positionShift = new Vector2D(0.0, 0.0);
      Vector2D acceleration = new Vector2D(movable.acceleration);
		Vector2D velocity = new Vector2D(movable.velocity);
		
		positionShift.add(velocity);
		
		velocity.add(integrateAcceleration(acceleration, (int)(deltaTime/2.0)));
		
		velocity.multiply(2);
		positionShift.add(velocity);
		velocity.divide(2);
		
		velocity.add(integrateAcceleration(acceleration, (int)(deltaTime/2.0)));
		
		positionShift.add(velocity);		
		positionShift.multiply(deltaTime/6);
		
		velocity.set(velocity);
		
		return positionShift;
   }
}

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
      components.position.x += (components.movable.velocity.x/1000.0) * deltaTime;
      components.position.y += (components.movable.velocity.y/1000.0) * deltaTime;
   }
   
   private static void moveEntityWithoutCollision(EntityComponents components) {
      long deltaTime = getDeltaTime(components.movable);
   }
   
   private static long getDeltaTime(Movable movable) {
      long time = System.currentTimeMillis();
      long deltaTime = time - movable.last_time;
      movable.last_time = time;
      return deltaTime;
   }
}

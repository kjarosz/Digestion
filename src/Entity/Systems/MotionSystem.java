package Entity.Systems;

import Entity.Components.Movable;
import Entity.EntityComponents;
import Level.World;
import Util.Vector2D;
import java.awt.geom.Rectangle2D;

public class MotionSystem {
   public static void move(World world) {
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if(entityIsMovable(entity))
            if(entityIsCollidable(entity))
               moveEntityWithCollision(i, world.accessComponents(i), world);
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
   
   private static void moveEntityWithCollision(int id, EntityComponents components, World world) {
      long deltaTime = getDeltaTime(components.movable);
      Vector2D posShift = new Vector2D();
      posShift.x = (components.movable.velocity.x/1000.0) * deltaTime;
      posShift.y = (components.movable.velocity.y/1000.0) * deltaTime;
      
      Rectangle2D.Double entity = new Rectangle2D.Double();
      createBoundingRectangle(entity, components);
      entity.x += posShift.x;
      entity.y += posShift.y;
      
      Rectangle2D.Double otherEntity = new Rectangle2D.Double();
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         if(i == id)
            continue;
         
         int entityMask = world.getEntityMask(i);
         if(!entityIsCollidable(entityMask))
            continue;
         
         EntityComponents otherComponents = world.accessComponents(i);
         createBoundingRectangle(otherEntity, otherComponents);
         
         if(entity.intersects(otherEntity)) {
            return;
         }
      }
      
      components.position.x += posShift.x;
      components.position.y += posShift.y;
   }
   
   private static void createBoundingRectangle(Rectangle2D.Double rect, EntityComponents components) {
      rect.x = components.position.x;
      rect.y = components.position.y;
      if(components.collidable.bindToImageDimensions) {
         rect.width = components.drawable.image.getWidth();
         rect.height = components.drawable.image.getHeight();
      } else {
         rect.width = components.collidable.width;
         rect.height = components.collidable.height;
      }
   }
   
   private static void moveEntityWithoutCollision(EntityComponents components) {
      long deltaTime = getDeltaTime(components.movable);
      components.position.x += (components.movable.velocity.x/1000.0) * deltaTime;
      components.position.y += (components.movable.velocity.y/1000.0) * deltaTime;
   }
   
   private static long getDeltaTime(Movable movable) {
      long time = System.currentTimeMillis();
      long deltaTime = time - movable.last_time;
      movable.last_time = time;
      return deltaTime;
   }
   
   
}

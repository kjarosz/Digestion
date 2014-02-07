package Entity.Systems;

import Entity.EntityComponents;
import Level.EntityContainer;

public class AnimationSystem {
   public static void animate(EntityContainer world) {
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if(entityIsAnimated(entity))
            animateEntity(world.accessComponents(i));
      }
   }
   private static boolean entityIsAnimated(int entity) {
      return (entity & EntityContainer.ENTITY_ANIMATED) != 0;
   }
   
   private static void animateEntity(EntityComponents components) {
      
   }
}

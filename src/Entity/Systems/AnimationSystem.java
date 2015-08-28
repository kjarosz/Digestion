package Entity.Systems;

import Entity.EntityComponents;
import Level.EntityContainer;
import Level.Level;

public class AnimationSystem {
   public static void animate(Level level) {
      EntityContainer entityContainer = level.entityContainer;
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int entity = entityContainer.getEntityMask(i);
         if(entityIsAnimated(entity))
            animateEntity(entityContainer.accessComponents(i));
      }
   }
   private static boolean entityIsAnimated(int entity) {
      return (entity & EntityContainer.ENTITY_ANIMATED) != 0;
   }
   
   private static void animateEntity(EntityComponents components) {
      
   }
}

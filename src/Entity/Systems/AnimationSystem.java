package Entity.Systems;

import Entity.EntityComponents;
import Level.World;

public class AnimationSystem {
   public static void animate(World world) {
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if(entityIsAnimated(entity))
            animateEntity(world.accessComponents(i));
      }
   }
   private static boolean entityIsAnimated(int entity) {
      return (entity & World.ENTITY_ANIMATED) != 0;
   }
   
   private static void animateEntity(EntityComponents components) {
      
   }
}

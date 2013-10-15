package Entity.Systems;

import Entity.EntityComponents;
import Level.World;

public class ControlSystem {
   public static void manipulate(World world) {
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if(isControllable(entity)) {
            manipulateEntity(world.accessComponents(i));
         }
      }  
   }
   
   private static boolean isControllable(int entity) {
      return (entity & World.ENTITY_CONTROLLABLE) != 0;
   }
   
   private static void manipulateEntity(EntityComponents entity) {
      
   }
}

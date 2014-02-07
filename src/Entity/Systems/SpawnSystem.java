package Entity.Systems;

import Entity.Components.Spawner;
import Level.EntityContainer;

public class SpawnSystem {
   public void spawn(EntityContainer world) {
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int mask = world.getEntityMask(i);
         if((mask & EntityContainer.ENTITY_SPAWNER) > 0) {
            activateSpawn(world, i);
         }
      }
   }
   
   private void activateSpawn(EntityContainer world, int entityID) {
      Spawner spawner = world.accessComponents(entityID).spawner;
   }
}

package Entity.Systems;

import Entity.Components.Spawner;
import Level.World;

public class SpawnSystem {
   public void spawn(World world) {
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int mask = world.getEntityMask(i);
         if((mask & World.ENTITY_SPAWNER) > 0) {
            activateSpawn(world, i);
         }
      }
   }
   
   private void activateSpawn(World world, int entityID) {
      Spawner spawner = world.accessComponents(entityID).spawner;
   }
}

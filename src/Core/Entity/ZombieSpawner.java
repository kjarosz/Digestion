package Core.Entity;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Level.EntityContainer;

public class ZombieSpawner extends EntitySpawner {
   @Override
   public int spawn(EntityComponents components) {
      int entityMask = EntityContainer.ENTITY_NONE;
      
      entityMask = makeSpawner();
      
      return entityMask;
   }
   
   private int makeSpawner() {
      
      return EntityContainer.ENTITY_SPAWNER;
   }
}

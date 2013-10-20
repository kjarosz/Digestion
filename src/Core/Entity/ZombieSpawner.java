package Core.Entity;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Level.World;

public class ZombieSpawner extends EntitySpawner {
   @Override
   public int spawn(EntityComponents components) {
      int entityMask = World.ENTITY_NONE;
      
      return entityMask;
   }
}

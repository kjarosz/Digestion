package Core.Entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Level.EntityContainer;

public class ZombieSpawner extends EntitySpawner {
   @Override
   public int spawn(World world, Vec2 position, EntityComponents components) {
      int entityMask = EntityContainer.ENTITY_NONE;
      
      entityMask = makeSpawner();
      
      return entityMask;
   }
   
   private int makeSpawner() {
      
      return EntityContainer.ENTITY_SPAWNER;
   }
}

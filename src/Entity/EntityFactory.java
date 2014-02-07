package Entity;

import java.awt.geom.Point2D;

import Core.Entity.BreakingBlockSpawner;
import Core.Entity.IceBlockSpawner;
import Core.Entity.NormalBlockSpawner;
import Core.Entity.PlayerSpawner;
import Core.Entity.SpikeyBlockSpawner;
import Core.Entity.ZombieSpawner;
import Level.EntityContainer;

public class EntityFactory {
   final private String ENTITY_NAMES[] = {
      "Breaking Block", "Ice Block", "Normal Block",
      "Player", "Spikey Block", "Zombie" 
   };

   final private EntitySpawner ENTITY_SPAWNERS[] = { 
      new BreakingBlockSpawner(), new IceBlockSpawner(), new NormalBlockSpawner(),
      new PlayerSpawner(), new SpikeyBlockSpawner(), new ZombieSpawner()
   };
   
   public String[] getEntityNames() {
      return ENTITY_NAMES;
   }
   
   public int createEntity(String name, Point2D.Double position, EntityComponents components) {
      for(int i = 0; i < ENTITY_NAMES.length; i++) {
         if(name.equals(ENTITY_NAMES[i])) {
            components.name = name;
            components.position.x = position.x;
            components.position.y = position.y;
            return ENTITY_SPAWNERS[i].spawn(components);
         }
      }
      return EntityContainer.ENTITY_NONE;
   }
}

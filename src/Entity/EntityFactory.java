package Entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

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
   
   public int createEntity(World world, String name, Vec2 position, EntityComponents components) {
      for(int i = 0; i < ENTITY_NAMES.length; i++) {
         if(name.equals(ENTITY_NAMES[i])) {
            components.name = name;
            return ENTITY_SPAWNERS[i].spawn(world, position, components);
         }
      }
      return EntityContainer.ENTITY_NONE;
   }
}

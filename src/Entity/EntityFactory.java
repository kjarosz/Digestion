package Entity;

import Core.Entity.BreakingBlockSpawner;
import Core.Entity.IceBlockSpawner;
import Core.Entity.NormalBlockSpawner;
import Core.Entity.PlayerSpawner;
import Core.Entity.SpikeyBlockSpawner;
import Level.EntityContainer;
import Util.Vector2D;

public class EntityFactory {
   private static EntityFactory sInstance;
   
   private EntityFactory() {}
   
   public static EntityFactory getInstance() {
      if(sInstance == null) {
         sInstance = new EntityFactory();
      }
      return sInstance;
   }
   
   final private String ENTITY_NAMES[] = {
      "Breaking Block", "Ice Block", "Normal Block",
      "Player", "Spikey Block"
   };

   final private EntitySpawner ENTITY_SPAWNERS[] = { 
      new BreakingBlockSpawner(), new IceBlockSpawner(), new NormalBlockSpawner(),
      new PlayerSpawner(), new SpikeyBlockSpawner()
   };
   
   public String[] getEntityNames() {
      return ENTITY_NAMES;
   }
   
   public int createEntity(String name, Vector2D position, 
                           Vector2D size, EntityComponents components) 
   {
      for(int i = 0; i < ENTITY_NAMES.length; i++) {
         if(name.equals(ENTITY_NAMES[i])) {
            components.name = name;
            return ENTITY_SPAWNERS[i].spawn(position, size, components);
         }
      }
      return EntityContainer.ENTITY_NONE;
   }
}

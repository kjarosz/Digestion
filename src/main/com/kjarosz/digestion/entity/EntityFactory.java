package com.kjarosz.digestion.entity;

import com.kjarosz.digestion.core.entity.BreakingBlockSpawner;
import com.kjarosz.digestion.core.entity.IceBlockSpawner;
import com.kjarosz.digestion.core.entity.NormalBlockSpawner;
import com.kjarosz.digestion.core.entity.PlayerSpawner;
import com.kjarosz.digestion.core.entity.SpikeyBlockSpawner;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.util.Vector2D;

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

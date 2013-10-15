package Entity;

import Core.Entity.BreakingBlockSpawner;
import Core.Entity.PlayerSpawner;
import Level.World;
import java.awt.geom.Point2D;

public class EntityFactory {
   final private String ENTITY_NAMES[] = { 
      "Player", "Breaking Block" 
   };

   final private EntitySpawner ENTITY_SPAWNERS[] = { 
      new PlayerSpawner(), new BreakingBlockSpawner() 
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
      return World.ENTITY_NONE;
   }
}

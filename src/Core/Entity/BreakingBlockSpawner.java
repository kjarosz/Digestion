package Core.Entity;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Systems.DrawingSystem;
import Level.World;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BreakingBlockSpawner extends EntitySpawner {
   @Override
   public int spawn(EntityComponents components) {
      int mask = World.ENTITY_NONE;
      mask |= setCollidable(components);
      mask |= setDrawable(components);
      return mask;
   }
   
   private int setCollidable(EntityComponents components) {
      components.collidable.width = 32;
      components.collidable.height = 32;
      return World.ENTITY_COLLIDABLE;
   }
   
   private int setDrawable(EntityComponents components) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Scripts" + File.separator 
                              + "Breaking Block" + File.separator 
                              + "BreakingBlock1.gif");
         components.drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         components.drawable.image = DrawingSystem.getNullImage();
      }
      return World.ENTITY_DRAWABLE;
   }
}

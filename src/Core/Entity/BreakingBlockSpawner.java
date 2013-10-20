package Core.Entity;

import Entity.Components.Collidable;
import Entity.Components.Drawable;
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
      mask |= setCollidable(components.collidable);
      mask |= setDrawable(components.drawable);
      return mask;
   }
   
   private int setCollidable(Collidable collidable) {
      collidable.width = 32;
      collidable.height = 32;
      return World.ENTITY_COLLIDABLE;
   }
   
   private int setDrawable(Drawable drawable) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Images" + File.separator 
                              + "Breaking Block" + File.separator 
                              + "BreakingBlock1.gif");
         drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return World.ENTITY_DRAWABLE;
   }
}

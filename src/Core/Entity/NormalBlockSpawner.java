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

public class NormalBlockSpawner extends EntitySpawner {
   @Override
   public int spawn(EntityComponents components) {
      int entityMask = World.ENTITY_NONE;
      entityMask |= makeCollidable(components.collidable);
      entityMask |= makeDrawable(components.drawable);
      return entityMask;
   }
   
   private int makeCollidable(Collidable collidable) {
      collidable.bindToImageDimensions = true;
      return World.ENTITY_COLLIDABLE;
   }
   
   private int makeDrawable(Drawable drawable) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Images" + File.separator 
                              + "Normal Block" + File.separator 
                              + "Block.gif");
         drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return World.ENTITY_DRAWABLE;
   }
}

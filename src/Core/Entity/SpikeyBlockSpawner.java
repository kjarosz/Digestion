package Core.Entity;

import Entity.Components.Collidable;
import Entity.Components.Drawable;
import Entity.Components.Movable;
import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Systems.DrawingSystem;
import Level.EntityContainer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpikeyBlockSpawner extends EntitySpawner {

   @Override
   public int spawn(EntityComponents components) {
      int entityMask = EntityContainer.ENTITY_NONE;
      entityMask |= makeCollidable(components.collidable);
      entityMask |= makeMovable(components.movable);
      entityMask |= makeDrawable(components.drawable);
      entityMask |= makeTriggerable(); // Triggered by a distant switch
      return entityMask;
   }

   private int makeCollidable(Collidable collidable) {
      collidable.bindToImageDimensions = true;
      return EntityContainer.ENTITY_COLLIDABLE;
   }
   
   private int makeMovable(Movable movable) {
      movable.acceleration.x = 0.0;
      movable.acceleration.y = 0.0;
      movable.velocity.x = 0.0;
      movable.velocity.y = 0.0;
      movable.lastTime = System.currentTimeMillis();
      return EntityContainer.ENTITY_MOVABLE;
   }
   
   private int makeDrawable(Drawable drawable) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Images" + File.separator 
                              + "Spike Block" + File.separator 
                              + "SpikeBlock.gif");
         drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return EntityContainer.ENTITY_DRAWABLE;
   }
   
   private int makeTriggerable() {
      return EntityContainer.ENTITY_TRIGGERABLE;
   }
}

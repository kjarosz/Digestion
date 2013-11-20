package Core.Entity;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Collidable;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Entity.Systems.DrawingSystem;
import Level.World;

public class BreakingBlockSpawner extends EntitySpawner {
   @Override
   public int spawn(EntityComponents components) {
      int mask = World.ENTITY_NONE;
      mask |= setCollidable(components.collidable);
      mask |= setDestructible(components.destructible);
      mask |= setDrawable(components.drawable);
      return mask;
   }
   
   private int setCollidable(Collidable collidable) {
      collidable.width = 32;
      collidable.height = 32;
      return World.ENTITY_COLLIDABLE;
   }
   
   private int setDestructible(Destructible destructible) {
      destructible.health = 100;
      destructible.maxHealth = 100;
      destructible.selfReviving = true;
      destructible.revivalInterval = 2000;
      return World.ENTITY_DESTRUCTIBLE;
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

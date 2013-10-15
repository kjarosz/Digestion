package Core.Entity;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.*;
import Entity.Systems.DrawingSystem;
import Level.World;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerSpawner extends EntitySpawner {
   @Override
   public int spawn(EntityComponents components) {
      int mask = World.ENTITY_NONE;
      mask |= setCollidable(components.collidable);
      mask |= setMovable(components.movable);
      mask |= setControllable(components.controllable);
      mask |= setDrawable(components.drawable);
      return   mask;
   }
   
   private int setCollidable(Collidable collidable) {
      collidable.width = 32;
      collidable.height = 64;
      return World.ENTITY_COLLIDABLE;
   }
   
   private int setMovable(Movable movable) {
      movable.acceleration.x = 0.0;
      movable.acceleration.y = 0.0;
      movable.velocity.x = 0.0;
      movable.velocity.y = 0.0;
      movable.last_time = System.currentTimeMillis();
      return World.ENTITY_COLLIDABLE;
   }
   
   private int setControllable(Controllable controllable) {
      controllable.moveDownKey = KeyEvent.VK_DOWN;
      controllable.moveLeftKey = KeyEvent.VK_LEFT;
      controllable.moveUpKey = KeyEvent.VK_UP;
      controllable.moveRightKey = KeyEvent.VK_RIGHT;
      return World.ENTITY_CONTROLLABLE;
   }
   
   private int setDrawable(Drawable drawable) {
      try {
         drawable.image = ImageIO.read(new File("Players" + File.separator + "Gordon" + File.separator + "standing.gif"));
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return World.ENTITY_DRAWABLE;
   }
}

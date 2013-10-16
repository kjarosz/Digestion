package Core.Entity;

import Entity.Components.Collidable;
import Entity.Components.Controllable;
import Entity.Components.Drawable;
import Entity.Components.Movable;
import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Systems.DrawingSystem;
import Input.ControlFunction;
import Input.KeyMapping;
import Level.World;
import Util.Vector2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerSpawner extends EntitySpawner {  
   final Vector2D LEFT_VELOCITY = new Vector2D(-5, 0);
   final Vector2D UP_VELOCITY = new Vector2D(0, 5);
   final Vector2D RIGHT_VELOCITY = new Vector2D(5, 0);
   final Vector2D DOWN_VELOCITY = new Vector2D(0, -5); 
   
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
      constructMovingKeyMapping(controllable, KeyEvent.VK_A, LEFT_VELOCITY);      
      constructMovingKeyMapping(controllable, KeyEvent.VK_W, UP_VELOCITY);
      constructMovingKeyMapping(controllable, KeyEvent.VK_D, RIGHT_VELOCITY);
      constructMovingKeyMapping(controllable, KeyEvent.VK_S, DOWN_VELOCITY);
      
      return World.ENTITY_CONTROLLABLE;
   }
   
   private void constructMovingKeyMapping(Controllable controllable, int keyCode, final Vector2D changeVelocity) {
      KeyMapping keyMapping = new KeyMapping();
      keyMapping.keyCode = keyCode;
      keyMapping.keyFunction = new ControlFunction() {
         @Override
         public void keyPressed(EntityComponents components) {
            components.movable.velocity.add(changeVelocity);
         }
         
         @Override
         public void keyReleased(EntityComponents components) {
            components.movable.velocity.subtract(changeVelocity);
         }
      };
      keyMapping.pressProcessed = false;
      keyMapping.releaseProcessed = true;
      
      controllable.keyMappings.add(keyMapping);
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

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
   private final Vector2D LEFT_ACCELERATION = new Vector2D(-250, 0);
   private final Vector2D RIGHT_ACCELERATION = new Vector2D(250, 0);
   private final Vector2D UP_ACCELERATION = new Vector2D(0, -250);
   private final Vector2D DOWN_ACCELERATION = new Vector2D(0, 250); 
   
   @Override
   public int spawn(EntityComponents components) {
      int mask = World.ENTITY_NONE;
      mask |= makeCollidable(components.collidable);
      mask |= makeMovable(components.movable);
      mask |= makeControllable(components.controllable);
      mask |= makeDrawable(components.drawable);
      return   mask;
   }
   
   private int makeCollidable(Collidable collidable) {
      collidable.bindToImageDimensions = true;
      collidable.width = 32;
      collidable.height = 64;
      return World.ENTITY_COLLIDABLE;
   }
   
   private int makeMovable(Movable movable) {
      movable.acceleration.x = 0.0;
      movable.acceleration.y = Movable.GRAVITY;
      movable.velocity.x = 0.0;
      movable.velocity.y = 0.0;
      movable.maximumSpeed = 75.0;
      movable.lastTime = System.currentTimeMillis();
      return World.ENTITY_MOVABLE;
   }
   
   private int makeControllable(Controllable controllable) {
      constructMovingKeyMapping(controllable, KeyEvent.VK_A, LEFT_ACCELERATION);      
      constructMovingKeyMapping(controllable, KeyEvent.VK_W, UP_ACCELERATION);
      constructMovingKeyMapping(controllable, KeyEvent.VK_D, RIGHT_ACCELERATION);
      constructMovingKeyMapping(controllable, KeyEvent.VK_S, DOWN_ACCELERATION);
      
      return World.ENTITY_CONTROLLABLE;
   }
   
   private void constructMovingKeyMapping(Controllable controllable, int keyCode, final Vector2D changeVelocity) {
      KeyMapping keyMapping = new KeyMapping();
      keyMapping.keyCode = keyCode;
      keyMapping.keyFunction = new ControlFunction() {
         @Override
         public void keyPressed(EntityComponents components) {
            components.movable.acceleration.add(changeVelocity);
         }
         
         @Override
         public void keyReleased(EntityComponents components) {
            components.movable.acceleration.subtract(changeVelocity);
         }
      };
      keyMapping.pressProcessed = false;
      keyMapping.releaseProcessed = true;
      
      controllable.keyMappings.add(keyMapping);
   }
   
   private int makeDrawable(Drawable drawable) {
      try {
         drawable.image = ImageIO.read(new File("Players" + File.separator + "Gordon" + File.separator + "standing.gif"));
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return World.ENTITY_DRAWABLE;
   }
}

package Core.Entity;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Collidable;
import Entity.Components.Controllable;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Entity.Components.Movable;
import Entity.Systems.DrawingSystem;
import Input.ControlFunction;
import Input.KeyMapping;
import Level.World;
import Util.Vector2D;

public class PlayerSpawner extends EntitySpawner {
   private final Vector2D ZERO_VECTOR = new Vector2D(0, 0);
   private final Vector2D LEFT_ACCELERATION = new Vector2D(-250, 0);
   private final Vector2D RIGHT_ACCELERATION = new Vector2D(250, 0);
   private final Vector2D JUMP_VELOCITY = new Vector2D(0, -200);
   private final Vector2D DOWN_ACCELERATION = new Vector2D(0, 120000); 
   
   @Override
   public int spawn(EntityComponents components) {
      int mask = World.ENTITY_NONE;
      mask |= makeCollidable(components.collidable);
      mask |= makeMovable(components.movable);
      mask |= makeDestructible(components.destructible);
      mask |= makeControllable(components.controllable);
      mask |= makeDrawable(components.drawable);
      mask |= World.ENTITY_FOCUSABLE;
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
      movable.maximumSpeed = 200.0;
      movable.lastTime = System.currentTimeMillis();
      return World.ENTITY_MOVABLE;
   }
   
   private int makeDestructible(Destructible destructible) {
      destructible.health = 100;
      destructible.maxHealth = 100;
      return World.ENTITY_DESTRUCTIBLE;
   }
   
   private int makeControllable(Controllable controllable) {
      constructMovingKeyMapping(controllable, KeyEvent.VK_A, LEFT_ACCELERATION, ZERO_VECTOR);      
      constructMovingKeyMapping(controllable, KeyEvent.VK_W, ZERO_VECTOR, JUMP_VELOCITY);
      constructMovingKeyMapping(controllable, KeyEvent.VK_D, RIGHT_ACCELERATION, ZERO_VECTOR);
      constructMovingKeyMapping(controllable, KeyEvent.VK_S, DOWN_ACCELERATION, ZERO_VECTOR);
      
      return World.ENTITY_CONTROLLABLE;
   }
   
   private void constructMovingKeyMapping(Controllable controllable, int keyCode, 
         final Vector2D changeAcceleration, final Vector2D changeVelocity) {
      KeyMapping keyMapping = new KeyMapping();
      keyMapping.keyCode = keyCode;
      keyMapping.keyFunction = new ControlFunction() {
         @Override
         public void keyPressed(EntityComponents components) {
            components.movable.acceleration.add(changeAcceleration);
            components.movable.velocity.add(changeVelocity);
         }
         
         @Override
         public void keyReleased(EntityComponents components) {
            components.movable.acceleration.subtract(changeAcceleration);
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

package Core.Entity;

import java.awt.event.KeyEvent;
import java.io.File;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Controllable;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Input.ControlFunction;
import Level.EntityContainer;
import Util.Vector2D;

public class PlayerSpawner extends EntitySpawner {
   private final static String ENTITY_IMAGE = "Players" + File.separator + "Gordon" + File.separator + "standing.gif";

   private final Vector2D LEFT_FORCE = new Vector2D(-25, 0);
   private final Vector2D RIGHT_FORCE = new Vector2D(25, 0);
   private final Vector2D UP_FORCE = new Vector2D(0, -6.8);
   
   @Override
   public int spawn(Vector2D position, Vector2D size, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeMovable(components, position, size);
      mask |= makeDestructible(components.destructible);
      mask |= makeControllable(components.controllable);
      mask |= makeDrawable(components.drawable);
      mask |= EntityContainer.ENTITY_FOCUSABLE;
      return   mask;
   }
   
   private int makeMovable(EntityComponents components, Vector2D position, Vector2D size) {
      makeAABB(components, position, size);
      return EntityContainer.ENTITY_COLLIDABLE | EntityContainer.ENTITY_MOVABLE;
   }
   
   private int makeDestructible(Destructible destructible) {
      destructible.health = 100;
      destructible.maxHealth = 100;
      return EntityContainer.ENTITY_DESTRUCTIBLE;
   }
   
   private int makeControllable(Controllable controllable) {
      constructMovingKeyMapping(controllable, KeyEvent.VK_A, LEFT_FORCE);
      constructJumpKeyMapping(controllable, KeyEvent.VK_W, UP_FORCE);
      constructMovingKeyMapping(controllable, KeyEvent.VK_D, RIGHT_FORCE);
      
      return EntityContainer.ENTITY_CONTROLLABLE;
   }
   
   private void constructMovingKeyMapping(Controllable controllable, int keyCode, 
         final Vector2D force) {
   	constructKeyMapping(controllable, keyCode, new ControlFunction() {
         @Override
         public void keyPressed(EntityComponents components) {
            components.movable.actingForces.addLocal(force);
         }
         
         @Override
         public void keyReleased(EntityComponents components) {
            components.movable.actingForces.subLocal(force);
         }
   	});
   }
   
   private void constructJumpKeyMapping(Controllable controllable, int keyCode, 
   		final Vector2D UP_FORCE) {
   	constructKeyMapping(controllable, keyCode, new ControlFunction() {
   		@Override
   		public void keyPressed(EntityComponents components) {
   		}
   		
   		@Override
   		public void keyReleased(EntityComponents components) {}
   	});
   }
   
   private int makeDrawable(Drawable drawable) {
      drawable.image = loadImage(ENTITY_IMAGE);
      return EntityContainer.ENTITY_DRAWABLE;
   }
}

package com.kjarosz.digestion.core.entity;

import java.awt.event.KeyEvent;
import java.io.File;

import com.kjarosz.digestion.entity.EntityComponents;
import com.kjarosz.digestion.entity.EntitySpawner;
import com.kjarosz.digestion.entity.components.Controllable;
import com.kjarosz.digestion.input.ControlFunction;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.util.Vector2D;

public class PlayerSpawner extends EntitySpawner {
   private final static String ENTITY_IMAGE = "Players" + File.separator + "Gordon" + File.separator + "standing.gif";
   
   private final Vector2D LEFT_FORCE = new Vector2D(-500, 0);
   private final Vector2D RIGHT_FORCE = new Vector2D(500, 0);
   private final Vector2D UP_FORCE = new Vector2D(0, -500);
   
   private EntityComponents components;

   @Override
   public int spawn(Vector2D position, Vector2D size, EntityComponents components) {
      this.components = components;

      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeDrawable();
      mask |= makeMovable(position);
      mask |= makeControllable();
      mask |= EntityContainer.ENTITY_FOCUSABLE;
      return   mask;
   }

   private int makeDrawable() {
      components.drawable.image = loadImage(ENTITY_IMAGE);
      return EntityContainer.ENTITY_DRAWABLE;
   }
   
   private int makeMovable(Vector2D position) {
      makeAABB(components, position, getImageDimensions());
      components.movable.terminalVelocity = 500;
      return EntityContainer.ENTITY_COLLIDABLE | EntityContainer.ENTITY_MOVABLE;
   }

   private Vector2D getImageDimensions() {
      return new Vector2D(components.drawable.image.getWidth(), 
                          components.drawable.image.getHeight());
   }
   
   private int makeControllable() {
      constructMovingKeyMapping(components.controllable, KeyEvent.VK_A, LEFT_FORCE);
      constructJumpKeyMapping(components.controllable, KeyEvent.VK_W, UP_FORCE);
      constructMovingKeyMapping(components.controllable, KeyEvent.VK_D, RIGHT_FORCE);
      
      return EntityContainer.ENTITY_CONTROLLABLE;
   }
   
   private void constructMovingKeyMapping(Controllable controllable, int keyCode, 
         final Vector2D force) {
   	constructKeyMapping(controllable, keyCode, new ControlFunction() {
         @Override
         public void keyPressed(EntityComponents components) {
            components.movable.velocity.addLocal(force);
         }
         
         @Override
         public void keyReleased(EntityComponents components) {
            components.movable.velocity.subLocal(force);
         }
   	});
   }
   
   private void constructJumpKeyMapping(Controllable controllable, int keyCode, 
   		final Vector2D UP_FORCE) {
   	constructKeyMapping(controllable, keyCode, new ControlFunction() {
   		@Override
   		public void keyPressed(EntityComponents components) {
   		   if(components.movable.canJump) {
   		      components.movable.velocity.addLocal(UP_FORCE);
   		      components.movable.canJump = false;
   		   } else if(components.movable.canDoubleJump) {
   		      components.movable.velocity.addLocal(UP_FORCE);
   		      components.movable.canDoubleJump = false;
   		   }
   		}
   		
   		@Override
   		public void keyReleased(EntityComponents components) {}
   	});
   }
}

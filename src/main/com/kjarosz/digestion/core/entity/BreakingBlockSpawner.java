package com.kjarosz.digestion.core.entity;

import java.io.File;

import com.kjarosz.digestion.entity.EntityComponents;
import com.kjarosz.digestion.entity.EntitySpawner;
import com.kjarosz.digestion.entity.components.Drawable;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.util.Vector2D;

public class BreakingBlockSpawner extends EntitySpawner {
   private final static String ENTITY_IMAGE = "resources" + File.separator 
         + "Entity Images" + File.separator 
         + "Breaking Block" + File.separator 
         + "BreakingBlock1.gif";
   
   @Override
   public int spawn(Vector2D position, Vector2D size, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= setCollidable(position, size, components);
      mask |= setDrawable(components.drawable);
      setEditorHints(components);
      return mask;
   }
   
   private int setCollidable(Vector2D position, Vector2D size, EntityComponents components) {
      components.body.position.set(position);
      components.body.size.set(size);
      return EntityContainer.ENTITY_COLLIDABLE;
   }
   
   private int setDrawable(Drawable drawable) {
      drawable.image = loadImage(ENTITY_IMAGE);
      drawable.tiled = true;
      return EntityContainer.ENTITY_DRAWABLE;
   }
   
   private void setEditorHints(EntityComponents components) {
      components.resizeable = true;
   }
}

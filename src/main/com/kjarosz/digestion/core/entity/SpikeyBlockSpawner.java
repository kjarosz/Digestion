package com.kjarosz.digestion.core.entity;

import java.io.File;

import com.kjarosz.digestion.entity.EntityComponents;
import com.kjarosz.digestion.entity.EntitySpawner;
import com.kjarosz.digestion.entity.components.Drawable;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.util.Vector2D;

public class SpikeyBlockSpawner extends EntitySpawner {
   private final static String ENTITY_IMAGE = "resources" + File.separator 
         + "Entity Images" + File.separator 
         + "Spike Block" + File.separator 
         + "SpikeBlock.gif";
   
   @Override
   public int spawn(Vector2D position, Vector2D size, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeMovable(components, position, size);
      mask |= makeDrawable(components.drawable);
      mask |= makeTriggerable();
      setEditorHints(components);
      return mask;
   }
   
   private int makeMovable(EntityComponents components, Vector2D position, Vector2D size) {
      makeAABB(components, position, size);
      components.movable.ignoreGravity = true;
      return EntityContainer.ENTITY_MOVABLE;
   }
   
   private int makeDrawable(Drawable drawable) {
      drawable.image = loadImage(ENTITY_IMAGE);
      drawable.tiled = true;
      return EntityContainer.ENTITY_DRAWABLE;
   }
   
   private int makeTriggerable() {
      return EntityContainer.ENTITY_TRIGGERABLE;
   }
   
   private void setEditorHints(EntityComponents components) {
      components.resizeable = true;
   }
}

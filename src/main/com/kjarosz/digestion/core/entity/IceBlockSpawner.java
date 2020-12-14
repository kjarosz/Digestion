package com.kjarosz.digestion.core.entity;

import java.io.File;

import com.kjarosz.digestion.entity.EntityComponents;
import com.kjarosz.digestion.entity.EntitySpawner;
import com.kjarosz.digestion.entity.components.Drawable;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.util.Vector2D;

public class IceBlockSpawner extends EntitySpawner {
   private static final String ENTITY_IMAGE = "resources" + File.separator 
         + "Entity Images" + File.separator 
         + "Ice Block" + File.separator 
         + "IceBlock.gif";

   @Override
   public int spawn(Vector2D position, Vector2D size, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeCollidable(components, position, size);
      mask |= makeDrawable(components.drawable);
      setEditorHints(components);
      return mask;
   }
   
   private int makeCollidable(EntityComponents components, Vector2D position, Vector2D size) {
      makeAABB(components, position, size);
      return EntityContainer.ENTITY_COLLIDABLE;
   }
   
   private int makeDrawable(Drawable drawable) {
      drawable.image = loadImage(ENTITY_IMAGE);
      drawable.tiled = true;
      return EntityContainer.ENTITY_DRAWABLE;
   }

   private void setEditorHints(EntityComponents components) {
      components.resizeable = true;
   } 
}

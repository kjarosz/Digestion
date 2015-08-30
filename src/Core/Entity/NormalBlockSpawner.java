package Core.Entity;

import java.io.File;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Drawable;
import Level.EntityContainer;
import Util.Vector2D;

public class NormalBlockSpawner extends EntitySpawner {
   private final static String ENTITY_IMAGE = "resources" + File.separator 
         + "Entity Images" + File.separator 
         + "Normal Block" + File.separator 
         + "Block.gif";
   
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

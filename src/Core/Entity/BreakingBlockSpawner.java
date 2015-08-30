package Core.Entity;

import java.io.File;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Level.EntityContainer;
import Util.Vector2D;

public class BreakingBlockSpawner extends EntitySpawner {
   private final static String ENTITY_IMAGE = "resources" + File.separator 
         + "Entity Images" + File.separator 
         + "Breaking Block" + File.separator 
         + "BreakingBlock1.gif";
   
   @Override
   public int spawn(Vector2D position, Vector2D size, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= setCollidable(position, size, components);
      mask |= setDestructible(components.destructible);
      mask |= setDrawable(components.drawable);
      setEditorHints(components);
      return mask;
   }
   
   private int setCollidable(Vector2D position, Vector2D size, EntityComponents components) {
      components.tangible.position.set(position);
      components.tangible.size.set(size);
      return EntityContainer.ENTITY_COLLIDABLE;
   }
   
   private int setDestructible(Destructible destructible) {
      destructible.health = 100;
      destructible.maxHealth = 100;
      destructible.selfReviving = true;
      destructible.revivalInterval = 2000;
      return EntityContainer.ENTITY_DESTRUCTIBLE;
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

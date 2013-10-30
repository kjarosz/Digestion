package Util;

import java.awt.geom.Rectangle2D.Double;

import Entity.EntityComponents;
import Level.World;

public class BoundingRectangle extends Double {
   public BoundingRectangle(EntityComponents components) {
      super();
      
      x = components.position.x;
      y = components.position.y;
      if(components.collidable.bindToImageDimensions) {
         width = components.drawable.image.getWidth();
         height = components.drawable.image.getHeight();
      } else {
         width = components.collidable.width;
         height = components.collidable.height;
      }
   }
   
   public BoundingRectangle(int id, World world) {
      this(world.accessComponents(id));
   }
}

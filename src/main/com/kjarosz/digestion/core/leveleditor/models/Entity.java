package com.kjarosz.digestion.core.leveleditor.models;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity extends AbstractModel {
   private String        name;
   private BufferedImage image;
   private Rectangle     entityRect;
   private boolean       resizeable;
   
   public Entity(String name, BufferedImage image, Rectangle bounds) {
      this.name = name;
      this.image = image;
      this.entityRect = new Rectangle(bounds);
      this.resizeable = false;
   }
   
   @Override
   public Entity clone() {
      Entity entity = new Entity(name, image, entityRect);
      entity.setResizeable(resizeable);
      return entity;
   }
   
   public boolean isResizeable() {
      return resizeable;
   }
   
   public void setResizeable(boolean flag) {
      resizeable = flag;
   }
   
   /* Immutable */
   public String getName() {
      return name;
   }
   
   /* Should be immutable, but I'm not about to copy it. */
   public BufferedImage getImage() {
      return image;
   }
   
   public Rectangle getRect() {
      return new Rectangle(entityRect);
   }
   
   public void setRect(Rectangle rect) {
      if(!entityRect.equals(rect)) {
         firePropertyChangeEvent("Entity size", 
               new Rectangle(entityRect), 
               new Rectangle(rect));
         entityRect.x = rect.x;
         entityRect.y = rect.y;
         if(resizeable) {
            entityRect.width = rect.width;
            entityRect.height = rect.height;
         }
      }
   }
}

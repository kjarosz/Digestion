package Core.LevelEditor.Models;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity extends AbstractModel {
   private String        name;
   private BufferedImage image;
   private Rectangle     entityRect;
   
   public Entity(String name, BufferedImage image, Rectangle bounds) {
      this.name = name;
      this.image = image;
      this.entityRect = new Rectangle(bounds);
   }
   
   @Override
   public Entity clone() {
      return new Entity(name, image, entityRect);
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
         entityRect.width = rect.width;
         entityRect.height = rect.height;
         
      }
   }
}

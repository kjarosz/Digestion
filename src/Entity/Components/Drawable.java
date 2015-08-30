package Entity.Components;

import Entity.Systems.DrawingSystem;
import java.awt.image.BufferedImage;

public class Drawable {
   public BufferedImage image;
   public boolean tiled;
   
   public Drawable() {
      image = DrawingSystem.getNullImage();
      tiled = false;
   }
}

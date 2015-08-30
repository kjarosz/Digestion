package Entity.Components;

import Entity.Systems.DrawingSystem;
import java.awt.image.BufferedImage;

public class Drawable {
   public BufferedImage image;
   public boolean tiled;
   public boolean flipped;
   
   public Drawable() {
      image = DrawingSystem.getNullImage();
      tiled = false;
      flipped = false;
   }
}

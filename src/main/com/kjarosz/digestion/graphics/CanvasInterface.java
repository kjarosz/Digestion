package com.kjarosz.digestion.graphics;

import java.awt.Image;

public interface CanvasInterface {   
   public ColorMode  setGraphicsMode(ColorMode newMode);
   public void       drawImage(ImageItem imageItem);
   public void       drawImage(Image image, double x, double y, double z, double width, double height);
   public void       draw();
}

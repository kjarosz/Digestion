package Graphics;

import java.awt.Image;

public interface CanvasInterface {   
   public ColorMode  setGraphicsMode(ColorMode newMode);
   public void       drawImage(ImageItem imageItem);
   public void       drawImage(Image image, float x, float y, float z, float width, float height);
   public void       draw();
}

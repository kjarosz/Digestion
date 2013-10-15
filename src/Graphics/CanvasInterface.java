package Graphics;

import java.awt.Image;

public interface CanvasInterface {
   public ColorMode  setGraphicsMode(ColorMode newMode);
   public void       drawImage(ImageItem imageItem);
   public void       drawImage(Image image, int x, int y, int z, int width, int height);
   public void       showCanvas();
}

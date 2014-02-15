package Graphics;

import java.awt.Image;

public interface CanvasInterface {
   public void       invertYAxis(boolean flag);
   
   /*
    * @param factor: Pixels per unit. ( ex. 32 pixels per 1 meter: 32.0f / 1.0f = 32.0f
    * 
    * To reset conversion factor, call the function with 1.0f as argument.
    */
   public void       setUnitConversionFactor(float factor);
   public float      getUnitConversionFactor();
   
   public ColorMode  setGraphicsMode(ColorMode newMode);
   public void       drawImage(ImageItem imageItem);
   public void       drawImage(Image image, float x, float y, float z, float width, float height);
   public void       showCanvas();
}

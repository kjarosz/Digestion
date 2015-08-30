package Graphics;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas implements CanvasInterface {	
   private ImageQueue mImageQueue;
   private BufferStrategy mStrategy;
   
   private Graphics2D mBuffer;

   public GameCanvas(GameWindow parent) {
      mImageQueue = new ImageQueue();

      parent.addCard(this, "GAME CANVAS");
      
      setIgnoreRepaint(true);
      
      createBufferStrategy(2);
      do {
         mStrategy = getBufferStrategy();
      } while (mStrategy == null);
   }

   @Override
   public ColorMode setGraphicsMode(ColorMode newMode) {
      return mImageQueue.setMode(new ColorMode(newMode));
   }

   @Override
   public void drawImage(ImageItem imageItem) {
      mImageQueue.addImage(imageItem);
   }

   @Override
   public void drawImage(Image image, double x, double y, double z, double width, double height) {
      ImageItem imageItem = new ImageItem();
      imageItem.image = image;
      imageItem.x = x;
      imageItem.y = y;
      imageItem.z = z;
      imageItem.width = width;
      imageItem.height = height;

      mImageQueue.addImage(imageItem);
   }
   
   private void spawnStrategy() {
      if(mStrategy != null) {
         return;
      }
      
      this.createBufferStrategy(2);
      while(mStrategy == null) {
         mStrategy = this.getBufferStrategy();
      }
   }

   private Graphics2D getBuffer() {
      spawnStrategy();
      if (mBuffer == null) {
         try {
              mBuffer = (Graphics2D)mStrategy.getDrawGraphics();
         } catch(IllegalStateException e) {
            return null;
         }
      }
      return mBuffer;
   }
   
   private boolean updateCanvas() {
      mBuffer.dispose();
      mBuffer = null;
      try {
         mStrategy.show();
         Toolkit.getDefaultToolkit().sync();
         return (!mStrategy.contentsLost());
      } catch (NullPointerException ex) {
         return true;
      } catch (IllegalStateException ex) {
         return true;
      }
   }
   
   @Override
   public void draw() {
      do {
         getBuffer();
         drawItems();
      } while (!updateCanvas());
   }

   private void drawItems() { 
      mBuffer.clearRect(0, 0, getWidth(), getHeight());
      while(mImageQueue.hasImages()) {
         ImageItem image = mImageQueue.nextImage(mBuffer);
         mBuffer.drawImage(image.image, 
               (int)(image.x), 
               (int)(image.y), 
               (int)(image.width), 
               (int)(image.height), 
               null);
      }
   }
}

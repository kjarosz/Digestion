package Graphics;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameCanvas extends Canvas implements CanvasInterface {	
   private GameViewport mViewport;
   private boolean mViewportEnabled;

   private ImageQueue mImageQueue;
   private BufferStrategy mStrategy;
   
   private Graphics2D mBuffer;

   public GameCanvas(JFrame parent) {
      mViewport = null;
      mViewportEnabled = false;
      mImageQueue = new ImageQueue();

      parent.add(this, 0);
      
      setIgnoreRepaint(true);
      
      createBufferStrategy(2);
      do {
         mStrategy = getBufferStrategy();
      } while (mStrategy == null);
   }

   public void setViewport(GameViewport viewport) {
      mViewport = viewport;
      mViewportEnabled = true;
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
   public void drawImage(Image image, float x, float y, float z, float width, float height) {
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
      if(!mViewportEnabled) {
         drawWithoutViewport();
      } else {
         drawWithViewport();
      }
   }

   private void drawWithoutViewport() {
      ImageItem imageItem;
      while(mImageQueue.hasImages()) {
         imageItem = mImageQueue.nextImage(mBuffer);
         mBuffer.drawImage(imageItem.image, 
               (int)(imageItem.x), 
               (int)(imageItem.y), 
               (int)(imageItem.width), 
               (int)(imageItem.height), 
               null);
      }
   }

   private void drawWithViewport() {
      mViewport.update();

      while(mImageQueue.hasImages()) {
         ImageItem imageItem = mImageQueue.nextImage(mBuffer);

         Rectangle2D.Float objectRect = new Rectangle2D.Float();
         objectRect.x = imageItem.x;
         objectRect.y = imageItem.y;
         objectRect.width = imageItem.width;
         objectRect.height = imageItem.height;

         if(!mViewport.contains(objectRect))
            continue;

         mViewport.translate(imageItem.x, imageItem.y, imageItem.width, imageItem.height, objectRect);
         mBuffer.drawImage(imageItem.image,
               (int)(objectRect.x),
               (int)(objectRect.y),
               (int)(objectRect.width),
               (int)(objectRect.height),
               null);
      }
   }
}

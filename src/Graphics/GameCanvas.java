package Graphics;

import Util.ErrorLog;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas implements CanvasInterface {	
	private BufferStrategy mBackBuffer;
   private boolean mBackBufferFailed;
	
	private GameViewport mViewport;
	private boolean mViewportEnabled;
	
	private ImageQueue mImageQueue;
	
	public GameCanvas() {
      mBackBufferFailed = false;
      
		mViewport = new GameViewport();
		mViewportEnabled = false;
      
		mImageQueue = new ImageQueue();
	}
   
   private boolean createBackBuffer() {
      try {
         createBufferStrategy(2);
         mBackBuffer = getBufferStrategy();
      } catch(Exception ex) {
         ErrorLog errorLog = ErrorLog.getInstance();
         errorLog.writeError(ex.getMessage());
         mBackBufferFailed = true;
         return false;
      }
      return true;
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
	public void drawImage(Image image, int x, int y, int z, int width, int height) {
		ImageItem imageItem = new ImageItem();
		imageItem.image = image;
		imageItem.x = x;
		imageItem.y = y;
		imageItem.z = z;
		imageItem.width = width;
		imageItem.height = height;
		
		mImageQueue.addImage(imageItem);
	}
	
   @Override
	public void showCanvas() {		
		mImageQueue.sort();
		
      Graphics2D g;
      if(!mBackBufferFailed && mBackBuffer == null)
         createBackBuffer();
      
      if(mBackBuffer == null) {
         g = (Graphics2D)getGraphics();
         drawItems(g);
         g.dispose();
      } else {
         g = (Graphics2D)mBackBuffer.getDrawGraphics();
         drawItems(g);
         g.dispose();
         mBackBuffer.show();
      }
   }
   
   private void drawItems(Graphics2D g) {
      g = (Graphics2D)mBackBuffer.getDrawGraphics();

      if(!mViewportEnabled) {
         ImageItem imageItem;
         while(mImageQueue.hasImages()) {
            imageItem = mImageQueue.nextImage(g);
            g.drawImage(imageItem.image, 
                     imageItem.x, 
                     imageItem.y, 
                     imageItem.width, 
                     imageItem.height, 
                     null);
         }
      } else {
         mViewport.update();

         Rectangle objectRect = new Rectangle();
         ImageItem imageItem;
         while(mImageQueue.hasImages()) {
            imageItem = mImageQueue.nextImage(g);
            objectRect.x = imageItem.x;
            objectRect.y = imageItem.y;
            objectRect.width = imageItem.width;
            objectRect.height = imageItem.height;

            if(!mViewport.contains(objectRect))
               continue;

            mViewport.translate(imageItem.x, imageItem.y, imageItem.width, imageItem.height, objectRect);
            g.drawImage(imageItem.image,
                     objectRect.x,
                     objectRect.y,
                     objectRect.width,
                     objectRect.height,
                     null);
         }
      }
      
   }
}

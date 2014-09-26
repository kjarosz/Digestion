package Graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class GameCanvas extends JPanel implements CanvasInterface {	
	private GameViewport mViewport;
	private boolean mViewportEnabled;
	
	private ImageQueue mImageQueue;
	
	public GameCanvas() {
		mViewport = null;
		mViewportEnabled = false;
      
		mImageQueue = new ImageQueue();
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
	
   @Override
	public void showCanvas() {
      repaint();
   }
   
   private void drawItems(Graphics2D g) { 
      Rectangle clipBounds = g.getClipBounds();
      g.clearRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
      
      if(!mViewportEnabled) {
         drawWithoutViewport(g);
      } else {
         drawWithViewport(g);
      }
   }
   
   private void drawWithoutViewport(Graphics2D g) {
      ImageItem imageItem;
      while(mImageQueue.hasImages()) {
         imageItem = mImageQueue.nextImage(g);
         g.drawImage(imageItem.image, 
                  (int)(imageItem.x), 
                  (int)(imageItem.y), 
                  (int)(imageItem.width), 
                  (int)(imageItem.height), 
                  null);
      }
   }
   
   private void drawWithViewport(Graphics2D g) {
         Rectangle bounds = g.getClipBounds();
         mViewport.setWindowSize(bounds.width, bounds.height);
         mViewport.update();

         while(mImageQueue.hasImages()) {
            ImageItem imageItem = mImageQueue.nextImage(g);
            
            Rectangle2D.Float objectRect = new Rectangle2D.Float();
            objectRect.x = imageItem.x;
            objectRect.y = imageItem.y;
            objectRect.width = imageItem.width;
            objectRect.height = imageItem.height;

            if(!mViewport.contains(objectRect))
               continue;
            
            mViewport.translate(imageItem.x, imageItem.y, imageItem.width, imageItem.height, objectRect);
            g.drawImage(imageItem.image,
                     (int)(objectRect.x),
                     (int)(objectRect.y),
                     (int)(objectRect.width),
                     (int)(objectRect.height),
                     null);
         }
   }
   
   @Override
   public void paint(Graphics g) {
      mImageQueue.sort();
      
      Graphics2D g2 = (Graphics2D)g;
      
      drawItems((Graphics2D)g);
   }
}

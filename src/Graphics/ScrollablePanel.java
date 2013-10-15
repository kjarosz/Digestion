package Graphics;

import Core.LevelEditor.Panels.Control.ContentPanelControl;
import Util.Size;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class ScrollablePanel extends JScrollPane implements CanvasInterface {
   private ImageQueue mImageQueue;
   
   private BufferedImage mCanvas;
   private ScrollablePicture mCanvasContainer;
   
   public ScrollablePanel(ContentPanelControl canvasControl, Size size) {
      super();
      
      mImageQueue = new ImageQueue();
      mCanvas = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
      createWidgets(canvasControl);
   }
   
   private void createWidgets(ContentPanelControl canvasControl) {
      setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      
      mCanvasContainer = new ScrollablePicture(new ImageIcon(mCanvas), 1);
      mCanvasContainer.addMouseListener(canvasControl);
      mCanvasContainer.addMouseMotionListener(canvasControl);
      add(mCanvasContainer);
      setViewportView(mCanvasContainer);
   }
   
   public Dimension getCanvasSize() {
      return new Dimension(mCanvas.getWidth(), mCanvas.getHeight());
   }
   
   public void setCanvasSize(Dimension size) {
      mCanvas = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
      mCanvasContainer.setIcon(new ImageIcon(mCanvas));
   }
   
   public Graphics getCanvasGraphics() {
      return mCanvas.getGraphics();
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
		
      Graphics2D g = (Graphics2D)getCanvasGraphics();
      drawItems(g);
      g.dispose();
      
      this.repaint();
   }
   
   private void drawItems(Graphics2D g) {
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
   }
}

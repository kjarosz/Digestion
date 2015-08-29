package Menu.Widgets;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Core.Events.Callback;
import Core.Events.DummyCallback;
import Core.Events.Event;
import Core.Events.MouseEvent;
import Graphics.CanvasInterface;

public class Button extends JButton implements UIElement {
   private BufferedImage mImage;
   private Rectangle mButton;
   
   private boolean mButtonActive;
   private Callback mCallback;
   
   public Button(String imageFilename) {
      loadButtonImage(imageFilename);
      mButtonActive = false;
      mCallback = new DummyCallback();
   }

   private void loadButtonImage(String imageFilename) {
      try {
         mImage = ImageIO.read(new File(imageFilename));
      } catch (IOException ex) {
         JOptionPane.showMessageDialog(null,
               ex.getMessage(), 
               "Error loading button image", 
               JOptionPane.ERROR_MESSAGE);
      }
   }
   
   public void setActionCallback(Callback callback) {
      mCallback = callback;
   }
   
   @Override
   public void handleUIEvent(Event event) {
      if(event instanceof MouseEvent) {
         MouseEvent e = (MouseEvent)event;
         if(e.mAction == MouseEvent.MouseAction.PRESSED 
               && mButton.contains(e.mPosition)) {
            mButtonActive = true;
         } else if(e.mAction == MouseEvent.MouseAction.RELEASED
               && mButtonActive) {
           if(mButton.contains(e.mPosition)) {
              mCallback.execute();
           } else {
              mButtonActive = false;
           }
         }
      }
   }
   
   @Override
   public void draw(CanvasInterface canvas) {
      Dimension dims = calculateImageDimensions();
      canvas.drawImage(mImage, 
            getX() + getWidth()/2 - dims.width/2, 
            getY() + getHeight()/2 - dims.height/2, 
            0, 
            dims.width, 
            dims.height);
   }
   
   private Dimension calculateImageDimensions() {
      Dimension img_dims = new Dimension(mImage.getWidth(), mImage.getHeight());
      Dimension bounds = new Dimension(getWidth(), getHeight());
      
      // Scale by width
      Dimension scale = new Dimension();
      scale.width = bounds.width;
      scale.height = (int)(img_dims.height*(double)bounds.width/(double)img_dims.width);
      if(scale.height <= bounds.height) {
         return scale;
      }
      
      scale.height = bounds.height;
      scale.width = (int)(img_dims.width*(double)bounds.height/(double)img_dims.height);
      return scale;
   }
}

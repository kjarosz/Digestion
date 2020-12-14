package com.kjarosz.digestion.menu.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.kjarosz.digestion.core.events.Callback;
import com.kjarosz.digestion.core.events.DummyCallback;
import com.kjarosz.digestion.core.events.Event;
import com.kjarosz.digestion.core.events.MouseEvent;
import com.kjarosz.digestion.graphics.CanvasInterface;

public class Button extends JButton implements UIElement {
   private BufferedImage mLighterImage;
   private BufferedImage mImage;
   
   private boolean mButtonActive;
   private boolean mButtonHighlighted;
   private Callback mCallback;
   
   public Button(String imageFilename) {
      loadButtonImage(imageFilename);
      mButtonActive = false;
      mCallback = new DummyCallback();
   }

   private void loadButtonImage(String imageFilename) {
      try {
         mLighterImage = null;
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
         processMousePress(e);
         processMouseMotion(e);
      }
   }
   
   private void processMousePress(MouseEvent e) {
      if(e.mAction == MouseEvent.MouseAction.PRESSED 
            && getRect().contains(e.mPosition)) {
         mButtonActive = true;
      } else if(e.mAction == MouseEvent.MouseAction.RELEASED
            && mButtonActive) {
         if(getRect().contains(e.mPosition)) {
            mCallback.execute();
         } 
         mButtonActive = false;
      }
   }
   
   private void processMouseMotion(MouseEvent e) {
      if(e.mAction == MouseEvent.MouseAction.MOVE ||
            e.mAction == MouseEvent.MouseAction.DRAG) {
         mButtonHighlighted = getRect().contains(e.mPosition); 
      }
      
   }
   
   private Rectangle getRect() {
      return new Rectangle(getX(), getY(),
            getWidth(), getHeight());
   }
   
   @Override
   public void draw(CanvasInterface canvas) {
      BufferedImage image = mImage;
      Dimension dims = calculateImageDimensions();
      int active_offset = 0;
      if(mButtonActive) active_offset = 5;
      if(mButtonHighlighted) image = getLighterImage();
      canvas.drawImage(image, 
            getX() + getWidth()/2 - dims.width/2, 
            getY() + getHeight()/2 - dims.height/2 + active_offset, 
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
   
   private BufferedImage getLighterImage() {
      if(mLighterImage == null) {
         createLighterImage();
      }
      return mLighterImage;
   }
   
   private void createLighterImage() {
      mLighterImage = new BufferedImage(mImage.getWidth(), mImage.getHeight(), mImage.getType());
      Graphics2D g = (Graphics2D)mLighterImage.getGraphics();
      g.drawImage(mImage, 0, 0, null);
      
      g.setColor(Color.RED);
      
      int yOffset = mLighterImage.getHeight() - 2;
      g.drawLine(0, yOffset, mLighterImage.getWidth(), yOffset);
      
      g.dispose();
   }
}

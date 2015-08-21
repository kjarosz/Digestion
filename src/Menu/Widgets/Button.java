package Menu.Widgets;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Button extends JButton {
   private BufferedImage mImage;
   
   public Button(String imageFilename) {
      loadButtonImage(imageFilename);
      setBorderPainted(false); 
      setContentAreaFilled(false); 
      setFocusPainted(false); 
      setOpaque(false);
   }
   
   public void loadButtonImage(String imageFilename) {
      try {
         mImage = ImageIO.read(new File(imageFilename));
      } catch (IOException ex) {
         JOptionPane.showMessageDialog(this,
               ex.getMessage(), 
               "Error loading button image", 
               JOptionPane.ERROR_MESSAGE);
      }
   }
   
   @Override
   public void paintComponent(Graphics g) {
      Dimension dims = calculateImageDimensions();
      g.drawImage(mImage, 
            getWidth()/2 - dims.width/2, 
            0, 
            dims.width, 
            dims.height, 
            null);
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

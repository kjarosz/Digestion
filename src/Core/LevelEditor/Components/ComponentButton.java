package Core.LevelEditor.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import Core.LevelEditor.Models.ComponentModel;
import Core.LevelEditor.Settings.EntityComposerSettings;

public class ComponentButton extends JButton implements MouseListener {
   private final static Color INACTIVE_COLOR = Color.GREEN;
   private final static Color SELECTED_COLOR = Color.BLUE;
   private final static Color INACTIVE_HOVER = Color.CYAN;
   private final static Color SELECTED_HOVER = new Color(0, 120, 0);
   
   private EntityComposerSettings mComponentComposerSettings;
   
   private ComponentModel mComponentModel;
   
   private Color mCurrentColor;
      
   public ComponentButton(EntityComposerSettings componentComposerSettings,
         ComponentModel componentModel) {
      mComponentComposerSettings = componentComposerSettings;
      mComponentModel = componentModel;
      
      mCurrentColor = INACTIVE_COLOR;
   }
   
   public Dimension setMinimumSize() {
      return new Dimension(50, 50);
   }
   
   public Dimension setMaximumSize() {
      return new Dimension(Integer.MAX_VALUE, 50);
   }
   
   @Override
   public void paintComponent(Graphics g) {
      String compName = mComponentModel.getName();
      drawBackground(g);      
      g.drawString(compName, 15,  15);
   }
   
   private void drawBackground(Graphics g) {
      Color oldColor = g.getColor();
      g.setColor(mCurrentColor);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(oldColor);
   }
   
   public boolean componentIsSelected() {
      ComponentModel selectedModel = mComponentComposerSettings.getSelectedComponentModel();
      if(mComponentModel.equals(selectedModel)) {
         return true;
      }
      return false;
   }

   @Override
   public void mouseClicked(MouseEvent arg0) {
      
   }

   @Override
   public void mouseEntered(MouseEvent arg0) {
      if(componentIsSelected()) {
         mCurrentColor = SELECTED_HOVER;
      } else {
         mCurrentColor = INACTIVE_HOVER;
      }
      repaint();
   }

   @Override
   public void mouseExited(MouseEvent arg0) {
      if(componentIsSelected()) {
         mCurrentColor = SELECTED_COLOR;
      } else {
         mCurrentColor = INACTIVE_COLOR;
      }
   }

   @Override public void mousePressed(MouseEvent arg0) {}
   @Override public void mouseReleased(MouseEvent arg0) {}
   
   
}

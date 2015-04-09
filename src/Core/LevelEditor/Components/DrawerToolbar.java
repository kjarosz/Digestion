package Core.LevelEditor.Components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.LevelModel;
import Util.Size;

public class DrawerToolbar extends JToolBar {
   private LevelModel mLevelModel;
   private DrawerSettings mDrawerSettings;

   private JTextField mLevelWidth;
   private JTextField mLevelHeight;
   private JTextField mGridWidth;
   private JTextField mGridHeight;
   
   public DrawerToolbar(LevelModel levelModel, DrawerSettings drawerSettings) {
      mLevelModel = levelModel;
      mDrawerSettings = drawerSettings;
      
      createWidgets();
   }
   
   /* ********************************************************************** */
   /*                               VIEW                                     */
   /* ********************************************************************** */
   private void createWidgets() {
      createToggles();
      add(new JSeparator(JSeparator.VERTICAL));
      createSizeFields();
      createGridSizeFields();
   }
   
   private void createToggles() {
      createToggle("Grid", 
            mDrawerSettings.isGridEnabled(), 
            createGridToggleAction());
      createToggle("Entities", 
            mDrawerSettings.areEntitiesEnabled(), 
            createEntitiesToggleAction());
      createToggle("Platforms", 
            mDrawerSettings.arePlatformsEnabled(),
            createPlatformsToggleAction());
   }
   
   private void createToggle(String name, boolean initValue, ActionListener listener) {
      JCheckBox toggle = new JCheckBox(name, initValue);
      toggle.addActionListener(listener);
      add(toggle);
   }
   
   private void createSizeFields() {
      add(new JLabel("Size:"));
      Dimension levelSize = mLevelModel.getSize();
      FocusListener levelSizeListener = createLevelSizeListener();
      mLevelWidth  = createTextField(levelSize.width + "", levelSizeListener);
      mLevelHeight = createTextField(levelSize.height + "", levelSizeListener);
   }
   
   private void createGridSizeFields() {
      add(new JLabel("GridSize:"));
      Size gridSize = mDrawerSettings.getGridSize();
      FocusListener gridSizeListener = createGridSizeListener();
      mGridWidth  = createTextField(gridSize.width + "", gridSizeListener);
      mGridHeight = createTextField(gridSize.height + "", gridSizeListener);
   }
   
   private JTextField createTextField(String text, FocusListener listener) {
      JTextField textField = new JTextField(4);
      textField.setText(text);
      textField.addFocusListener(listener);
      add(textField);
      return textField;
   }
   
   /* ********************************************************************** */
   /*                                CONTROL                                 */
   /* ********************************************************************** */
   private ActionListener createGridToggleAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JCheckBox toggle = (JCheckBox)e.getSource();
            mDrawerSettings.setGridEnabled(toggle.isSelected());
         }
      };
   }
   
   private ActionListener createEntitiesToggleAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JCheckBox toggle = (JCheckBox)e.getSource();
            mDrawerSettings.setEntitiesEnabled(toggle.isSelected());
         }
      };
   }
   
   private ActionListener createPlatformsToggleAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JCheckBox toggle = (JCheckBox)e.getSource();
            mDrawerSettings.setPlatformsEnabled(toggle.isSelected());
         }
      };
   }
   
   private FocusListener createLevelSizeListener() {
      return new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            String widthStr = mLevelWidth.getText();
            String heightStr = mLevelHeight.getText();
            try {
               int width = Integer.parseInt(widthStr);
               int height = Integer.parseInt(heightStr);
               
               if(width < 256 || height < 256) {
                  warnAboutSize();
                  resetLevelSize();
               }
               
               mLevelModel.setSize(new Dimension(width, height));
            } catch(NumberFormatException ex) {
               resetLevelSize();
            }
         }
      };
   }
   
   private void warnAboutSize() {
      JOptionPane.showMessageDialog(this,
            "No dimension allowed to be less 256!",
            "Level too small.",
            JOptionPane.OK_OPTION);
   }
   
   private void resetLevelSize() {
      Dimension levelSize = mLevelModel.getSize();
      mLevelWidth.setText(levelSize.width+"");
      mLevelHeight.setText(levelSize.height+"");
   }
   
   private FocusListener createGridSizeListener() {
      return new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            String widthStr = mGridWidth.getText();
            String heightStr = mGridHeight.getText();
            try {
               int width = Integer.parseInt(widthStr);
               int height = Integer.parseInt(heightStr);
               
               if(width < 1 || height < 1) {
                  resetGridSize();
               }
               
               mDrawerSettings.setGridSize(new Size(width, height));
            } catch(NumberFormatException ex) {
               resetGridSize();
            }
         }
      };
   }
   
   private void resetGridSize() {
      Size gridSize = mDrawerSettings.getGridSize();
      mGridWidth.setText(gridSize.width + "");
      mGridHeight.setText(gridSize.height + "");
   }
}

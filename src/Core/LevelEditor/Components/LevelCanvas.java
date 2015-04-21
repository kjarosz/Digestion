package Core.LevelEditor.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import Core.LevelEditor.Control.ContentPanelControl;
import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.Entity;
import Core.LevelEditor.Models.LevelModel;
import Util.Size;

public class LevelCanvas extends JComponent {
   private EditorSettings mEditorSettings;
   private LevelModel mLevelModel;
   private DrawerSettings mDrawerSettings;
   
   private BufferedImage mCanvas;
   
   public LevelCanvas(EditorSettings editorSettings, 
         LevelModel levelModel, DrawerSettings drawerSettings) 
   {      
      mEditorSettings = editorSettings;
      mLevelModel = levelModel;
      mDrawerSettings = drawerSettings;
      createPropertyListeners();
      
      ContentPanelControl contentControl = new ContentPanelControl(
            mEditorSettings, mLevelModel, mDrawerSettings
      );
      addMouseListener(contentControl);
      addMouseMotionListener(contentControl);
   }
   
   private void createPropertyListeners() {
      JComponent me = this;
      PropertyChangeListener listener = new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent e) {
            me.getParent().revalidate();
            me.repaint();
         }
      };
      
      mEditorSettings.addPropertyChangeListener(listener);
      mLevelModel.addPropertyChangeListener(listener);
      mDrawerSettings.addPropertyChangeListener(listener);
   }
      
   public Dimension getMinimumSize() {
      return mLevelModel.getSize();
   }
   
   public Dimension getPreferredSize() {
      return mLevelModel.getSize();
   }
   
   public Dimension getMaximumSize() {
      return mLevelModel.getSize();
   }
   
   @Override
   public void paintComponent(Graphics g) {
      Dimension levelSize = mLevelModel.getSize();
      formCanvas(levelSize);
      paintToCanvas(levelSize);
      g.drawImage(mCanvas, 0, 0, null);
   }
   
   private void formCanvas(Dimension levelSize) {
      if(mCanvas == null 
            || mCanvas.getWidth() != levelSize.width
            || mCanvas.getHeight() != levelSize.height) {
         mCanvas = new BufferedImage(
               levelSize.width,
               levelSize.height,
               BufferedImage.TYPE_INT_ARGB);
      }
   }
   
   private void paintToCanvas(Dimension size) {
      Graphics2D g2 = mCanvas.createGraphics();
      clear(g2, size);
      if(mDrawerSettings.isBackgroundEnabled()) drawBackground(g2, size);
      if(mDrawerSettings.isGridEnabled())       drawGrid(g2, size);
      if(mDrawerSettings.areEntitiesEnabled())  drawItems(g2, size);
      if(mDrawerSettings.arePlatformsEnabled()) drawPlatforms(g2, size);
      g2.dispose();
   }
   
   private void clear(Graphics2D g2, Dimension size) {
      Color oldColor = g2.getColor();
      g2.setColor(Color.white);
      g2.fillRect(0, 0, size.width, size.height);
      g2.setColor(oldColor);
   }
   
   private void drawBackground(Graphics2D g2, Dimension size) {
      BufferedImage background = mLevelModel.getBackground();
      if(background != null) {
         g2.drawImage(background, 0, 0, size.width, size.height, null);
      }
   }
   
   private void drawGrid(Graphics2D g2, Dimension size) {
      Color oldColor = g2.getColor();
      g2.setColor(Color.black);
      
      Size gridSize = mDrawerSettings.getGridSize();
      for(int x = gridSize.width; x < size.width; x += gridSize.width) {
         g2.drawLine(x, 0, x, size.height);
         for(int y = gridSize.height; y < size.height; y += gridSize.height) {
            g2.drawLine(0, y, size.width, y);
         }
      }
      
      g2.setColor(oldColor);
   }
   
   private void drawItems(Graphics2D g2, Dimension size) {
      for(Entity entity: mLevelModel.getEntities()) {
         drawItem(g2, size, entity);
      }
   }
   
   private void drawItem(Graphics2D g2, Dimension size, Entity entity) {
      Rectangle bounds = entity.getRect();
      BufferedImage image = entity.getImage();
      int width = image.getWidth();
      int height = image.getHeight();
      for(int x = 0; x < bounds.width; x += width) {
         for(int y = 0; y < bounds.height; y += height) {
            Rectangle cell = getCellBounds(x, y, width, height, bounds);
            
            g2.drawImage(image, 
                  cell.x, cell.y, // Destination
                  cell.x + cell.width, cell.y + cell.height,
                  0, 0, // Source
                  cell.width, cell.height,
                  null);
         }
      }
   }
   
   private Rectangle getCellBounds(int x, int y, int imgWidth, int imgHeight,
         Rectangle entityBounds) {
      Rectangle cellBounds = new Rectangle();
      cellBounds.x = x + entityBounds.x;
      cellBounds.y = y + entityBounds.y;
      cellBounds.width = getMaxDim(entityBounds.width, x, imgWidth);
      cellBounds.height = getMaxDim(entityBounds.height, y, imgHeight);
      return cellBounds;
   }
   
   private int getMaxDim(int bound, int offset, int dimension) {
      return (bound - offset > dimension) ? dimension : bound - offset;
   }
   
   private void drawPlatforms(Graphics2D g2, Dimension size) {
      // Draw some platforms.
   }
}

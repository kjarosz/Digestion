package com.kjarosz.digestion.core.leveleditor.control.commands;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import com.kjarosz.digestion.core.leveleditor.models.Entity;
import com.kjarosz.digestion.core.leveleditor.models.EntityCache;
import com.kjarosz.digestion.core.leveleditor.models.LevelModel;
import com.kjarosz.digestion.core.leveleditor.settings.DrawerSettings;
import com.kjarosz.digestion.core.leveleditor.settings.EditorSettings;

public class AddEntity extends LevelModelCommand {
   private ChangeEntityCommand mChangeCommand;
   
   private EntityCache mEntityCache;
   
   public AddEntity(ChangeEntityCommand changeCommand, LevelModel level, 
         EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
      
      mChangeCommand = changeCommand;
      mEntityCache = new EntityCache();
   }
   
   @Override
   public void perform(MouseEvent e) {
      if(levelHasEntity(e.getPoint())) {
         return;
      }
      
      Entity entity = getSelectedEntity();
      
      Point pos = snapToGrid(e.getPoint());
      Dimension gridSize = mDrawer.getGridSize();
      Rectangle newEntityRect = new Rectangle();
      newEntityRect.x = pos.x;
      newEntityRect.y = pos.y;
      newEntityRect.width = gridSize.width;
      newEntityRect.height = gridSize.height;
      
      entity.setRect(newEntityRect);
      
      mLevel.addEntity(entity);
      
      mChangeCommand.setActiveEntity(entity);
   }
   
   private boolean levelHasEntity(Point coords) {
      return mLevel.getEntities().stream()
         .anyMatch((entity) -> (entity.getRect().contains(coords)));
   }
   
   private Entity getSelectedEntity() {
      try {
         String entityName = mEditor.getSelectedEntity();
         return mEntityCache.cloneEntity(entityName);
      } catch(RuntimeException e) {
         JOptionPane.showMessageDialog(null, 
               e.getMessage(),
               "Error Selecting Entity",
               JOptionPane.ERROR_MESSAGE);
         throw e;
      }
   }
}

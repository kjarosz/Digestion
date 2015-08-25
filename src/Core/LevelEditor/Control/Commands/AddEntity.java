package Core.LevelEditor.Control.Commands;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import Core.LevelEditor.Models.Entity;
import Core.LevelEditor.Models.EntityCache;
import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Settings.DrawerSettings;
import Core.LevelEditor.Settings.EditorSettings;
import Util.Size;

public class AddEntity extends LevelModelCommand {
   private ChangeEntityCommand mChangeCommand;
   
   private EntityCache mEntityCache;
   
   public AddEntity(ChangeEntityCommand changeCommand, LevelModel level, 
         EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
      
      mChangeCommand = changeCommand;
      mEntityCache = new EntityCache();
   }
   
   public void perform(MouseEvent e) {
      if(levelHasEntity(e.getPoint())) {
         return;
      }
      
      Entity entity = getSelectedEntity();
      
      Point pos = snapToGrid(e.getPoint());
      Size gridSize = mDrawer.getGridSize();
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
      for(Entity entity: mLevel.getEntities()) {
         if(entity.getRect().contains(coords)) {
            return true;
         }
      }
      return false;
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

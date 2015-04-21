package Core.LevelEditor.Control.Commands;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.Entity;
import Core.LevelEditor.Models.LevelModel;

public class ChangeEntityCommand extends LevelModelCommand {

   private Entity mActiveEntity;
   
   private Point mStartPosition;
   
   public ChangeEntityCommand(LevelModel level,
         EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
      mStartPosition = new Point();
   }
   
   @Override
   public void perform(MouseEvent event) {
      
   }
   
   protected void setActiveEntity(Entity entity) {
      mActiveEntity = entity;
      Rectangle bounds = entity.getRect();
      mStartPosition.x = bounds.x;
      mStartPosition.y = bounds.y;
   }

}

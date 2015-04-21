package Core.LevelEditor.Control.Commands;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.Entity;
import Core.LevelEditor.Models.LevelModel;
import Util.Size;

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
      Point mouseCoords = snapToGrid(event.getPoint());
      
      Rectangle bounds = getBounds(mStartPosition, mouseCoords);
      mActiveEntity.setRect(bounds);
   }
   
   private Rectangle getBounds(Point p1, Point p2) {
      Point topLeft = getTopLeft(p1, p2);
      Point bottomRight = getBottomRight(p1, p2);
      return formBounds(topLeft, bottomRight);
   }
   
   private Point getTopLeft(Point p1, Point p2) {
      Point topLeft = new Point();
      topLeft.x = (p1.x < p2.x) ? p1.x : p2.x;
      topLeft.y = (p1.y < p2.y) ? p1.y : p2.y;
      return topLeft;
   }
   
   private Point getBottomRight(Point p1, Point p2) {
      Point bottomRight = new Point();
      bottomRight.x = (p1.x >= p2.x) ? p1.x : p2.x;
      bottomRight.y = (p1.y >= p2.y) ? p1.y : p2.y;
      return bottomRight;
   }
   
   private Rectangle formBounds(Point topLeft, Point bottomRight) {
      Size gridSize = mDrawer.getGridSize();
      Rectangle bounds = new Rectangle();
      bounds.x = topLeft.x;
      bounds.y = topLeft.y;
      bounds.width = bottomRight.x - topLeft.x + gridSize.width;
      bounds.height = bottomRight.y - topLeft.y + gridSize.height;
      return bounds;
   }
   
   protected void setActiveEntity(Entity entity) {
      mActiveEntity = entity;
      Rectangle bounds = entity.getRect();
      mStartPosition.x = bounds.x;
      mStartPosition.y = bounds.y;
   }

}

package Core.LevelEditor.Control.Commands;

import java.awt.Point;
import java.awt.event.MouseEvent;

import Util.Size;
import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Settings.DrawerSettings;
import Core.LevelEditor.Settings.EditorSettings;

public abstract class LevelModelCommand {

   protected LevelModel mLevel;
   protected EditorSettings mEditor;
   protected DrawerSettings mDrawer;
   
   public LevelModelCommand(LevelModel level, EditorSettings editor, 
         DrawerSettings drawer) {
      mLevel = level;
      mEditor = editor;
      mDrawer = drawer;
   }
   
   public abstract void perform(MouseEvent event);
   
   protected Point snapToGrid(Point coords) {
      Size gridSize = mDrawer.getGridSize();
      Point snapped = new Point(coords);
      snapped.x = (int)(coords.x/(float)gridSize.width)*gridSize.width;
      snapped.y = (int)(coords.y/(float)gridSize.height)*gridSize.height;
      return snapped;
   }
}

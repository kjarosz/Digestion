package Core.LevelEditor.Control.Commands;

import java.awt.event.MouseEvent;

import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.LevelModel;

public abstract class LevelModelCommand {

   protected LevelModel mLevel;
   protected EditorSettings mEditor;
   protected DrawerSettings mDrawer;
   
   public LevelModelCommand(LevelModel level, EditorSettings editor, DrawerSettings drawer) {
      mLevel = level;
      mEditor = editor;
      mDrawer = drawer;
   }
   
   public abstract void perform(MouseEvent event);
}

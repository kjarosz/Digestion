package Core.LevelEditor.Control.Commands;

import java.awt.event.MouseEvent;

import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Settings.DrawerSettings;
import Core.LevelEditor.Settings.EditorSettings;

public class RemoveEntity extends LevelModelCommand {

   public RemoveEntity(LevelModel level, EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
   }
   
   @Override
   public void perform(MouseEvent event) {
      mLevel.removeEntity(event.getPoint());
   }

}

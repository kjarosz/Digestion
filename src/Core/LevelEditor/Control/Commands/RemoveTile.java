package Core.LevelEditor.Control.Commands;

import java.awt.event.MouseEvent;

import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.LevelModel;

public class RemoveTile extends LevelModelCommand {

   public RemoveTile(LevelModel level, EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
   }
   
   @Override
   public void perform(MouseEvent event) {
      mLevel.removeEntity(event.getPoint());
   }

}

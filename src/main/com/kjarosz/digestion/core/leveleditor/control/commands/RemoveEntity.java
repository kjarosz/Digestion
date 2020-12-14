package com.kjarosz.digestion.core.leveleditor.control.commands;

import java.awt.event.MouseEvent;

import com.kjarosz.digestion.core.leveleditor.models.LevelModel;
import com.kjarosz.digestion.core.leveleditor.settings.DrawerSettings;
import com.kjarosz.digestion.core.leveleditor.settings.EditorSettings;

public class RemoveEntity extends LevelModelCommand {

   public RemoveEntity(LevelModel level, EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
   }
   
   @Override
   public void perform(MouseEvent event) {
      mLevel.removeEntity(event.getPoint());
   }

}

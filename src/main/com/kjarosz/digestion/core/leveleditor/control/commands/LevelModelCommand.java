package com.kjarosz.digestion.core.leveleditor.control.commands;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.kjarosz.digestion.core.leveleditor.models.LevelModel;
import com.kjarosz.digestion.core.leveleditor.settings.DrawerSettings;
import com.kjarosz.digestion.core.leveleditor.settings.EditorSettings;

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
      Dimension gridSize = mDrawer.getGridSize();
      Point snapped = new Point(coords);
      snapped.x = (int)(coords.x/(float)gridSize.width)*gridSize.width;
      snapped.y = (int)(coords.y/(float)gridSize.height)*gridSize.height;
      return snapped;
   }
}

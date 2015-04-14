package Core.LevelEditor.Control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Core.LevelEditor.Control.Commands.AddTile;
import Core.LevelEditor.Control.Commands.LevelModelCommand;
import Core.LevelEditor.Control.Commands.RemoveTile;
import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.EditorSettings.EditorMode;
import Core.LevelEditor.Models.LevelModel;

public class ContentPanelControl implements MouseListener, MouseMotionListener {
   private EditorSettings mEditorSettings;
   private LevelModel mLevelModel;
   private DrawerSettings mDrawerSettings;
   
   private LevelModelCommand mAddTileAction;
   private LevelModelCommand mRemoveTileAction;
   
   public ContentPanelControl(EditorSettings editorSettings, 
         LevelModel levelModel, DrawerSettings drawerSettings) {
      mEditorSettings = editorSettings;
      mLevelModel = levelModel;
      mDrawerSettings = drawerSettings;
      
      makeCommands();
   }
   
   private void makeCommands() {
      mAddTileAction = new AddTile(
            mLevelModel, mEditorSettings, mDrawerSettings
      );
      
      mRemoveTileAction = new RemoveTile(
            mLevelModel, mEditorSettings, mDrawerSettings
      );
   }

   @Override
   public void mousePressed(MouseEvent event) {
      LevelModelCommand command = getCommand(event.getButton());
      if(command != null) {
         command.perform(event);
      }
   }
   
   private LevelModelCommand getCommand(int mouseButton) {
      if(mEditorSettings.getEditorMode() == EditorMode.TILING) {
         switch(mouseButton) 
         {
            case MouseEvent.BUTTON1: return mAddTileAction;
            case MouseEvent.BUTTON3: return mRemoveTileAction;
         }
      }
      return null;
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      mousePressed(event);
   }

   /* ********************************************************************** */
   /*                              UNUSED                                    */
   /* ********************************************************************** */
   @Override public void mouseClicked(MouseEvent arg0) {}
   @Override public void mouseReleased(MouseEvent arg0) {}
   @Override public void mouseEntered(MouseEvent arg0) {}
   @Override public void mouseExited(MouseEvent arg0) {}
   @Override public void mouseMoved(MouseEvent arg0) {}
}

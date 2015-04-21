package Core.LevelEditor.Control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Core.LevelEditor.Control.Commands.AddEntity;
import Core.LevelEditor.Control.Commands.ChangeEntityCommand;
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
   
   private LevelModelCommand mAddEntityAction;
   private LevelModelCommand mChangeEntityAction;
   private LevelModelCommand mRemoveEntityAction;
   
   private LevelModelCommand mCurrentCommand;
   
   public ContentPanelControl(EditorSettings editorSettings, 
         LevelModel levelModel, DrawerSettings drawerSettings) {
      mEditorSettings = editorSettings;
      mLevelModel = levelModel;
      mDrawerSettings = drawerSettings;
      
      makeCommands();
   }
   
   private void makeCommands() {
      mChangeEntityAction = new ChangeEntityCommand(
            mLevelModel, mEditorSettings, mDrawerSettings
      );
      
      mAddEntityAction = new AddEntity(
            (ChangeEntityCommand)mChangeEntityAction,
            mLevelModel, mEditorSettings, mDrawerSettings
      );
      
      mRemoveEntityAction = new RemoveTile(
            mLevelModel, mEditorSettings, mDrawerSettings
      );
   }

   @Override
   public void mousePressed(MouseEvent event) {
      LevelModelCommand cmd = getCommand(event.getButton());
      
      if(cmd != null) {
         mCurrentCommand = cmd;
      }
      
      if(mCurrentCommand != null) {
         mCurrentCommand.perform(event);
      }
   }
   
   @Override 
   public void mouseReleased(MouseEvent event) {
      LevelModelCommand cmd = getCommand(event.getButton());
      if(cmd == mCurrentCommand) {
         mCurrentCommand = null;
      }
   }
   
   private LevelModelCommand getCommand(int mouseButton) {
      if(mEditorSettings.getEditorMode() == EditorMode.OBJECTS) {
         switch(mouseButton) 
         {
            case MouseEvent.BUTTON1: return mAddEntityAction;
            case MouseEvent.BUTTON3: return mRemoveEntityAction;
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
   @Override public void mouseEntered(MouseEvent arg0) {}
   @Override public void mouseExited(MouseEvent arg0) {}
   @Override public void mouseMoved(MouseEvent arg0) {}
}

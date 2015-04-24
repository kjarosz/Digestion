package Core.LevelEditor.Control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Core.LevelEditor.Control.Commands.AddEntity;
import Core.LevelEditor.Control.Commands.ChangeEntityCommand;
import Core.LevelEditor.Control.Commands.LevelModelCommand;
import Core.LevelEditor.Control.Commands.NullCommand;
import Core.LevelEditor.Control.Commands.RemoveEntity;
import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Settings.DrawerSettings;
import Core.LevelEditor.Settings.EditorSettings;
import Core.LevelEditor.Settings.EditorSettings.EditorMode;

public class ContentPanelControl implements MouseListener, MouseMotionListener {
   private EditorSettings mEditorSettings;
   private LevelModel mLevelModel;
   private DrawerSettings mDrawerSettings;
   
   private LevelModelCommand mAddEntityAction;
   private LevelModelCommand mChangeEntityAction;
   private LevelModelCommand mRemoveEntityAction;
   private LevelModelCommand mNullCommand;
   
   private int mPressedButton = -1;
   
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
      
      mRemoveEntityAction = new RemoveEntity(
            mLevelModel, mEditorSettings, mDrawerSettings
      );
      
      mNullCommand = new NullCommand();
   }

   @Override
   public void mousePressed(MouseEvent event) {
      mPressedButton = event.getButton();
      LevelModelCommand command = getPressCommand();
      command.perform(event);
   }
   
   private LevelModelCommand getPressCommand() {
      if(mEditorSettings.getEditorMode() == EditorMode.OBJECTS) {
         switch(mPressedButton) 
         {
            case MouseEvent.BUTTON1: return mAddEntityAction;
            case MouseEvent.BUTTON3: return mRemoveEntityAction;
         }
      }
      return mNullCommand;
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      LevelModelCommand command = getDragCommand();
      command.perform(event);
   }
   
   private LevelModelCommand getDragCommand() {
      if(mEditorSettings.getEditorMode() == EditorMode.OBJECTS) {
         switch(mPressedButton)
         {
            case MouseEvent.BUTTON1: return mChangeEntityAction;
            case MouseEvent.BUTTON3: return mRemoveEntityAction;
         }
      }
      return mNullCommand;
   }

   /* ********************************************************************** */
   /*                              UNUSED                                    */
   /* ********************************************************************** */
   @Override public void mouseReleased(MouseEvent event) {}
   @Override public void mouseClicked(MouseEvent arg0) {}
   @Override public void mouseEntered(MouseEvent arg0) {}
   @Override public void mouseExited(MouseEvent arg0) {}
   @Override public void mouseMoved(MouseEvent arg0) {}
}

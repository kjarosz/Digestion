package Core.LevelEditor.Control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.LevelModel;


public class ContentPanelControl implements MouseListener {
   private EditorSettings mEditorSettings;
   private LevelModel mLevelModel;
   
   public ContentPanelControl(EditorSettings editorSettings, LevelModel levelModel) {
      mEditorSettings = editorSettings;
      mLevelModel = levelModel;
   }

   @Override
   public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mousePressed(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseReleased(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override public void mouseEntered(MouseEvent arg0) {}
   @Override public void mouseExited(MouseEvent arg0) {}
}

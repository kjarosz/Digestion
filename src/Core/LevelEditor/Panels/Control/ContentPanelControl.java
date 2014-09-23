package Core.LevelEditor.Panels.Control;

import Core.LevelEditor.LevelEditor;
import java.awt.event.*;

public class ContentPanelControl implements 
        ActionListener, FocusListener, MouseListener, MouseMotionListener {
   private boolean mMouseInside = false;
   private int mButton = 0;
   private LevelEditor mEditor;
   
   public ContentPanelControl(LevelEditor editor) {
      mEditor = editor;
   }

   @Override
   public void mousePressed(MouseEvent e) {
      mButton = e.getButton();
      mEditor.processClick(e, mButton);
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      if(mMouseInside)
         mEditor.processClick(e, mButton);
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      if(mMouseInside)
         mEditor.processClick(e, mButton);
   }

   @Override
   public void mouseEntered(MouseEvent e) {
      mMouseInside = true;
   }

   @Override
   public void mouseExited(MouseEvent e) {
      mMouseInside = false;
   }

   @Override
   public void mouseClicked(MouseEvent e) {}
   @Override
   public void mouseMoved(MouseEvent e) {}
   @Override
   public void actionPerformed(ActionEvent e) {}
   @Override
   public void focusGained(FocusEvent e) {}
   @Override
   public void focusLost(FocusEvent e) {}
}

package Core.LevelEditor.Panels.Control;

import Core.LevelEditor.LevelEditor;
import java.awt.Point;
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
   public void actionPerformed(ActionEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void focusGained(FocusEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void focusLost(FocusEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
      
   private MouseEvent translate(MouseEvent e) {
      Point offset = new Point();

      Point coords = e.getPoint();
      coords.x = coords.x + offset.x;
      coords.y = coords.y + offset.y;

      return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(),
            e.getModifiers(), coords.x, coords.y, e.getClickCount(), 
            e.isPopupTrigger(), e.getButton());
   }

   @Override
   public void mousePressed(MouseEvent e) {
      MouseEvent translatedEvent = translate(e);
      mButton = e.getButton();
      mEditor.processClick(translatedEvent, mButton);
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      MouseEvent translatedEvent = translate(e);
      if(mMouseInside)
         mEditor.processClick(translatedEvent, mButton);
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      MouseEvent translatedEvent = translate(e);
      if(mMouseInside)
         mEditor.processClick(translatedEvent, mButton);
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
   
}

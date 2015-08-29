package Core.Events;

import java.awt.Point;

public class MouseEvent extends Event {
   public static enum MouseButton {
      LEFT, MIDDLE, RIGHT
   }
   
   public static enum MouseAction {
      MOVE, DRAG, PRESSED, RELEASED
   }
   
   public MouseButton mButton;
   public MouseAction mAction;
   public Point mPosition;
   
   public MouseEvent(MouseButton button, MouseAction action, Point position) {
      mButton = button;
      mAction = action;
      mPosition = position;
   }
}

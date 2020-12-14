package com.kjarosz.digestion.core.events;


public class KeyEvent extends Event {
   public static enum KeyAction {
      PRESSED, RELEASED, TYPED
   }
   
   public KeyAction mKeyAction;
   public int mKeyCode;
   
   public KeyEvent(KeyAction action, int keyCode) {
      mKeyAction = action;
      mKeyCode = keyCode;
   }
}

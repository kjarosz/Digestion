package com.kjarosz.digestion.core.events;

import java.awt.Dimension;

public class ScreenEvent extends Event {
   public static enum ScreenAction {
      FOCUS_GAINED, FOCUS_LOST, 
      HIDDEN, MOVED, RESIZED, SHOWN
   }
   
   public ScreenAction mAction;
   public Dimension mScreenSize;
   
   public ScreenEvent(ScreenAction action, Dimension screenSize) {
      mAction = action;
      mScreenSize = screenSize;
   }
}

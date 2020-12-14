package com.kjarosz.digestion.core.leveleditor.control.commands;

import java.awt.event.MouseEvent;

public class NullCommand extends LevelModelCommand {

   public NullCommand() {
      super(null, null, null);
   }
   
   @Override
   public void perform(MouseEvent event) {
      // Do Nothing      
   }

}

package com.kjarosz.digestion.core.messaging.messages;

import com.kjarosz.digestion.core.messaging.Message;

public class LevelSelector extends Message {
   public String mSelectedName;
   
   public LevelSelector(String selectedName) {
      mSelectedName = selectedName;
   }
}

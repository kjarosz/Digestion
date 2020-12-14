package com.kjarosz.digestion.core.messaging.messages;

import com.kjarosz.digestion.core.messaging.Message;

public class StartLevelLoadMessage extends Message {
   public String mLevelName;
   
   public StartLevelLoadMessage(String levelName) {
      mLevelName = levelName;
   }
}

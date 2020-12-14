package com.kjarosz.digestion.core.messaging.messages;

import com.kjarosz.digestion.core.messaging.Message;

public class LevelLoaderTracker extends Message {
   public static enum LoadingStatus {
      FINISHED, FAILED
   }

   public LoadingStatus mStatus;
   
   public LevelLoaderTracker(LoadingStatus status) {
      mStatus = status;
   }
}

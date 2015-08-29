package Core.Messaging.Messages;

import Core.Messaging.Message;

public class LevelLoaderTracker extends Message {
   public static enum LoadingStatus {
      FINISHED, FAILED
   }

   public LoadingStatus mStatus;
   
   public LevelLoaderTracker(LoadingStatus status) {
      mStatus = status;
   }
}

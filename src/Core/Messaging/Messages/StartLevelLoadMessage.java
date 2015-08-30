package Core.Messaging.Messages;

import Core.Messaging.Message;

public class StartLevelLoadMessage extends Message {
   public String mLevelName;
   
   public StartLevelLoadMessage(String levelName) {
      mLevelName = levelName;
   }
}

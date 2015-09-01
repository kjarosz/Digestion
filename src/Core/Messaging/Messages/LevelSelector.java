package Core.Messaging.Messages;

import Core.Messaging.Message;

public class LevelSelector extends Message {
   public String mSelectedName;
   
   public LevelSelector(String selectedName) {
      mSelectedName = selectedName;
   }
}

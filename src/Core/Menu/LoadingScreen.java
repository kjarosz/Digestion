package Core.Menu;

import static java.lang.Math.random;

import java.awt.Dimension;

import Core.Game.Game;
import Core.Messaging.Message;
import Core.Messaging.Receiver;
import Core.Messaging.Messages.LevelLoaderTracker;
import Core.Messaging.Messages.LevelLoaderTracker.LoadingStatus;
import Core.Messaging.Messages.StartLevelLoadMessage;
import Level.LevelFactory;
import Menu.MenuScreen;

public class LoadingScreen extends MenuScreen implements Receiver {
   private final String LOADING_SCREEN = "resources/Images/loading_screen.png";

   private final String mLevelName = "";
   
   public LoadingScreen(Game game) {
      super(game);
      loadBackground(LOADING_SCREEN);
      mGame.registerReceiver("Level Loader", this);
   }

   @Override
   public String stateName() {
      return "LOADING SCREEN";
   }
   
   @Override
   public void beforeSwitch(Dimension screenSize) {
      super.beforeSwitch(screenSize);
      String levelName = getLevelName();
      StartLevelLoadMessage message = new StartLevelLoadMessage(levelName);
      mGame.publishMessage("Level", message);
   }
   
   private String getLevelName() {
      if(mLevelName.equals("")) {
         String[] levels = LevelFactory.getLevelScripts();
         int randIdx = (int)(random()*100 % levels.length);
         return levels[randIdx];
      } else {
         return mLevelName;
      }
   }
   
   @Override
   public void processMessage(Message message) {
      if(message instanceof LevelLoaderTracker) {
         LevelLoaderTracker levelLoaderMsg = (LevelLoaderTracker)message;
         if(levelLoaderMsg.mStatus == LoadingStatus.FINISHED) {
            mGame.switchToState("LEVEL");
         } else {
            mGame.switchToState("TITLE SCREEN");
         }
      }
   }
}

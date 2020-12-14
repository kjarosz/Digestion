package com.kjarosz.digestion.core.menu;

import static java.lang.Math.random;

import java.awt.Dimension;

import com.kjarosz.digestion.core.game.Game;
import com.kjarosz.digestion.core.messaging.Message;
import com.kjarosz.digestion.core.messaging.Receiver;
import com.kjarosz.digestion.core.messaging.messages.LevelLoaderTracker;
import com.kjarosz.digestion.core.messaging.messages.LevelSelector;
import com.kjarosz.digestion.core.messaging.messages.StartLevelLoadMessage;
import com.kjarosz.digestion.core.messaging.messages.LevelLoaderTracker.LoadingStatus;
import com.kjarosz.digestion.level.LevelFactory;
import com.kjarosz.digestion.menu.MenuScreen;

public class LoadingScreen extends MenuScreen implements Receiver {
   private final String LOADING_SCREEN = "resources/Images/loading_screen.png";

   private String mLevelName = "";
   
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
      } else if(message instanceof LevelSelector) {
         LevelSelector selectorMsg = (LevelSelector)message;
         mLevelName = selectorMsg.mSelectedName;
      }
   }
}

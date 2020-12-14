package com.kjarosz.digestion.core.menu;

import java.awt.Dimension;

import com.kjarosz.digestion.core.game.Game;
import com.kjarosz.digestion.core.messaging.messages.LevelSelector;
import com.kjarosz.digestion.menu.MenuScreen;

public class LevelMenu extends MenuScreen {
   private final String BACKGROUND = "resources/Images/Title.png";
   private final String TEST_LEVEL_BUTTON = "resources/Images/test_level_button.png";
   private final String BACK_BUTTON = "resources/Images/back_button.png";
   
   private Dimension BUTTON_SIZE = new Dimension(600, 75);
   
   
   public LevelMenu(Game game) {
      super(game);
      createWidgets();
      loadBackground(BACKGROUND);
   }
   
   private void createWidgets() {
      createButton(TEST_LEVEL_BUTTON, BUTTON_SIZE, () -> selectTestLevel());
      createButton(BACK_BUTTON, BUTTON_SIZE, () -> mGame.switchToState("SINGLE PLAYER SCREEN"));
   }
   
   public void selectTestLevel() {
      LevelSelector selector = new LevelSelector("Level1.4");
      mGame.publishMessage("Level Loader", selector);
   }

   @Override
   public String stateName() {
      return "LEVEL SELECT";
   }
}

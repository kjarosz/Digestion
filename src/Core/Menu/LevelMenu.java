package Core.Menu;

import java.awt.Dimension;

import Core.Game.Game;
import Menu.MenuScreen;

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
      createButton(TEST_LEVEL_BUTTON, BUTTON_SIZE, () -> System.out.println("Test level"));
      createButton(BACK_BUTTON, BUTTON_SIZE, () -> mGame.switchToState("SINGLE PLAYER SCREEN"));
   }

   @Override
   public String stateName() {
      return "LEVEL SELECT";
   }
}

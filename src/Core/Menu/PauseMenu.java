package Core.Menu;

import java.awt.Dimension;

import Core.Game.Game;
import Menu.MenuScreen;

public class PauseMenu extends MenuScreen {
   private final static String BACKGROUND = "resources/Images/Title.png";
   private final static String RESUME_BUTTON = "resources/Images/resume_button.png";
   private final static String QUIT_BUTTON = "resources/Images/quit_button.png";
   private final static String EXIT_BUTTON = "resources/Images/exit_button.png";
   
   private final Dimension BUTTON_SIZE = new Dimension(600, 75);
   
   public PauseMenu(Game game) {
      super(game);
      loadBackground(BACKGROUND);
      createWidgets();
   }
   
   private void createWidgets() {
      createButton(RESUME_BUTTON, BUTTON_SIZE, () -> mGame.switchToState("LEVEL"));
      createButton(QUIT_BUTTON, BUTTON_SIZE, () -> mGame.switchToState("TITLE SCREEN"));
      createButton(EXIT_BUTTON, BUTTON_SIZE, () -> System.exit(0));
   }

   @Override
   public String stateName() {
      return "PAUSE MENU";
   }

}

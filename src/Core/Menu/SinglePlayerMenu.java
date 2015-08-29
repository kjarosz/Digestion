package Core.Menu;

import java.awt.Dimension;

import Core.Game.Game;
import Menu.MenuScreen;

public class SinglePlayerMenu extends MenuScreen {
   final private String SINGLE_PLAYER_BACKGROUND   = "resources/Images/Title.png";
   final private String START_GAME                 = "resources/Images/start_game_button.png";
   final private String BACK                       = "resources/Images/back_button.png";

	final private Dimension BUTTON_SIZE = new Dimension(600, 50);

	public SinglePlayerMenu(Game game) {
	   super(game);
	   loadBackground(SINGLE_PLAYER_BACKGROUND);
		createWidgets();
	}

	private void createWidgets() {
	   createButton(START_GAME, BUTTON_SIZE, () -> System.out.println("Action"));
	   createButton(BACK, BUTTON_SIZE, () -> mGame.switchToState("TITLE SCREEN"));
	}

   @Override
   public String stateName() {
      return "SINGLE PLAYER SCREEN";
   }
}

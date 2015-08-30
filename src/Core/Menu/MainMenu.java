package Core.Menu;

import java.awt.Dimension;

import Core.Game.Game;
import Menu.MenuScreen;

public class MainMenu extends MenuScreen {
	private final String MAIN_MENU_BACKGROUND = "resources/Images/Title.png";
	private final String SINGLE_PLAYER        = "resources/Images/single_player_button.png";
	private final String LEVEL_EDITOR         = "resources/Images/level_editor_button.png";
	private final String EXIT                 = "resources/Images/exit_button.png";
	
	private final Dimension BUTTON_SIZE = new Dimension(600, 75);

	public MainMenu(Game game) {
	   super(game);
		loadBackground(MAIN_MENU_BACKGROUND);
		createWidgets();
	}
	
	private void createWidgets() {
	   createButton(SINGLE_PLAYER, BUTTON_SIZE, () -> mGame.switchToState("SINGLE PLAYER SCREEN"));
	   createButton(LEVEL_EDITOR, BUTTON_SIZE, () -> mGame.switchToState("LEVEL EDITOR"));
	   createButton(EXIT, BUTTON_SIZE, () -> System.exit(0));
	}

	@Override
	public String stateName() {
	   return "TITLE SCREEN";
	}
}

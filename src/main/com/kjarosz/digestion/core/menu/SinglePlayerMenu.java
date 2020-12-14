package com.kjarosz.digestion.core.menu;

import java.awt.Dimension;

import com.kjarosz.digestion.core.game.Game;
import com.kjarosz.digestion.menu.MenuScreen;

public class SinglePlayerMenu extends MenuScreen {
   final private String SINGLE_PLAYER_BACKGROUND   = "resources/Images/Title.png";
   final private String START_GAME                 = "resources/Images/start_game_button.png";
   final private String LEVEL_SELECT               = "resources/Images/level_select_button.png";
   final private String BACK                       = "resources/Images/back_button.png";

	final private Dimension BUTTON_SIZE = new Dimension(600, 75);

	public SinglePlayerMenu(Game game) {
	   super(game);
	   loadBackground(SINGLE_PLAYER_BACKGROUND);
		createWidgets();
	}

	private void createWidgets() {
	   createButton(START_GAME, BUTTON_SIZE, () -> startNewGame());
	   createButton(LEVEL_SELECT, BUTTON_SIZE, () -> mGame.switchToState("LEVEL SELECT"));
	   createButton(BACK, BUTTON_SIZE, () -> mGame.switchToState("TITLE SCREEN"));
	}

   @Override
   public String stateName() {
      return "SINGLE PLAYER SCREEN";
   }
   
   private void startNewGame() {
      mGame.switchToState("LOADING SCREEN");
   }
}

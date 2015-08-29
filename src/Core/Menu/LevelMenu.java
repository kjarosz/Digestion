package Core.Menu;

import java.awt.Dimension;

import javax.swing.JList;

import Core.Game.Game;
import Level.LevelFactory;
import Menu.MenuScreen;

public class LevelMenu extends MenuScreen {
   private final String BACKGROUND = "resources/Images/Title.png";
   private final String BACK_BUTTON = "resources/Images/back_button.png";
   
   private Dimension BUTTON_SIZE = new Dimension(600, 50);
   
   private JList<String> mLevelList;
   
   public LevelMenu(Game game) {
      super(game);
      createWidgets();
      loadBackground(BACKGROUND);
   }
   
   private void createWidgets() {
      createLevelList();
      createButton(BACK_BUTTON, BUTTON_SIZE, () -> mGame.switchToState("SINGLE PLAYER SCREEN"));
   }

   private void createLevelList() {
      mLevelList = new JList<>(LevelFactory.getLevelScripts());
      mLevelList.setSelectedIndex(0);
   }

   protected String getSelectedLevel() {
      return mLevelList.getSelectedValue();
   }
   
   @Override
   public String stateName() {
      return "LEVEL SELECT";
   }
}

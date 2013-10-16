package Core.Menu;

import Core.Game.Game;
import Menu.MenuScreen;
import Menu.MenuStack;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class SinglePlayerMenu extends MenuScreen implements ActionListener {
   final private String ACTION_START_GAME = "Start Game";
   final private String ACTION_CHOOSE_LEVEL = "Choose Level";
   final private String ACTION_CHOOSE_CHARACTER = "Choose Character";
   final private String ACTION_BACK = "Back";
   final private Dimension BUTTON_SIZE = new Dimension(150, 25);
   
   private Game mGame;
   private MenuStack mStack;
   
   private LevelMenu mLevelMenu;
   private CharacterMenu mCharacterMenu;
   
   public SinglePlayerMenu(Game game, MenuStack stack) {
      super();
      
      mGame = game;
      mStack = stack;
      
      createWidgets();
      
      mLevelMenu = new LevelMenu(mStack);
      mCharacterMenu = new CharacterMenu(mStack);
   }
   
   private void createWidgets() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      add(Box.createVerticalGlue());
      createButton("Start Game", ACTION_START_GAME);
      add(Box.createVerticalStrut(5));
      createButton("Choose Level", ACTION_CHOOSE_LEVEL);
      add(Box.createVerticalStrut(5));
      createButton("Choose Character", ACTION_CHOOSE_CHARACTER);
      add(Box.createVerticalStrut(5));
      createButton("Back", ACTION_BACK);
      add(Box.createVerticalGlue());
   }
   
   private void createButton(String name, String command) {
      JButton button = new JButton(name);
      button.setMinimumSize(BUTTON_SIZE);
      button.setMaximumSize(BUTTON_SIZE);
      button.setAlignmentX(JButton.CENTER_ALIGNMENT);
      button.setActionCommand(command);
      button.addActionListener(this);
      add(button);
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      String action = e.getActionCommand();
      
      if(action.compareTo(ACTION_START_GAME) == 0) {
         // Return stack to the Main Menu
         mStack.popScreen();
         
         Thread thread = 
         new Thread(
            new Runnable() {
                        @Override
                        public void run() {
                           mGame.startLevel(mLevelMenu.getSelectedLevel());
                        }
                     });
         thread.start();
         
      } else if(action.compareTo(ACTION_CHOOSE_LEVEL) == 0) {
         mStack.pushScreen(mLevelMenu);
      } else if(action.compareTo(ACTION_CHOOSE_CHARACTER) == 0) {
         mStack.pushScreen(mCharacterMenu);
      } else if(action.compareTo(ACTION_BACK) == 0) {
         mStack.popScreen();
      }
   }
   
}

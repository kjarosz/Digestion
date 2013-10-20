package Core.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import Core.Game.Game;
import Menu.MenuScreen;
import Menu.MenuStack;

public class PauseMenu extends MenuScreen implements ActionListener {
   private final String ACTION_RESUME = "Resume";
   private final String ACTION_OPTIONS = "Options";
   private final String ACTION_QUIT = "Quit";
   
   private Game mGame;
   
   private MenuStack mStack;
   
   public PauseMenu(Game game, MenuStack stack) {
      mStack = stack;
      mGame = game;
      
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      
      JButton resumeButton = new JButton("Resume");
      resumeButton.setActionCommand(ACTION_RESUME);
      resumeButton.addActionListener(this);
      add(resumeButton);
      
      JButton optionsButton = new JButton("Options");
      optionsButton.setActionCommand(ACTION_OPTIONS);
      optionsButton.addActionListener(this);
      add(optionsButton);
      
      JButton quitButton = new JButton("Quit");
      quitButton.setActionCommand(ACTION_QUIT);
      quitButton.addActionListener(this);
      add(quitButton);
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      
      if(command.compareTo(ACTION_RESUME) == 0) {
         mGame.resume();
         mStack.popScreen();
      } else if(command.compareTo(ACTION_OPTIONS) == 0) {
         
      } else if(command.compareTo(ACTION_QUIT) == 0) {
         mStack.popScreen();
         mGame.quitToMenu();
      }
   }
}

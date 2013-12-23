package Core.Menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import Core.Game.Game;
import Menu.MenuScreen;
import Menu.MenuStack;

public class PauseMenu extends MenuScreen implements ActionListener {
   private final String ACTION_RESUME = "Resume";
   private final String ACTION_OPTIONS = "Options";
   private final String ACTION_QUIT = "Quit";
   
   private final Dimension BUTTON_SIZE = new Dimension(110, 25);
   
   private Game mGame;
   
   private MenuStack mStack;
   
   public PauseMenu(Game game, MenuStack stack) {
      mStack = stack;
      mGame = game;
      
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

      add(Box.createVerticalGlue());
      addButton("Resume", ACTION_RESUME);
      add(Box.createVerticalStrut(5));
      addButton("Options", ACTION_OPTIONS);
      add(Box.createVerticalStrut(5));
      addButton("Quit", ACTION_QUIT);
      add(Box.createVerticalGlue());
   }
   
   private void addButton(String name, String command) {
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

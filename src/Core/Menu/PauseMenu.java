package Core.Menu;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import Core.Game.Game;
import Menu.MenuScreen;
import Menu.MenuStack;

public class PauseMenu extends MenuScreen {
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
      addButton("Resume", (e) -> resumeGame());
      add(Box.createVerticalStrut(5));
      addButton("Quit", (e) -> quitToMenu());
      add(Box.createVerticalGlue());
   }
   
   private void addButton(String name, ActionListener command) {
      JButton button = new JButton(name);
      button.setMinimumSize(BUTTON_SIZE);
      button.setMaximumSize(BUTTON_SIZE);
      button.setAlignmentX(JButton.CENTER_ALIGNMENT);
      button.addActionListener(command);
      add(button);
   }
   
   private void resumeGame() {
      mGame.resume();
      mStack.popScreen();
   }
   
   private void quitToMenu() {
      mGame.quitToMenu();
      mStack.popScreen();
   }
}

package Core.Menu;

import Core.Game.Game;
import Menu.MenuScreen;
import Menu.MenuStack;
import Util.ErrorLog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class PauseMenu extends MenuScreen implements ActionListener {
   private final String ACTION_RESUME = "Resume";
   private final String ACTION_OPTIONS = "Options";
   private final String ACTION_QUIT = "Quit";
   
   private ErrorLog mErrorLog;
   private Game mGame;
   
   private MenuStack mStack;
   private boolean mVisible;
   
   public PauseMenu(Game game, MenuStack stack) {
      mStack = stack;
      
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
   
   public void display() {
      if(mVisible)
         return;
      
      mVisible = true;
      mStack.pushScreen(this);
   }
   
   public void remove() {
      if(!mVisible)
         return;
      
      mVisible = false;
      
      while(mStack.currentScreen() != this)
         mStack.popScreen();
      
      mStack.popScreen();
   }

   public void previousScreen() {
      if(mStack.currentScreen() == this)
         mGame.unpause();
      
      mStack.popScreen();
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      
      if(command.compareTo(ACTION_RESUME) == 0) {
         mGame.unpause();
      } else if(command.compareTo(ACTION_OPTIONS) == 0) {
         
      } else if(command.compareTo(ACTION_QUIT) == 0) {
        //mGame.quitLevel();
         mGame.unpause();
      }
   }
}

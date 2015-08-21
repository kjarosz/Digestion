package Core.Menu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Core.Game.Game;
import Core.LevelEditor.LevelEditor;
import Menu.MenuScreen;
import Menu.MenuStack;

public class MainMenu extends MenuScreen implements ActionListener {
   final private String MAIN_MENU_BACKGROUND = "resources/Images/Title.png";

   final private String ACTION_SINGLE_PLAYER = "Single Player";
   final private String ACTION_MULTI_PLAYER = "Multi Player";
   final private String ACTION_LEVEL_EDITOR = "Level Editor";
   final private String ACTION_CREDITS = "Credits";
   final private String ACTION_EXIT = "Exit";
   final private Dimension BUTTON_SIZE = new Dimension(110, 25);
   
   private MenuStack mStack;
   
   private SinglePlayerMenu mSinglePlayerMenu;
   private CreditsMenu mCreditsMenu;
   
   private BufferedImage mBackground;
   
   public MainMenu(Game game, MenuStack stack) {
      mStack = stack;
      
      loadBackground();
      createWidgets();
      createSubmenus(game);
   }
   
   private void loadBackground() {
      try {
         File bckgrFile = new File(MAIN_MENU_BACKGROUND);
         mBackground = ImageIO.read(bckgrFile);
      } catch(IOException ex) {
         JOptionPane.showMessageDialog(this, 
               ex.getMessage(), 
               "Error Loading Menu Background", 
               JOptionPane.ERROR_MESSAGE);
      }
   }
   
   private void createWidgets() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      add(Box.createVerticalGlue());
      addButton("Single Player", ACTION_SINGLE_PLAYER);
      add(Box.createVerticalStrut(5));
      addButton("Multi Player", ACTION_MULTI_PLAYER);
      add(Box.createVerticalStrut(5));
      addButton("Level Editor", ACTION_LEVEL_EDITOR);
      add(Box.createVerticalStrut(5));
      addButton("Credits", ACTION_CREDITS);
      add(Box.createVerticalStrut(5));
      addButton("Exit", ACTION_EXIT);
      add(Box.createVerticalGlue());
   }
   
   private void addButton(String text, String actionCommand) {
      JButton button = new JButton(text);
      button.setMinimumSize(BUTTON_SIZE);
      button.setMaximumSize(BUTTON_SIZE);
      button.setAlignmentX(JButton.CENTER_ALIGNMENT);
      button.setActionCommand(actionCommand);
      button.addActionListener(this);
      add(button);
   }
   
   private void createSubmenus(Game game) {
      mSinglePlayerMenu = new SinglePlayerMenu(game, mStack);
      mCreditsMenu = new CreditsMenu(mStack);
   }
   
   @Override
   public void actionPerformed(java.awt.event.ActionEvent e) {
      String action = e.getActionCommand();
      
      if(action.compareTo(ACTION_SINGLE_PLAYER) == 0) {
         mStack.pushScreen(mSinglePlayerMenu);
      } else if(action.compareTo(ACTION_MULTI_PLAYER) == 0) {
         JOptionPane.showMessageDialog(this, "This functions is not implemented yet");
      } else if(action.compareTo(ACTION_LEVEL_EDITOR) == 0) {
         loadLevelEditor();
      } else if(action.compareTo(ACTION_CREDITS) == 0) {
         mStack.pushScreen(mCreditsMenu);
      } else if(action.compareTo(ACTION_EXIT) == 0) {
         System.exit(0);
      }
   }
   
   private void loadLevelEditor() {
      LevelEditor editor = new LevelEditor(mStack); 
      mStack.pushScreen(editor);
   }
  
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(mBackground, 0, 0, getWidth(), getHeight(), null);
   }
}

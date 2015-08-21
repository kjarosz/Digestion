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
import Menu.Widgets.Button;

public class MainMenu extends MenuScreen {
   final private String MAIN_MENU_BACKGROUND = "resources/Images/Title.png";
   final private String SINGLE_PLAYER        = "resources/Images/single_player_button.png";
   final private String LEVEL_EDITOR         = "resources/Images/level_editor_button.png";
   final private String EXIT                 = "resources/Images/exit_button.png";

   final private Dimension BUTTON_SIZE = new Dimension(400, 50);
   
   private MenuStack mStack;
   
   private SinglePlayerMenu mSinglePlayerMenu;
   
   private BufferedImage mBackground;
   
   public MainMenu(Game game, MenuStack stack) {
      mStack = stack;
      
      loadBackground();
      createSubmenus(game);
      createWidgets();
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
      addButton(SINGLE_PLAYER,   e -> mStack.pushScreen(mSinglePlayerMenu));
      add(Box.createVerticalStrut(5));
      addButton(LEVEL_EDITOR,    e -> loadLevelEditor());
      add(Box.createVerticalStrut(5));
      addButton(EXIT,            e -> System.exit(0));
      add(Box.createVerticalGlue());
   }
   
   private void addButton(String text, ActionListener listener) {
      Button button = new Button(text);
      button.setMinimumSize(BUTTON_SIZE);
      button.setPreferredSize(BUTTON_SIZE);
      button.setMaximumSize(BUTTON_SIZE);
      button.setAlignmentX(JButton.CENTER_ALIGNMENT);
      button.addActionListener(listener);
      add(button);
   }
   
   private void createSubmenus(Game game) {
      mSinglePlayerMenu = new SinglePlayerMenu(game, mStack);
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

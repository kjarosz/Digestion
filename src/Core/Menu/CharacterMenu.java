package Core.Menu;

import Menu.MenuScreen;
import Menu.MenuStack;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CharacterMenu extends MenuScreen implements ActionListener {
   final private String ACTION_BACK = "Back";
   final private Dimension BUTTON_SIZE = new Dimension(110, 25);
   
   private MenuStack mStack;
   
   public CharacterMenu(MenuStack stack) {
      super();
      
      mStack = stack;
      
      createWidgets();
   }
   
   private void createWidgets() {
      createButton("Back", ACTION_BACK);
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
      
      if(action.compareTo(ACTION_BACK) == 0) {
         mStack.popScreen();
      }
   }

}

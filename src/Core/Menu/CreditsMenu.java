package Core.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Menu.MenuScreen;
import Menu.MenuStack;

public class CreditsMenu extends MenuScreen implements ActionListener {
   final private String ACTION_BACK = "Back";
   
   private MenuStack mStack;   
   
   public CreditsMenu(MenuStack stack) {
      super();
      
      mStack = stack;
      
      createWidgets();
   }
   
   private void createWidgets() {
      JButton back = new JButton("Back");
      back.setActionCommand(ACTION_BACK);
      back.addActionListener(this);
      add(back);
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      String action = e.getActionCommand();
      
      if(action.compareTo(ACTION_BACK) == 0) {
         mStack.popScreen();
      }
   }
}

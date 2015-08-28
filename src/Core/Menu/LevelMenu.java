package Core.Menu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import Level.LevelFactory;
import Menu.MenuScreen;
import Menu.MenuStack;

public class LevelMenu extends MenuScreen implements ActionListener {
   private final String ACTION_BACK = "Back";
   
   private MenuStack mStack;
   
   private JList<String> mLevelList;
   private JScrollPane mListScroller;
   
   public LevelMenu(MenuStack stack) {
      mStack = stack;
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BorderLayout());
      createLevelList();
      createBackButton();
   }
   
   private void createLevelList() {
      mLevelList = new JList<>(LevelFactory.getLevelScripts());
      mLevelList.setSelectedIndex(0);
      mListScroller = new JScrollPane(mLevelList);
      add(mListScroller, BorderLayout.CENTER);
   }
   
   private void createBackButton() {
      JButton backButton = new JButton("Back");
      backButton.setActionCommand("Back");
      backButton.addActionListener(this);
      add(backButton, BorderLayout.SOUTH);
   }

   protected String getSelectedLevel() {
      return mLevelList.getSelectedValue();
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      
      if(command.compareTo(ACTION_BACK) == 0) {
         mStack.popScreen();
      }
   }
   
   
}

package Core.Menu;

import Level.LevelFactory;
import Level.LevelLoadingScript;
import Menu.MenuScreen;
import Menu.MenuStack;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

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
      mLevelList = new JList<>(LevelFactor.getLevelScripts());
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

   protected LevelLoadingScript getSelectedLevel() {
      return mLevelScriptLoader.loadLevelScript(mLevelList.getSelectedValue());
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      
      if(command.compareTo(ACTION_BACK) == 0) {
         mStack.popScreen();
      }
   }
   
   
}

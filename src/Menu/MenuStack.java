package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import javax.swing.JPanel;

public class MenuStack extends JPanel {
   private LinkedList<MenuScreen> mScreenStack;
   
   private Color mBackgroundColor;
   
   public MenuStack() {
      mScreenStack = new LinkedList<>();
      setLayout(new BorderLayout());
   }
   
   public void pushScreen(MenuScreen screen) {
      if(!mScreenStack.isEmpty()) 
         remove(mScreenStack.getLast());
      mScreenStack.add(screen);
      
      add(mScreenStack.getLast(), BorderLayout.CENTER);
      revalidate();
      repaint();
   }
   
   public void popScreen() {
      if(mScreenStack.isEmpty())
         return;
      
      remove(mScreenStack.getLast());
      mScreenStack.removeLast();
      
      add(mScreenStack.getLast(), BorderLayout.CENTER);
      repaint();
   }
   
   public MenuScreen currentScreen() {
      return (MenuScreen)getComponent(getComponentCount()-1);
   }
}

package Menu;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class MenuStack extends JPanel implements ComponentListener {
   private LinkedList<MenuScreen> mScreenStack;
   
   public MenuStack() {
      mScreenStack = new LinkedList<>();
      setLayout(new BorderLayout());
   }
   
   public void emptyStack() {
	   while(!mScreenStack.isEmpty()) {
		   popScreen();
	   }
   }
   
   public void pushScreen(MenuScreen screen) {
      if(!mScreenStack.isEmpty()) 
         remove(mScreenStack.getLast());
      mScreenStack.add(screen);
      
      add(mScreenStack.getLast(), BorderLayout.CENTER);
      update();
   }
   
   public void popScreen() {
      if(mScreenStack.isEmpty())
         return;
      
      remove(mScreenStack.getLast());
      mScreenStack.removeLast();
      
      add(mScreenStack.getLast(), BorderLayout.CENTER);
      update();
   }

   private void update()    {
      revalidate();
      repaint();
   }
   
   public MenuScreen currentScreen() {
      return (MenuScreen)getComponent(getComponentCount()-1);
   }
   
   @Override
   public void componentResized(ComponentEvent arg0) {
      update();
   }

   @Override
   public void componentHidden(ComponentEvent arg0) {}

   @Override
   public void componentMoved(ComponentEvent arg0) { }

   @Override
   public void componentShown(ComponentEvent arg0) {   }
}

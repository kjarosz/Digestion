package Core.Menu;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import Menu.MenuScreen;

public class LoadingScreen extends MenuScreen {
   public LoadingScreen() {
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      add(Box.createVerticalGlue());

      JLabel loadingText = new JLabel("Loading...");
      loadingText.setAlignmentX(CENTER_ALIGNMENT);
      loadingText.setSize(150, 25);
      
      add(loadingText);
      
      add(Box.createVerticalGlue());
   }
}

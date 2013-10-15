package Menu;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MenuScreen extends JPanel {
   public MenuScreen() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      setOpaque(false);
   }
   
   public void addWidget(Component widget) {
      add(widget);
   }
   
   public void removeWidget(Component widget) {
      remove(widget);
   }
}

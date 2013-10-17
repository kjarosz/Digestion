package Menu;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MenuScreen extends JPanel {
   public MenuScreen() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      setBackground(Color.BLACK);
   }
   
   public void addWidget(Component widget) {
      add(widget);
   }
   
   public void removeWidget(Component widget) {
      remove(widget);
   }
}

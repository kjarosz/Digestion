package Core.LevelEditor.Tabs;

import Level.Level;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsTab extends JPanel implements ActionListener {
   private Level mLevel;
   
   public SettingsTab(Level level) {
      mLevel = level;
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      createNameField();
      add(Box.createVerticalStrut(5));
      createBackgroundField();
      add(Box.createVerticalGlue());
   }
   
   private void createNameField() {
      JPanel namePanel = new JPanel();
      namePanel.add(new JLabel("Name:"));
      JTextField nameField = new JTextField(10);
      nameField.setText(mLevel.name);
      namePanel.add(nameField);
      add(namePanel);
   }
   
   private void createBackgroundField() {
      
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      
   }

}

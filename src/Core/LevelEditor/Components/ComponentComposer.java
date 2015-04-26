package Core.LevelEditor.Components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Core.LevelEditor.Settings.EntityComposerSettings;

public class ComponentComposer extends JPanel {
   private EntityComposerSettings mEntityComposerSettings;
   
   public ComponentComposer(EntityComposerSettings composerSettings) {
      mEntityComposerSettings = composerSettings;
      
      createWidgets();
      
      mEntityComposerSettings.addPropertyChangeListener(createSettingsListener());
   }
   
   private void createWidgets() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      displaySelectedComponent();
   }
   
   private void displaySelectedComponent() {
      removeAll();
   }
   
   private PropertyChangeListener createSettingsListener() {
      return new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent e) {
            if("component selected".equals(e.getPropertyName())) {
               displaySelectedComponent();
            }
         }
      };
   }
}

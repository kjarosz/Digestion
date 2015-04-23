package Core.LevelEditor.Components;

import javax.swing.JPanel;

import Core.LevelEditor.Settings.EntityComposerSettings;

public class ComponentComposer extends JPanel {
   private EntityComposerSettings mEntityComposerSettings;
   
   public ComponentComposer(EntityComposerSettings composerSettings) {
      mEntityComposerSettings = composerSettings;
      
      createWidgets();
   }
   
   private void createWidgets() {
      
   }
}

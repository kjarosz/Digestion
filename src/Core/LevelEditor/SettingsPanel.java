package Core.LevelEditor;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.EditorSettings.EditorMode;
import Core.LevelEditor.Tabs.EntitiesTab;

public class SettingsPanel extends JTabbedPane {   
	public SettingsPanel(EditorSettings editorSettings) {
      EntitiesTab objectsTab = new EntitiesTab(editorSettings);
      add(objectsTab, "Entities");
      
      editorSettings.setEditorMode(EditorMode.OBJECTS);
      addChangeListener(createTabListener(editorSettings));
	}
	
	private ChangeListener createTabListener(EditorSettings settings) {
	   JTabbedPane me = this;
	   return new ChangeListener() {
	      @Override
	      public void stateChanged(ChangeEvent e) {
	         switch(me.getSelectedIndex())
	         {
	         case 0: // Entities
	            settings.setEditorMode(EditorMode.OBJECTS);
	            break;
	         case 1: // Pathfinding
	            
	            break;
            default:
               return;
	         }
	      }
	   };
	}
}

package Core.LevelEditor;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.EditorSettings.EditorMode;
import Core.LevelEditor.Tabs.EntityTab;
import Core.LevelEditor.Tabs.TilesTab;

public class SettingsPanel extends JTabbedPane {   
	public SettingsPanel(EditorSettings editorSettings) {
      TilesTab objectsTab = new TilesTab(editorSettings);
      add(objectsTab, "Tiles");
      
      EntityTab entityTab = new EntityTab(editorSettings);
      add(entityTab, "Entities");
      
      
      addChangeListener(createTabListener(editorSettings));
	}
	
	private ChangeListener createTabListener(EditorSettings settings) {
	   JTabbedPane me = this;
	   return new ChangeListener() {
	      @Override
	      public void stateChanged(ChangeEvent e) {
	         switch(me.getSelectedIndex())
	         {
	         case 0: // Tiles
	            settings.setEditorMode(EditorMode.TILING);
	            break;
	         case 1: // Entities
	            settings.setEditorMode(EditorMode.OBJECTS);
	            break;
	         case 2: // Pathfinding
	            
	            break;
            default:
               return;
	         }
	      }
	   };
	}
}

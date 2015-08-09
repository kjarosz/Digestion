package Core.LevelEditor;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Core.LevelEditor.Settings.EditorSettings;
import Core.LevelEditor.Settings.EditorSettings.EditorMode;
import Core.LevelEditor.Tabs.EntityTab;
import Core.LevelEditor.Tabs.ObjectsTab;

public class SettingsPanel extends JTabbedPane {   
	public SettingsPanel(EditorSettings editorSettings) {
	   EntityTab entityTab = new EntityTab(editorSettings);
	   add(entityTab, "Entities");
	   
      ObjectsTab objectsTab = new ObjectsTab(editorSettings);
      add(objectsTab, "Objects");
      
      editorSettings.setEditorMode(EditorMode.ENTITY_EDITOR);
      addChangeListener(createTabListener(editorSettings));
	}
	
	private ChangeListener createTabListener(EditorSettings settings) {
	   return new ChangeListener() {
	      @Override
	      public void stateChanged(ChangeEvent e) {
	         EditorMode mode = getSelectedMode();
	         settings.setEditorMode(mode);
	      }
	   };
	}
	
	private EditorMode getSelectedMode() {
	   return EditorMode.values()[this.getSelectedIndex()];
	}
}

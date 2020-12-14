package com.kjarosz.digestion.core.leveleditor;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.kjarosz.digestion.core.leveleditor.settings.EditorSettings;
import com.kjarosz.digestion.core.leveleditor.settings.EditorSettings.EditorMode;
import com.kjarosz.digestion.core.leveleditor.tabs.ObjectsTab;

public class SettingsPanel extends JTabbedPane {   
	public SettingsPanel(EditorSettings editorSettings) {
      ObjectsTab objectsTab = new ObjectsTab(editorSettings);
      add(objectsTab, "Objects");
      
      editorSettings.setEditorMode(EditorMode.OBJECTS);
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

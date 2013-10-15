package Core.LevelEditor.Panels;

import Core.LevelEditor.Panels.Tabs.ObjectsTab;
import Core.LevelEditor.Panels.Tabs.PathfindingTab;
import Entity.EntityFactory;
import java.awt.Component;
import javax.swing.JTabbedPane;

public class SettingsPanel extends JTabbedPane {
   public enum InputMode {
      Entity,
      Pathfinding
   }
   
	private ObjectsTab mObjectsTab;
	private PathfindingTab mPathfindingTab;
   
	public SettingsPanel(EntityFactory entityFactory) {
      mObjectsTab = new ObjectsTab(entityFactory);
      add(mObjectsTab, "Objects");
      
      mPathfindingTab = new PathfindingTab();
      add(mPathfindingTab, "Pathfinding");
	}
   
   public InputMode getInputMode() {
      Component selectedTab = getSelectedComponent();
      
      if(selectedTab == mObjectsTab)
         return InputMode.Entity;
      else
         return InputMode.Pathfinding;
   }
   
   public String getSelectedEntity() {
      return mObjectsTab.getSelectedEntity();
   }
}

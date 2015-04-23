package Core.LevelEditor.Components;

import javax.swing.JSplitPane;

import Core.LevelEditor.Models.EntityModelList;
import Core.LevelEditor.Settings.EditorSettings;
import Core.LevelEditor.Settings.EntityComposerSettings;

public class EntityComposer extends JSplitPane {
   private EditorSettings mEditorSettings;
   private EntityComposerSettings mComposerSettings;
   
   private EntityModelList mEntityModelList;
   
   public EntityComposer(EditorSettings editorSettings, EntityModelList entityModelList) {
      super(HORIZONTAL_SPLIT);
      
      mEditorSettings = editorSettings;
      mComposerSettings = new EntityComposerSettings();
      mEntityModelList = entityModelList;
      
      createWidgets();
      
      setResizeWeight(0.5);
   }
   
   private void createWidgets() {
      createComponentListPanel();
      createComponentComposer();
   }
   
   private void createComponentListPanel() {
      ComponentList compList = new ComponentList(mEditorSettings);
      add(compList);
   }
   
   private void createComponentComposer() {
      ComponentComposer compComposer = new ComponentComposer(mComposerSettings);
      add(compComposer):
   }
}

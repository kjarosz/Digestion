package Core.LevelEditor.Models;


public class EditorSettings extends AbstractModel {
   public static enum EditorMode {
      ENTITY_EDITOR,
      OBJECTS
   }
   
   private EditorMode editorMode;
   
   private String     selectedEntity;
   
   public EditorSettings() {
      editorMode = EditorMode.OBJECTS;
   }
   
   public EditorMode getEditorMode() {
      return editorMode;
   }
   
   public void setEditorMode(EditorMode mode) {
      if(mode != editorMode) {
         firePropertyChangeEvent("editor_mode", editorMode, mode);
         editorMode = mode;
      }
   }
   
   public String getSelectedEntity() {
      return selectedEntity;
   }
   
   public void setSelectedEntity(String entity) {
      if(!entity.equals(selectedEntity)) {
         firePropertyChangeEvent("selected_entity", selectedEntity, entity);
         selectedEntity = entity;
      }
   }
}

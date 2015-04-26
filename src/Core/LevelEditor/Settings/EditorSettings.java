package Core.LevelEditor.Settings;

import Core.LevelEditor.Models.AbstractModel;
import Core.LevelEditor.Models.EntityModel;


public class EditorSettings extends AbstractModel {
   public static enum EditorMode {
      ENTITY_EDITOR,
      OBJECTS
   }
   
   private EditorMode  editorMode;
   
   private String      selectedEntity;
   private EntityModel selectedEntityModel;
   
   public EditorSettings() {
      editorMode = EditorMode.ENTITY_EDITOR;
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
   
   public EntityModel getSelectedEntityModel() {
      return selectedEntityModel;
   }
   
   public void setSelectedEntityModel(EntityModel model) {
      if(!model.equals(selectedEntityModel)) {
         firePropertyChangeEvent("selected_entity_model", selectedEntityModel, model);
         selectedEntityModel = model;
      }
   }
}

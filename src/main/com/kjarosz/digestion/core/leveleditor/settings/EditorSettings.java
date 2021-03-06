package com.kjarosz.digestion.core.leveleditor.settings;

import com.kjarosz.digestion.core.leveleditor.models.AbstractModel;

public class EditorSettings extends AbstractModel {
   public static enum EditorMode {
      OBJECTS
   }
   
   private EditorMode  editorMode;
   
   private String      selectedEntity;
   
   public EditorSettings() { }
   
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

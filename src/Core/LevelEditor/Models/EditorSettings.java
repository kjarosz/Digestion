package Core.LevelEditor.Models;


public class EditorSettings extends AbstractModel {
   public static enum EditorMode {
      OBJECTS
   }
   
   private EditorMode editorMode;
   
   private String     selectedTile;
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
   
   public String getSelectedTile() {
      return selectedTile;
   }
   
   public void setSelectedTile(String tile) {
      if(!tile.equals(selectedTile)) {
         firePropertyChangeEvent("selected_tile", selectedTile, tile);
         selectedTile = tile;
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

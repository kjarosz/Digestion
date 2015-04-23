package Core.LevelEditor.Settings;

import Core.LevelEditor.Models.AbstractModel;
import Util.Size;

public class DrawerSettings extends AbstractModel {
   private Size      gridSize;
   private boolean   gridEnabled;
   private boolean   backgroundEnabled;
   private boolean   entitiesEnabled;
   private boolean   platformsEnabled;
   
   public DrawerSettings() {
      gridSize = new Size(32, 32);
      gridEnabled = true;
      backgroundEnabled = true;
      entitiesEnabled = true;
      platformsEnabled = true;
   }
   
   public Size getGridSize() {
      return new Size(gridSize);
   }
   
   public void setGridSize(Size size) {
      if(size.width != gridSize.width || size.height != gridSize.height) {
         firePropertyChangeEvent("grid_size", gridSize, size);
         gridSize = size;
      }
   }
   
   public boolean isGridEnabled() {
      return gridEnabled;
   }
   
   public void setGridEnabled(boolean flag) {
      if(flag != gridEnabled) {
         firePropertyChangeEvent("grid_enabled", gridEnabled, flag);
         gridEnabled = flag;
      }
   }
   
   public boolean isBackgroundEnabled() {
      return backgroundEnabled;
   }

   public void setBackgroundEnabled(boolean flag) {
      if(flag != backgroundEnabled) {
         firePropertyChangeEvent("background_flag", backgroundEnabled, flag);
         backgroundEnabled = flag;
      }
   }
   
   public boolean areEntitiesEnabled() {
      return entitiesEnabled;
   }
   
   public void setEntitiesEnabled(boolean flag) {
      if(flag != entitiesEnabled) {
         firePropertyChangeEvent("entities_flag", entitiesEnabled, flag);
         entitiesEnabled = flag;
      }
   }
   
   public boolean arePlatformsEnabled() {
      return platformsEnabled;
   }
   
   public void setPlatformsEnabled(boolean flag) {
      if(flag != platformsEnabled) {
         firePropertyChangeEvent("platforms_flag", platformsEnabled, flag);
         platformsEnabled = flag;
      }
   }
}

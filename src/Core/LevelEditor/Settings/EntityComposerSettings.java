package Core.LevelEditor.Settings;

import Core.LevelEditor.Models.AbstractModel;
import Core.LevelEditor.Models.ComponentModel;

public class EntityComposerSettings extends AbstractModel {
   private ComponentModel selectedModel;
   
   public ComponentModel getSelectedComponentModel() {
      return selectedModel;
   }
   
   public void setSelectedComponentModel(ComponentModel componentModel) {
      if(!componentModel.equals(selectedModel)) {
         firePropertyChangeEvent("component selected", selectedModel, componentModel);
         selectedModel = componentModel;
      }
   }
}

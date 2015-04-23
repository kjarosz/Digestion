package Core.LevelEditor.Components;

import javax.swing.JSplitPane;

import Core.LevelEditor.Models.EntityModelList;

public class EntityComposer extends JSplitPane {
   private EntityModelList mEntityModelList;
   
   public EntityComposer(EntityModelList entityModelList) {
      super(HORIZONTAL_SPLIT);
      
      createWidgets();
   }
   
   private void createWidgets() {
      
   }
}

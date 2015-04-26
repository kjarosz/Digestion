package Core.LevelEditor.Utils;

public class ComponentFactory {
   private static String COMPONENT_LIST[] = {
      "Animated",
      "Controllable",
      "Destructible",
      "Drawable",
      "Movable",
      "Position"
   };
   
   public ComponentFactory() {
      
   }
   
   public String[] getComponentList() {
      return COMPONENT_LIST;
   }
   
   
}

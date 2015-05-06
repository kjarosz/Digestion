package Core.LevelEditor.Utils;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import Core.LevelEditor.Models.ComponentModel;
import Core.LevelEditor.Models.ComponentProperty;

public class ComponentFactory {
   private static String COMPONENT_LIST[] = {
      "Animated",
      "Controllable",
      "Destructible",
      "Drawable",
      "Movable",
      "Position"
   };
   
   private LinkedList<ComponentModel> mComponentModels;
   
   public ComponentFactory() {
      mComponentModels = new LinkedList<>();
      createComponents();
   }
   
   private void createComponents() {
      createPositionComponent();
      createMovableComponent();
      createDrawableComponent();
      createControllableComponent();
   }
   
   private void createPositionComponent() {
      ComponentModel position = new ComponentModel("position");
      position.addProperty(makeProperty("location", new Point(0, 0)));
      mComponentModels.add(position);
   }
   
   private void createMovableComponent() {
      ComponentModel movable = new ComponentModel("movable");
      movable.addProperty(makeProperty("velocity", new Point2D.Float(0, 0)));
      mComponentModels.add(movable);
   }
   
   private void createDrawableComponent() {
      ComponentModel drawable = new ComponentModel("drawable");
      drawable.addProperty(makeProperty("image", new Object()));
      mComponentModels.add(drawable);
   }
   
   private void createControllableComponent() {
      ComponentModel controllable = new ComponentModel("controllable");
      mComponentModels.add(controllable);
   }
   
   private ComponentProperty makeProperty(String name, Object value) {
      ComponentProperty property = new ComponentProperty(name, value);
      return property;
   }
   
   public String[] getComponentList() {
      return COMPONENT_LIST;
   }
   
   
   
   
}

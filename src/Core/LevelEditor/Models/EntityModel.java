package Core.LevelEditor.Models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EntityModel extends AbstractModel implements PropertyChangeListener {
   private String                     mName;
   private LinkedList<ComponentModel> mComponents;
   
   public EntityModel(String name) {
      mName = name;
      mComponents = new LinkedList<>();
   }
   
   public String getName() {
      return mName;
   }
   
   public void setName(String name) {
      if(!mName.equals(name)) {
         firePropertyChangeEvent("name", mName, name);
         mName = name;
      }
   }
   
   public ComponentModel getComponent(String name) {
      for(ComponentModel component: mComponents) {
         if(component.getName().equals(name)) {
            return component;
         }
      }
      throw new RuntimeException("Component not found.");
   }
   
   public List<ComponentModel> getComponents() {
      return Collections.<ComponentModel>unmodifiableList(mComponents);
   }
   
   public void addComponent(ComponentModel component) {
      for(ComponentModel existingComponent: mComponents) {
         if(existingComponent.getName().equals(component.getName())) {
            throw new RuntimeException("Components with duplicate names found.");
         }
      }
      firePropertyChangeEvent("add component", null, component);
      component.addPropertyChangeListener(this);
      mComponents.add(component);      
   }
   
   public void removeComponent(ComponentModel component) {
      if(mComponents.contains(component)) {
         firePropertyChangeEvent("remove component", component, null);
         component.removePropertyChangeListener(this);
         mComponents.remove(component);
      }
   }
   
   @Override
   public void propertyChange(PropertyChangeEvent e) {
      firePropertyChangeEvent("components changed", e.getOldValue(), e.getNewValue());
   }
}

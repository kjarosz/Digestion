package Core.LevelEditor.Models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ComponentModel extends AbstractModel implements PropertyChangeListener {
   private String mName;
   private LinkedList<ComponentProperty> mProperties;
   
   public ComponentModel(String name) {
      mName = name;
      mProperties = new LinkedList<>();
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
   
   public ComponentProperty getProperty(String name) {
      for(ComponentProperty property: mProperties) {
         if(property.getName().equals(name)) {
            return property;
         }
      }
      throw new RuntimeException("Property not found.");
   }
   
   public List<ComponentProperty> getProperties() {
      return Collections.<ComponentProperty>unmodifiableList(mProperties);
   }
   
   public void addProperty(ComponentProperty newProperty) {
      for(ComponentProperty property: mProperties) {
         if(property.getName().equals(newProperty.getName())) {
            throw new RuntimeException("Duplicate properties found.");
         }
      }
      firePropertyChangeEvent("property added", null, newProperty);
      newProperty.addPropertyChangeListener(this);
      mProperties.add(newProperty);
   }
   
   public void removeProperty(ComponentProperty property) {
      if(mProperties.contains(property)) {
         firePropertyChangeEvent("property removed", property, null);
         property.removePropertyChangeListener(this);
         mProperties.remove(property);
      }
      throw new RuntimeException("Property not found");
   }
   
   

   @Override
   public void propertyChange(PropertyChangeEvent event) {
      firePropertyChangeEvent("property changed", event.getSource(), event.getPropertyName());
   }
}

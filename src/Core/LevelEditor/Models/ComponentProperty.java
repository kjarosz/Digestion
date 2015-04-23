package Core.LevelEditor.Models;

public class ComponentProperty extends AbstractModel {
   private String mName;
   private Object mValue;
   
   public ComponentProperty(String name, Object value) {
      mName = name;
      mValue = value;
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
   
   public Object getValue() {
      return mValue;
   }
   
   public void setValue(Object value) {
      if(!mValue.equals(value)) {
         firePropertyChangeEvent("value", mValue, value);
         mValue = value;
      }
   }
}

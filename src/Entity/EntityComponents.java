package Entity;

import Entity.Components.Body;
import Entity.Components.Controllable;
import Entity.Components.Drawable;
import Entity.Components.Movable;

public class EntityComponents {
   public String name;
   
   public Body body;
   public Movable movable;
   public Drawable drawable;
   public Controllable controllable;
   
   public EntityComponents() {
      body = new Body();
      movable = new Movable();
      drawable = new Drawable();
      controllable = new Controllable();
      
      resizeable = false;
   }
   
   // Level editor hints
   public boolean resizeable;
}

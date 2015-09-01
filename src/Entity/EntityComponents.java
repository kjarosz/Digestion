package Entity;

import Entity.Components.Controllable;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Entity.Components.Movable;
import Entity.Components.Spawner;
import Entity.Components.Body;

public class EntityComponents {
   public String name;
   
   public Body tangible;
   public Movable movable;
   public Destructible destructible;
   public Drawable drawable;
   public Controllable controllable;
   public Spawner spawner;
   
   public EntityComponents() {
      tangible = new Body();
      movable = new Movable();
      destructible = new Destructible();
      drawable = new Drawable();
      controllable = new Controllable();
      spawner = new Spawner();
      
      resizeable = false;
   }
   
   // Level editor hints
   public boolean resizeable;
}

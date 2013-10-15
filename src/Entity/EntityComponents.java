package Entity;

import Entity.Components.*;

public class EntityComponents {
   public String name;
   
   public Position position;
   public Drawable drawable;
   public Collidable collidable;
   public Movable movable;
   public Controllable controllable;
   
   public EntityComponents() {
      position = new Position();
      drawable = new Drawable();
      collidable = new Collidable();
      movable = new Movable();
      controllable = new Controllable();
   }
}

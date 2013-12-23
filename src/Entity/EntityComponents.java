package Entity;

import Entity.Components.*;

public class EntityComponents {
   public String name;
   
   public Position position;
   public Destructible destructible;
   public Drawable drawable;
   public Collidable collidable;
   public Movable movable;
   public Controllable controllable;
   public Spawner spawner;
   
   public EntityComponents() {
      position = new Position();
      destructible = new Destructible();
      drawable = new Drawable();
      collidable = new Collidable();
      movable = new Movable();
      controllable = new Controllable();
      spawner = new Spawner();
   }
}

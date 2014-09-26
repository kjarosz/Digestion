package Entity;

import org.jbox2d.dynamics.Body;

import Entity.Components.Controllable;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Entity.Components.Movable;
import Entity.Components.Spawner;

public class EntityComponents {
   public String name;
   
   public Body body;
   public float m_width;
   public float m_height;
   public Movable movable;
   public Destructible destructible;
   public Drawable drawable;
   public Controllable controllable;
   public Spawner spawner;
   
   public EntityComponents() {
   	m_width = 0.0f;
      m_height = 0.0f;
      movable = new Movable();
      destructible = new Destructible();
      drawable = new Drawable();
      controllable = new Controllable();
      spawner = new Spawner();
   }
}

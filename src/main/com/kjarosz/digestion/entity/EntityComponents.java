package com.kjarosz.digestion.entity;

import com.kjarosz.digestion.entity.components.Body;
import com.kjarosz.digestion.entity.components.Controllable;
import com.kjarosz.digestion.entity.components.Drawable;
import com.kjarosz.digestion.entity.components.Movable;

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

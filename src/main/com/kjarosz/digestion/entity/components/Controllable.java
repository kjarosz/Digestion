package com.kjarosz.digestion.entity.components;

import java.util.LinkedList;

import com.kjarosz.digestion.input.KeyMapping;

public class Controllable {
   public LinkedList<KeyMapping> keyMappings;
   
   public Controllable() {
      keyMappings = new LinkedList<>();
   }
}

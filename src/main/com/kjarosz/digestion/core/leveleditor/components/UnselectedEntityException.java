package com.kjarosz.digestion.core.leveleditor.components;

public class UnselectedEntityException extends RuntimeException {
   
   public UnselectedEntityException() {
      super("No entity has been selected to add component to.");
   }
}

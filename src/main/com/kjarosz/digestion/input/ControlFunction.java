package com.kjarosz.digestion.input;

import com.kjarosz.digestion.entity.EntityComponents;

public interface ControlFunction {
   public void keyPressed(EntityComponents entity);
   public void keyReleased(EntityComponents entity);
}

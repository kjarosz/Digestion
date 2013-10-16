package Input;

import Entity.EntityComponents;

public interface ControlFunction {
   public void keyPressed(EntityComponents entity);
   public void keyReleased(EntityComponents entity);
}

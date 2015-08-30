package Entity.Systems;

import Core.Events.KeyEvent;
import Core.Events.KeyEvent.KeyAction;
import Entity.EntityComponents;
import Entity.Components.Controllable;
import Input.KeyManager;
import Input.KeyMapping;
import Level.EntityContainer;
import Level.Level;

public class ControlSystem {
   private KeyManager mKeyManager;
   
   public ControlSystem() {
      mKeyManager = new KeyManager();
   }
   
   public void clearKeys() {
      mKeyManager.clearKeys();
   }
   
   public void processKeyEvent(KeyEvent event) {
      if(event.mKeyAction == KeyAction.PRESSED) {
         mKeyManager.pressKey(event.mKeyCode);
      } else if(event.mKeyAction == KeyAction.RELEASED) {
         mKeyManager.releaseKey(event.mKeyCode);
      }
   }
   
   public void manipulate(Level level) {
      EntityContainer container = level.entityContainer;
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int entity = container.getEntityMask(i);
         if(isControllable(entity)) {
            manipulateEntity(container.accessComponents(i));
         }
      }  
   }
   
   private boolean isControllable(int entity) {
      return (entity & EntityContainer.ENTITY_CONTROLLABLE) != 0;
   }
   
   private void manipulateEntity(EntityComponents entity) {
      Controllable controlComp = entity.controllable;
      for(KeyMapping keyMapping: controlComp.keyMappings) {
         if(mKeyManager.isKeyPressed(keyMapping.keyCode) && !keyMapping.pressProcessed) {
            keyMapping.keyFunction.keyPressed(entity);
            keyMapping.pressProcessed = true;
            keyMapping.releaseProcessed = false;
         } else if(!mKeyManager.isKeyPressed(keyMapping.keyCode) && !keyMapping.releaseProcessed) {
            keyMapping.keyFunction.keyReleased(entity);
            keyMapping.releaseProcessed = true;
            keyMapping.pressProcessed = false;
         }
      }
   }
}

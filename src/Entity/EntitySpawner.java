package Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Components.Controllable;
import Entity.Systems.DrawingSystem;
import Input.ControlFunction;
import Input.KeyMapping;
import Util.Vector2D;


public abstract class EntitySpawner {
	/* 
	 * This method spawns an entity at a given position and adds its
	 * body to the box2D World object and will fill out its components
	 * to be later added to an EntityContainer. Its return value is the
	 * mask that describes all the components that the entity comprises of.
	 */
   public abstract int spawn(Vector2D position, Vector2D size, EntityComponents components);

   protected void constructKeyMapping(Controllable controllable, int keyCode, ControlFunction function) {
   	KeyMapping keyMapping = new KeyMapping();
   	keyMapping.keyCode = keyCode;
   	keyMapping.keyFunction = function;
   	keyMapping.pressProcessed = false;
   	keyMapping.releaseProcessed = true;
   	
   	controllable.keyMappings.add(keyMapping);
   }
   
   protected BufferedImage loadImage(String imageName) {
      try {
         File file = new File(imageName);
         return ImageIO.read(file);
      } catch(IOException ex) {
         return DrawingSystem.getNullImage();
      }
   }
   
   protected void makeAABB(EntityComponents components, Vector2D position, Vector2D size) {
      components.tangible.position.set(position);
      components.tangible.size.set(size);
   }
}

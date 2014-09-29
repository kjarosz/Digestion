package Entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Entity.Components.Controllable;
import Input.ControlFunction;
import Input.KeyMapping;


public abstract class EntitySpawner {
	/* 
	 * This method spawns an entity at a given position and adds its
	 * body to the box2D World object and will fill out its components
	 * to be later added to an EntityContainer. Its return value is the
	 * mask that describes all the components that the entity comprises of.
	 */
   public abstract int spawn(World world, Vec2 position, EntityComponents components);

   protected void constructKeyMapping(Controllable controllable, int keyCode, ControlFunction function) {
   	KeyMapping keyMapping = new KeyMapping();
   	keyMapping.keyCode = keyCode;
   	keyMapping.keyFunction = function;
   	keyMapping.pressProcessed = false;
   	keyMapping.releaseProcessed = true;
   	
   	controllable.keyMappings.add(keyMapping);
   }
   
}

package Core.LevelEditor.Models;

import java.awt.Rectangle;
import java.util.HashMap;

import Entity.EntityComponents;
import Entity.EntityFactory;
import Level.EntityContainer;
import Util.Vector2D;

public class EntityCache {
	private HashMap<String, Entity> entityCache;

	public EntityCache() {
	   entityCache = new HashMap<>();
		EntityFactory factory = EntityFactory.getInstance();

		Vector2D position = new Vector2D(0, 0);
		for(String entityName: factory.getEntityNames()) {
			EntityComponents comps = new EntityComponents();
			int mask = factory.createEntity(entityName, position, 
			      new Vector2D(0.5f, 2.0f), comps);

			if((mask & EntityContainer.ENTITY_DRAWABLE) != 0) {
				Rectangle size = new Rectangle(0, 0, 0, 0);
				size.width = (int)comps.tangible.size.x;
				size.height = (int)comps.tangible.size.y;
				Entity entity = new Entity(entityName, 
						comps.drawable.image,
						size);
				entity.setResizeable(comps.resizeable);
				entityCache.put(entityName, entity);
			}
		}
	}

	public Entity cloneEntity(String entityName) {
		if(entityCache.containsKey(entityName)) {
			return entityCache.get(entityName).clone();
		}
		throw new RuntimeException("No entity found.");
	}
}

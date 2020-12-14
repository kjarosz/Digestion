package com.kjarosz.digestion.core.leveleditor.models;

import java.awt.Rectangle;
import java.util.HashMap;

import com.kjarosz.digestion.entity.EntityComponents;
import com.kjarosz.digestion.entity.EntityFactory;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.util.Vector2D;

public class EntityCache {
	private HashMap<String, Entity> entityCache;

	public EntityCache() {
	   entityCache = new HashMap<>();
		EntityFactory factory = EntityFactory.getInstance();

		Vector2D position = new Vector2D(0, 0);
		for(String entityName: factory.getEntityNames()) {
			EntityComponents comps = new EntityComponents();
			int mask = factory.createEntity(entityName, position, 
			      Vector2D.ZERO_VECTOR, comps);

			if((mask & EntityContainer.ENTITY_DRAWABLE) != 0) {
				Rectangle size = new Rectangle(0, 0, 0, 0);
				size.width = (int)comps.body.size.x;
				size.height = (int)comps.body.size.y;
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

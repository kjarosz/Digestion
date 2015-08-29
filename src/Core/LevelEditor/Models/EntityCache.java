package Core.LevelEditor.Models;

import java.awt.Rectangle;
import java.util.HashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Entity.EntityFactory;
import Level.EntityContainer;
import Util.UnitConverter;

public class EntityCache {
	private HashMap<String, Entity> entityCache;

	public EntityCache() {
	   entityCache = new HashMap<>();
		EntityFactory factory = EntityFactory.getInstance();

		World world = new World(new Vec2(0.0f, 0.0f));
		Vec2 position = new Vec2(0, 0);
		for(String entityName: factory.getEntityNames()) {
			EntityComponents comps = new EntityComponents();
			int mask = factory.createEntity(world, entityName, position, 
			      new Vec2(0.5f, 2.0f), comps);

			if((mask & EntityContainer.ENTITY_DRAWABLE) != 0) {
				Rectangle size = new Rectangle(0, 0, 0, 0);
				size.width = (int)UnitConverter.metersToPixels(comps.m_width);
				size.height = (int)UnitConverter.metersToPixels(comps.m_height);
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

package Level;

import java.awt.Dimension;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Graphics.GameViewport;
import Util.UnitConverter;

public class Level {
	public String name;
	public Vec2 m_size;
	public Vec2 m_gravity;
	
	public EntityContainer entityContainer;
	public World world;

	public Level(String name, Vec2 levelSize) {
		this.name = name;
		m_size = levelSize;
		m_gravity = new Vec2(0.0f, 9.81f);
		
		entityContainer = new EntityContainer();
		world = new World(m_gravity);
	}
	
	public GameViewport getFocusViewport(Dimension windowSize) {
		for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
			int entityMask = entityContainer.getEntityMask(i);
			if((entityMask & EntityContainer.ENTITY_FOCUSABLE) != 0) {
				EntityComponents components = entityContainer.accessComponents(i);

				Vec2 px_levelSize = UnitConverter.metersToPixels(m_size);
				GameViewport viewport = new GameViewport(px_levelSize, new Vec2(windowSize.width, windowSize.height));
				viewport.setFocusObject(components);
				return viewport;
			}
		}
		return null;
	}
}

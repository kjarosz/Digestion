package com.kjarosz.digestion.level;

import java.awt.Dimension;

import com.kjarosz.digestion.entity.EntityComponents;
import com.kjarosz.digestion.graphics.GameViewport;
import com.kjarosz.digestion.util.Vector2D;

public class Level {
	public String name;
	public Vector2D size;
	public Vector2D gravity;
	
	public EntityContainer entityContainer;

	public Level(String name, Vector2D levelSize) {
		this.name = name;
		size = levelSize;
		gravity = new Vector2D(0.0f, 1350f);
		
		entityContainer = new EntityContainer();
	}
	
	public GameViewport getFocusViewport(Dimension windowSize) {
		for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
			int entityMask = entityContainer.getEntityMask(i);
			if((entityMask & EntityContainer.ENTITY_FOCUSABLE) != 0) {
				EntityComponents components = entityContainer.accessComponents(i);

				GameViewport viewport = new GameViewport(size, new Vector2D(windowSize.width, windowSize.height));
				viewport.setFocusObject(components);
				return viewport;
			}
		}
		Vector2D dummyVec = new Vector2D();
		return new GameViewport(dummyVec, dummyVec);
	}
}

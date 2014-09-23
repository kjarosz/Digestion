package Level;

import org.jbox2d.dynamics.World;

import Entity.EntityFactory;

public interface LevelLoadingScript {
   public void loadLevel(Level level);
   public void createEntities(World world, EntityFactory entityFactory, EntityContainer entityContainer);
}

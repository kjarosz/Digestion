package Level;

import Entity.EntityFactory;

public interface LevelLoadingScript {
   public void loadLevel(Level level);
   public void createEntities(EntityFactory entityFactory, World world);
}

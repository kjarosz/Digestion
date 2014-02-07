package Entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public abstract class EntitySpawner {
   public abstract int spawn(World world, Vec2 position, EntityComponents components);
}

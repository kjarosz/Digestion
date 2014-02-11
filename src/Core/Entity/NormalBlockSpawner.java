package Core.Entity;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Drawable;
import Entity.Systems.DrawingSystem;
import Level.EntityContainer;

public class NormalBlockSpawner extends EntitySpawner {
	private static final float WIDTH = 1.0f;
	private static final float HEIGHT = 1.0f;
	
   @Override
   public int spawn(World world, Vec2 position, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeCollidable(world, position, components);
      mask |= makeDrawable(components.drawable);
      return mask;
   }
   
   private int makeCollidable(World world, Vec2 position, EntityComponents components) {
   	BodyDef def = new BodyDef();
   	def.position = new Vec2(position);
   	
   	components.body = world.createBody(def);
   	components.width = WIDTH;
   	components.height = HEIGHT;
   	
   	PolygonShape shape = new PolygonShape();
   	shape.setAsBox(components.width/2.0f, components.height/2.0f);
   	
   	components.body.createFixture(shape, 0.0f);
   	
      return EntityContainer.ENTITY_COLLIDABLE;
   }
   
   private int makeDrawable(Drawable drawable) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Images" + File.separator 
                              + "Normal Block" + File.separator 
                              + "Block.gif");
         drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return EntityContainer.ENTITY_DRAWABLE;
   }
}

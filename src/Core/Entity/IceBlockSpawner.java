package Core.Entity;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Drawable;
import Entity.Systems.DrawingSystem;
import Entity.Systems.MotionSystem;
import Level.EntityContainer;

public class IceBlockSpawner extends EntitySpawner {
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
   	components.m_width = WIDTH;
   	components.m_height = HEIGHT;
   	
   	PolygonShape shape = new PolygonShape();
   	shape.setAsBox(components.m_width/2.0f, components.m_height/2.0f);

   	FixtureDef fixtureDef = new FixtureDef();
   	fixtureDef.shape = shape;
   	fixtureDef.density = 0.0f;
   	fixtureDef.filter.categoryBits |= MotionSystem.STAGE;
   	components.body.createFixture(fixtureDef);
   	
      return EntityContainer.ENTITY_COLLIDABLE;
   }
   
   private int makeDrawable(Drawable drawable) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Images" + File.separator 
                              + "Ice Block" + File.separator 
                              + "IceBlock.gif");
         drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return EntityContainer.ENTITY_DRAWABLE;
   }
   
}

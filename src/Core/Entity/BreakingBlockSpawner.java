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
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Entity.Systems.DrawingSystem;
import Entity.Systems.MotionSystem;
import Level.EntityContainer;

public class BreakingBlockSpawner extends EntitySpawner {
   @Override
   public int spawn(World world, Vec2 position, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= setCollidable(world, position, components);
      mask |= setDestructible(components.destructible);
      mask |= setDrawable(components.drawable);
      setEditorHints(components);
      return mask;
   }
   
   private int setCollidable(World world, Vec2 position, EntityComponents components) {
   	BodyDef bodyDef = new BodyDef();
   	bodyDef.position = new Vec2(position);
   	
   	components.body = world.createBody(bodyDef);
   	components.m_width = 1.0f;
   	components.m_height = 1.0f;
   	
   	PolygonShape shape = new PolygonShape();
   	shape.setAsBox(components.m_width/2.0f, components.m_height/2.0f);
   	
   	FixtureDef fixtureDef = new FixtureDef();
   	fixtureDef.shape = shape;
   	fixtureDef.density = 0.0f;
   	fixtureDef.filter.categoryBits |= MotionSystem.STAGE;
   	components.body.createFixture(fixtureDef);
   	
      return EntityContainer.ENTITY_COLLIDABLE;
   }
   
   private int setDestructible(Destructible destructible) {
      destructible.health = 100;
      destructible.maxHealth = 100;
      destructible.selfReviving = true;
      destructible.revivalInterval = 2000;
      return EntityContainer.ENTITY_DESTRUCTIBLE;
   }
   
   private int setDrawable(Drawable drawable) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Images" + File.separator 
                              + "Breaking Block" + File.separator 
                              + "BreakingBlock1.gif");
         drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return EntityContainer.ENTITY_DRAWABLE;
   }
   
   private void setEditorHints(EntityComponents components) {
      components.resizeable = true;
   }
}

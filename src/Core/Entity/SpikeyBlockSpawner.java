package Core.Entity;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Drawable;
import Entity.Systems.DrawingSystem;
import Level.EntityContainer;

public class SpikeyBlockSpawner extends EntitySpawner {
   @Override
   public int spawn(World world, Vec2 position, Vec2 size, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeMovable(world, position, size, components);
      mask |= makeDrawable(components.drawable);
      mask |= makeTriggerable();
      setEditorHints(components);
      return mask;
   }
   
   private int makeMovable(World world, Vec2 position, Vec2 size, EntityComponents components) {
   	BodyDef def = new BodyDef();
   	def.type = BodyType.DYNAMIC;
   	def.fixedRotation = true;
   	def.position = new Vec2(position);
   	
   	components.body = world.createBody(def);
   	components.m_width = size.x;
   	components.m_height = size.y;
   	
   	PolygonShape shape = new PolygonShape();
   	shape.setAsBox(components.m_width/2.0f, components.m_height/2.0f);
   	
   	FixtureDef fixtureDef = new FixtureDef();
   	fixtureDef.shape = shape;
   	fixtureDef.density = 1.0f;
   	components.body.createFixture(fixtureDef);
   	
      return EntityContainer.ENTITY_COLLIDABLE | EntityContainer.ENTITY_MOVABLE;
   }
   
   private int makeDrawable(Drawable drawable) {
      try {
         File file = new File("resources" + File.separator 
                              + "Entity Images" + File.separator 
                              + "Spike Block" + File.separator 
                              + "SpikeBlock.gif");
         drawable.image = ImageIO.read(file);
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return EntityContainer.ENTITY_DRAWABLE;
   }
   
   private int makeTriggerable() {
      return EntityContainer.ENTITY_TRIGGERABLE;
   }
   
   private void setEditorHints(EntityComponents components) {
      components.resizeable = true;
   }
}

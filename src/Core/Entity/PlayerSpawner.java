package Core.Entity;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import Core.Entity.Subcomponents.GroundSensor;
import Entity.EntityComponents;
import Entity.EntitySpawner;
import Entity.Components.Controllable;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Entity.Systems.DrawingSystem;
import Entity.Systems.MotionSystem;
import Input.ControlFunction;
import Level.EntityContainer;

public class PlayerSpawner extends EntitySpawner {
	private final float DENSITY = 5.0f;
	private final float FRICTION = 2.0f;
	
	private final int OUTER_CIRCLE_DIVISIONS = 36;
	
   private final Vec2 LEFT_FORCE = new Vec2(-25f, 0);
   private final Vec2 RIGHT_FORCE = new Vec2(25f, 0);
   private final Vec2 UP_FORCE = new Vec2(0, -6.8f);
   
   @Override
   public int spawn(World world, Vec2 position, Vec2 size, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeMovable(world, position, size, components);
      mask |= makeDestructible(components.destructible);
      mask |= makeControllable(components.controllable);
      mask |= makeDrawable(components.drawable);
      mask |= EntityContainer.ENTITY_FOCUSABLE;
      return   mask;
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
   	fixtureDef.density = 0.0f;
   	fixtureDef.filter.categoryBits |= MotionSystem.STAGE;
   	components.body.createFixture(fixtureDef);
   	
   	GroundSensor sensor = createGroundSensor(components);
   	world.setContactListener(sensor);
   	
      return EntityContainer.ENTITY_COLLIDABLE | EntityContainer.ENTITY_MOVABLE;
   }
   
   private GroundSensor createGroundSensor(EntityComponents components) {
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(components.m_width, 0.1f, new Vec2(0.0f, 1.0f), 0.0f);
      
      return new GroundSensor(shape, components);
   }
   
   private int makeDestructible(Destructible destructible) {
      destructible.health = 100;
      destructible.maxHealth = 100;
      return EntityContainer.ENTITY_DESTRUCTIBLE;
   }
   
   private int makeControllable(Controllable controllable) {
      constructMovingKeyMapping(controllable, KeyEvent.VK_A, LEFT_FORCE);
      constructJumpKeyMapping(controllable, KeyEvent.VK_W, UP_FORCE);
      constructMovingKeyMapping(controllable, KeyEvent.VK_D, RIGHT_FORCE);
      
      return EntityContainer.ENTITY_CONTROLLABLE;
   }
   
   private void constructMovingKeyMapping(Controllable controllable, int keyCode, 
         final Vec2 force) {
   	constructKeyMapping(controllable, keyCode, new ControlFunction() {
         @Override
         public void keyPressed(EntityComponents components) {
            components.movable.actingForces.addLocal(force);
         }
         
         @Override
         public void keyReleased(EntityComponents components) {
            components.movable.actingForces.subLocal(force);
         }
   	});
   }
   
   private void constructJumpKeyMapping(Controllable controllable, int keyCode, 
   		final Vec2 UP_FORCE) {
   	constructKeyMapping(controllable, keyCode, new ControlFunction() {
   		@Override
   		public void keyPressed(EntityComponents components) {
   		   if(components.movable.groundContacts > 0) {
   		      components.body.applyLinearImpulse(UP_FORCE, new Vec2(0.0f, 0.0f));
   		      components.movable.doubleJumpAvailable = true;
   		   } else if(components.movable.doubleJumpAvailable) {
               components.body.applyLinearImpulse(UP_FORCE, new Vec2(0.0f, 0.0f));
               components.movable.doubleJumpAvailable = false;
   		   }
   		}
   		
   		@Override
   		public void keyReleased(EntityComponents components) {}
   	});
   }
   
   private int makeDrawable(Drawable drawable) {
      try {
         drawable.image = ImageIO.read(new File("Players" + File.separator + "Gordon" + File.separator + "standing.gif"));
      } catch(IOException ex) {
         drawable.image = DrawingSystem.getNullImage();
      }
      return EntityContainer.ENTITY_DRAWABLE;
   }
}

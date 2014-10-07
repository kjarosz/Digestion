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
	private final float WIDTH = 0.5f;
	private final float HEIGHT = 2.0f;
	
	private final float DENSITY = 5.0f;
	private final float FRICTION = 2.0f;
	
	private final int OUTER_CIRCLE_DIVISIONS = 36;
	
   private final Vec2 LEFT_FORCE = new Vec2(-25f, 0);
   private final Vec2 RIGHT_FORCE = new Vec2(25f, 0);
   private final Vec2 UP_FORCE = new Vec2(0, -6.8f);
   
   @Override
   public int spawn(World world, Vec2 position, EntityComponents components) {
      int mask = EntityContainer.ENTITY_NONE;
      mask |= makeMovable(world, position, components);
      mask |= makeDestructible(components.destructible);
      mask |= makeControllable(components.controllable);
      mask |= makeDrawable(components.drawable);
      mask |= EntityContainer.ENTITY_FOCUSABLE;
      return   mask;
   }
   
   private int makeMovable(World world, Vec2 position, EntityComponents components) {
   	BodyDef def = new BodyDef();
   	def.type = BodyType.DYNAMIC;
   	def.fixedRotation = true;
   	def.position = new Vec2(position);
   	
   	components.body = world.createBody(def);
   	components.m_width = WIDTH;
   	components.m_height = HEIGHT;
   	
   	createInnerCircleFixture(components);
   	createOuterCircleFixture(components);
   	
   	GroundSensor sensor = createGroundSensor(components);
   	world.setContactListener(sensor);
   	
      return EntityContainer.ENTITY_COLLIDABLE | EntityContainer.ENTITY_MOVABLE;
   }
   
   private void createInnerCircleFixture(EntityComponents components) {
      CircleShape innerCircle = new CircleShape();
      innerCircle.setRadius(WIDTH/2.0f);
      
      FixtureDef innerCircleFixtureDef = new FixtureDef();
      innerCircleFixtureDef.shape = innerCircle;
      innerCircleFixtureDef.density = DENSITY;
      innerCircleFixtureDef.friction = FRICTION;
      components.body.createFixture(innerCircleFixtureDef);
   }
   
   private void createOuterCircleFixture(EntityComponents components) {
      ChainShape outerCircle = new ChainShape();
      Vec2 vertices[] = new Vec2[OUTER_CIRCLE_DIVISIONS + 1];
      for(int i = 0; i < OUTER_CIRCLE_DIVISIONS; i++) {
         float angle = (float)((2*Math.PI)/OUTER_CIRCLE_DIVISIONS)*i;
         float x, y;
         
         x = (WIDTH/2.0f)*(float)Math.cos(angle);
         y = (HEIGHT/2.0f)*(float)Math.sin(angle);
         vertices[i] = new Vec2(x, y);
      }
      vertices[OUTER_CIRCLE_DIVISIONS] = new Vec2(vertices[0]);
      outerCircle.createChain(vertices, OUTER_CIRCLE_DIVISIONS);
            
      FixtureDef outerCircleFixtureDef = new FixtureDef();
      outerCircleFixtureDef.shape = outerCircle;
      outerCircleFixtureDef.density = DENSITY;
      outerCircleFixtureDef.friction = FRICTION;
      outerCircleFixtureDef.filter.categoryBits |= MotionSystem.AGENT;
      components.body.createFixture(outerCircleFixtureDef);
   }
   
   private GroundSensor createGroundSensor(EntityComponents components) {
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(WIDTH, 0.1f, new Vec2(0.0f, 1.0f), 0.0f);
      
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

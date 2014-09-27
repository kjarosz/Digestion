package Core.Entity;

import java.awt.event.KeyEvent;
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
import Entity.Components.Controllable;
import Entity.Components.Destructible;
import Entity.Components.Drawable;
import Entity.Systems.DrawingSystem;
import Input.ControlFunction;
import Input.KeyMapping;
import Level.EntityContainer;

public class PlayerSpawner extends EntitySpawner {
	private final float WIDTH = 0.5f;
	private final float HEIGHT = 2.0f;
	
   private final Vec2 LEFT_FORCE = new Vec2(-50f, 0);
   private final Vec2 RIGHT_FORCE = new Vec2(50f, 0);
   private final Vec2 UP_FORCE = new Vec2(0, -50f);
   
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
   	
   	PolygonShape shape = new PolygonShape();
   	shape.setAsBox(components.m_width/2.0f, components.m_height/2.0f);
   	
   	FixtureDef fixtureDef = new FixtureDef();
   	fixtureDef.shape = shape;
   	fixtureDef.density = 1.0f;
   	components.body.createFixture(fixtureDef);
   	
      return EntityContainer.ENTITY_COLLIDABLE | EntityContainer.ENTITY_MOVABLE;
   }
   
   private int makeDestructible(Destructible destructible) {
      destructible.health = 100;
      destructible.maxHealth = 100;
      return EntityContainer.ENTITY_DESTRUCTIBLE;
   }
   
   private int makeControllable(Controllable controllable) {
      constructMovingKeyMapping(controllable, KeyEvent.VK_A, LEFT_FORCE);
      constructMovingKeyMapping(controllable, KeyEvent.VK_W, UP_FORCE);
      constructMovingKeyMapping(controllable, KeyEvent.VK_D, RIGHT_FORCE);
      
      return EntityContainer.ENTITY_CONTROLLABLE;
   }
   
   private void constructMovingKeyMapping(Controllable controllable, int keyCode, 
         final Vec2 force) {
      KeyMapping keyMapping = new KeyMapping();
      keyMapping.keyCode = keyCode;
      keyMapping.keyFunction = new ControlFunction() {
         @Override
         public void keyPressed(EntityComponents components) {
            components.movable.actingForces.addLocal(force);
         }
         
         @Override
         public void keyReleased(EntityComponents components) {
            components.movable.actingForces.subLocal(force);
         }
      };
      keyMapping.pressProcessed = false;
      keyMapping.releaseProcessed = true;
      
      controllable.keyMappings.add(keyMapping);
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

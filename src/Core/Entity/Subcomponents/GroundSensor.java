package Core.Entity.Subcomponents;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import Entity.EntityComponents;
import Entity.Systems.MotionSystem;

public class GroundSensor implements ContactListener {

   private EntityComponents mEntity;
   
   public GroundSensor(PolygonShape shape, EntityComponents entity) {
      mEntity = entity;
      
      FixtureDef sensorFixtureDef = new FixtureDef();
      sensorFixtureDef.shape = shape;
      sensorFixtureDef.density = 1;
      sensorFixtureDef.isSensor = true;
      sensorFixtureDef.filter.categoryBits |= MotionSystem.GROUND_SENSOR;
      mEntity.body.createFixture(sensorFixtureDef);
   }
   @Override
   public void beginContact(Contact contact) {
      if(isContactWithGround(contact, mEntity)) {
         mEntity.movable.doubleJumpAvailable = false;
         mEntity.movable.groundContacts++;
      }
   }
   
   @Override
   public void endContact(Contact contact) {
      if(isContactWithGround(contact, mEntity))
         mEntity.movable.groundContacts--;
   }
   
   @Override
   public void preSolve(Contact contact, Manifold manifold) {}
   
   @Override
   public void postSolve(Contact contact, ContactImpulse impulse) {}
   
   private boolean isContactWithGround(Contact contact, EntityComponents components) {
      Fixture fixtureA = contact.getFixtureA();
      Fixture fixtureB = contact.getFixtureB();
      
      Fixture groundSensor = null;
      Fixture object = null;
      
      boolean aSensor = isGroundSensor(fixtureA);
      boolean bSensor = isGroundSensor(fixtureB); 
      if(aSensor && !bSensor) {
         groundSensor = fixtureA;
         object = fixtureB;
      } else if (!aSensor && bSensor) {
         groundSensor = fixtureB;
         object = fixtureA;
      } else {
         return false;
      }
      
      if(!isEntityFootSensor(components.body, groundSensor))
         return false;
      
      return (object.getFilterData().categoryBits & MotionSystem.STAGE) != 0;
   }
   
   private boolean isGroundSensor(Fixture fixture) {
      return (fixture.getFilterData().categoryBits & MotionSystem.GROUND_SENSOR) != 0;
   }
   
   private boolean isEntityFootSensor(Body body, Fixture sensor) {
      Fixture fixture = body.getFixtureList();
      while(fixture != null) {
         if(fixture == sensor)
            return true;
         
         fixture = fixture.getNext();
      }
      return false;
   }

}

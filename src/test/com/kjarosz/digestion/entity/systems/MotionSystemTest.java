package com.kjarosz.digestion.entity.systems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.kjarosz.digestion.entity.components.Body;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.util.Vector2D;

@RunWith(MockitoJUnitRunner.class)
public class MotionSystemTest {

	private MotionSystem motionSystem;

	@Mock
	private EntityContainer entityContainer;

	private Body body1;
	private Body body2;

	@Before
	public void setup() {
		motionSystem = new MotionSystem();

		body1 = new Body();
		body1.position.x = 1;
		body1.position.y = 1;
		body1.size.x = 1;
		body1.size.y = 1;

		body2 = new Body();
		body2.position.x = 1;
		body2.position.y = 1;
		body2.size.x = 1;
		body2.size.y = 1;
	}

	@Test
	public void entityCollidable_NoEntityReturnsFalse() {
		when(entityContainer.getEntityMask(0)).thenReturn(EntityContainer.ENTITY_NONE);

		boolean result = motionSystem.entityCollidable(entityContainer, 0);

		assertThat(result).isFalse();
	}

	@Test
	public void entityCollidable_EntityWithCollidableMaskReturnsTrue() {
		int entityMask = EntityContainer.ENTITY_ANIMATED | EntityContainer.ENTITY_COLLIDABLE;

		when(entityContainer.getEntityMask(0)).thenReturn(entityMask);

		boolean result = motionSystem.entityCollidable(entityContainer, 0);

		assertThat(result).isTrue();
	}

	@Test
	public void entityCollidable_EntityWithoutCollidableMaskReturnsFalse() {
		int entityMask = EntityContainer.ENTITY_ANIMATED | EntityContainer.ENTITY_MOVABLE;

		when(entityContainer.getEntityMask(0)).thenReturn(entityMask);

		boolean result = motionSystem.entityCollidable(entityContainer, 0);

		assertThat(result).isFalse();
	}

	@Test
	public void entitiesCollide_Entity1LeftSideIsRightOfEntity2RightMostSideReturnsFalse() {
		body1.position.x = 3;

		boolean result = motionSystem.entitiesCollide(body1, body2);

		assertThat(result).isFalse();
	}

	@Test
	public void entitiesCollide_Entity1RightSideIsLeftOfEntity2LeftMostSideReturnsFalse() {
		body2.position.x = 3;

		boolean result = motionSystem.entitiesCollide(body1, body2);

		assertThat(result).isFalse();
	}

	@Test
	public void entitiesCollide_Entity1TopSideIsBelowEntity2BottomSideReturnsFalse() {
		body2.position.y = 3;

		boolean result = motionSystem.entitiesCollide(body1, body2);

		assertThat(result).isFalse();
	}

	@Test
	public void entitiesCollide_Entity1BottomSideIsAboveEntity2TopSideReturnsFalse() {
		body1.position.y = 3;

		boolean result = motionSystem.entitiesCollide(body1, body2);

		assertThat(result).isFalse();
	}

	@Test
	public void entityCollide_EntitySidesOverlapReturnsTrue() {
		boolean result = motionSystem.entitiesCollide(body1, body2);

		assertThat(result).isTrue();
	}

	@Test
	public void moveOutOfCollision_BodyMovingLeftIsMovedRightOfCollidingBody() {
		Vector2D motion = new Vector2D(-1.0, 0.0);

		motionSystem.moveOutOfCollision(body1, body2, motion);

		assertThat(body1.position.x).isBetween(1.99999, 2.000001);
	}

	@Test
	public void moveOutOfCollision_BodyMovingRightIsMovedLeftOfCollidingBody() {
		Vector2D motion = new Vector2D(1.0, 0.0);

		motionSystem.moveOutOfCollision(body1, body2, motion);

		assertThat(body1.position.x).isBetween(-0.000001, 0.000001);
	}

	@Test
	public void moveOutOfCollision_BodyMovingUpIsMovedBelowCollidingBody() {
		Vector2D motion = new Vector2D(0.0, -1.0);

		motionSystem.moveOutOfCollision(body1, body2, motion);

		assertThat(body1.position.y).isBetween(1.99999, 2.000001);
	}

	@Test
	public void moveOutOfCollision_BodyMovingDownIsMovedAboveCollidingBody() {
		Vector2D motion = new Vector2D(0.0, 1.0);

		motionSystem.moveOutOfCollision(body1, body2, motion);

		assertThat(body1.position.y).isBetween(-0.000001, 0.000001);
	}

}

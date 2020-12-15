package com.kjarosz.digestion.entity.systems;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.kjarosz.digestion.entity.components.Body;

@RunWith(MockitoJUnitRunner.class)
public class MotionSystemTest {

	private MotionSystem motionSystem;

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
}
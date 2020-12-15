package com.kjarosz.digestion.entity.systems;

import com.kjarosz.digestion.entity.EntityComponents;
import com.kjarosz.digestion.entity.components.Body;
import com.kjarosz.digestion.level.EntityContainer;
import com.kjarosz.digestion.level.Level;
import com.kjarosz.digestion.util.GameTimer;
import com.kjarosz.digestion.util.RKIntegrator;
import com.kjarosz.digestion.util.Vector2D;
import com.kjarosz.digestion.util.VectorTransform;

public class MotionSystem {
	public final static long NANO_TIMESTEP = (long) (1.0 / 60.0 * 1000000000);

	private GameTimer mTimer;

	public MotionSystem() {
		mTimer = new GameTimer(NANO_TIMESTEP);
	}

	public void resetTimer() {
		mTimer.reset();
	}

	public void pauseTimer() {
		mTimer.pause();
	}

	public void startTimer() {
		mTimer.start();
	}

	private final VectorTransform x_filter = (v) -> new Vector2D(v.x, 0.0);
	private final VectorTransform y_filter = (v) -> new Vector2D(0.0, v.y);

	private Vector2D mGravity;
	private EntityContainer mContainer;
	private EntityComponents mEntity;
	private EntityComponents mOtherEntity;

	private boolean isMovable(int eID) {
		return (mContainer.getEntityMask(eID) & EntityContainer.ENTITY_MOVABLE) != 0;
	}

	private boolean entityCollidable(int id) {
		return (mContainer.getEntityMask(id) & EntityContainer.ENTITY_COLLIDABLE) != 0;
	}

	public void move(Level level) {
		mTimer.updateFrame();
		while (mTimer.hasAccumulatedTime()) {
			double ms_timestep = mTimer.stepMillisTime();

			mGravity = level.gravity;
			mContainer = level.entityContainer;
			for (int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
				if (isMovable(i)) {
					step(i, ms_timestep);
				}
			}
		}
	}

	private void step(int eID, double dt) {
		mEntity = mContainer.accessComponents(eID);
		stepAlongXAxis(eID, dt);
		stepAlongYAxis(eID, dt);
	}

	private void stepAlongXAxis(int eID, double dt) {
		Vector2D shift = RKIntegrator.integrate(mEntity.movable.terminalVelocity,
				x_filter.filter(mEntity.movable.velocity), Vector2D.ZERO_VECTOR, dt);
		mEntity.body.position.addLocal(shift);

		if (!shift.equals(Vector2D.ZERO_VECTOR) && entityCollidable(eID)) {
			resolveCollisions(eID, shift, x_filter, false);
		}
	}

	private void stepAlongYAxis(int eID, double dt) {
		Vector2D acceleration = new Vector2D(mEntity.movable.netForce);
		acceleration.divLocal(mEntity.movable.mass);
		if (!mEntity.movable.ignoreGravity) {
			acceleration.addLocal(mGravity);
		}
		acceleration = y_filter.filter(acceleration);
		Vector2D shift = RKIntegrator.integrate(mEntity.movable.terminalVelocity,
				y_filter.filter(mEntity.movable.velocity), acceleration, dt);
		mEntity.movable.velocity.addLocal(acceleration.mul(dt));
		mEntity.body.position.addLocal(shift);

		if (!shift.equals(Vector2D.ZERO_VECTOR) && entityCollidable(eID)) {
			if (resolveCollisions(eID, shift, y_filter, true) && shift.y > 0.0) {
				mEntity.movable.canJump = true;
				mEntity.movable.canDoubleJump = true;
			}
		}
	}

	private boolean resolveCollisions(int eID, Vector2D dx, VectorTransform axisFilter, boolean clearVelocity) {
		boolean collisionFound = false;
		boolean cycleAgain;
		do {
			cycleAgain = false;
			for (int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
				if (i == eID || !entityCollidable(i)) {
					continue;
				}

				mOtherEntity = mContainer.accessComponents(i);
				if (entitiesCollide()) {
					cycleAgain = collisionFound = true;
					moveOutOfCollision(dx);
					if (clearVelocity) {
						mEntity.movable.velocity.subLocal(axisFilter.filter(mEntity.movable.velocity));
					}
				}
			}
		} while (cycleAgain);
		return collisionFound;
	}

	boolean entitiesCollide(Body body1, Body body2) {
		if (body1.position.x >= body2.position.x + body2.size.x)
			return false;

		if (body1.position.x + body1.size.x <= body2.position.x)
			return false;

		if (body1.position.y >= body2.position.y + body2.size.y)
			return false;

		if (body1.position.y + body1.size.y <= body2.position.y)
			return false;

		return true;
	}

	private void moveOutOfCollision(Vector2D dx) {
		Body body = mEntity.body;
		Body oBody = mOtherEntity.body;
		if (dx.x > 0) {
			body.position.x = oBody.position.x - body.size.x;
		} else if (dx.x < 0) {
			body.position.x = oBody.position.x + oBody.size.x;
		}

		if (dx.y > 0) {
			body.position.y = oBody.position.y - body.size.y;
		} else if (dx.y < 0) {
			body.position.y = oBody.position.y + oBody.size.y;
		}
	}
}

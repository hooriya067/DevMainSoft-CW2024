package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.ActiveActor;

/**
 * The {@code EnemyPlane} class represents a basic enemy type in the game.
 * It is responsible for horizontal movement and firing projectiles at a low rate.
 *
 * <p>
 * This class extends {@link EnemyParent}, inheriting shared enemy behaviors and
 * implementing the specifics of a basic enemy plane.
 * </p>
 */
public class EnemyPlane extends EnemyParent {

	/**
	 * The file name of the image representing the enemy plane.
	 */
	private static final String IMAGE_NAME = "enemyplane.png";

	/**
	 * The probability (per frame) of the enemy firing a projectile.
	 */
	private static final double FIRE_RATE = 0.01;

	/**
	 * Constructs an {@code EnemyPlane} object with the specified initial position
	 * and level context.
	 *
	 * @param initialXPos  the initial X-coordinate of the enemy plane.
	 * @param initialYPos  the initial Y-coordinate of the enemy plane.
	 * @param levelParent  the {@link LevelParent} instance the enemy belongs to.
	 */
	public EnemyPlane(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, 70, initialXPos, initialYPos, 1, levelParent);
	}

	/**
	 * Updates the state of the enemy plane, including position and firing behavior.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 * The enemy plane moves left across the screen at a fixed speed.
	 */
	@Override
	public void updatePositionWhenActive() {
		moveHorizontally(-6);
	}

	/**
	 * Fires a projectile if the randomly generated value is less than the defined {@code FIRE_RATE}.
	 *
	 * @return an {@link ActiveActor} representing the fired projectile, or {@code null} if no projectile is fired.
	 */
	@Override
	public ActiveActor fireProjectileWhenActive() {
		return Math.random() < FIRE_RATE
				? ProjectileFactory.createProjectile("ENEMY_PROJECTILE", getProjectileXPosition(0), getProjectileYPosition(20), levelParent)
				: null;
	}
}


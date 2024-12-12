package com.example.demo.actors.active.enemies;

import com.example.demo.actors.active.FighterPlane;
import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;

/**
 * The {@code EnemyParent} class serves as a base class for all enemy types in the game.
 * It provides a shared structure and common behaviors for enemies, such as health management,
 * movement logic, and firing projectiles.
 *
 * <p>
 * This class extends {@link FighterPlane} and implements {@link Enemy}, combining
 * the functionality of a destructible fighter plane with additional enemy-specific behaviors.
 * It also introduces an abstract framework for unique enemy actions, making it easier
 * to create diverse enemy types.
 * </p>
 */
public abstract class EnemyParent extends FighterPlane implements Enemy {

    /**
     * Reference to the level in which the enemy exists.
     */
    protected LevelParent levelParent;

    /**
     * Constructs an {@code EnemyParent} object with the specified parameters.
     *
     * @param imageName   the name of the image representing the enemy.
     * @param imageHeight the height of the enemy's image in pixels.
     * @param initialX    the initial X-coordinate of the enemy.
     * @param initialY    the initial Y-coordinate of the enemy.
     * @param health      the initial health of the enemy.
     * @param levelParent the {@link LevelParent} the enemy belongs to.
     */
    public EnemyParent(String imageName, int imageHeight, double initialX, double initialY, int health, LevelParent levelParent) {
        super(imageName, imageHeight, initialX, initialY, health);
        this.health = health;
        this.levelParent = levelParent;
    }

    /**
     * Reduces the enemy's health when it takes damage. If health reaches zero,
     * the enemy is destroyed.
     */
    @Override
    public void takeDamage() {
        health--;
        if (health <= 0) {
            destroy();
        }
    }

    /**
     * Fires a projectile if the game is not paused. The actual firing logic
     * is delegated to the {@link #fireProjectileWhenActive()} method.
     *
     * @return an {@link ActiveActor} representing the fired projectile, or {@code null} if the game is paused.
     */
    @Override
    public ActiveActor fireProjectile() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return null;
        }
        return fireProjectileWhenActive();
    }

    /**
     * Abstract method to define the logic for firing projectiles when the enemy is active.
     *
     * @return an {@link ActiveActor} representing the fired projectile.
     */
    protected abstract ActiveActor fireProjectileWhenActive();

    /**
     * Updates the position of the enemy if the game is not paused. The actual movement
     * logic is delegated to the {@link #updatePositionWhenActive()} method.
     */
    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        updatePositionWhenActive();
    }

    /**
     * Abstract method to define the movement logic for the enemy when it is active.
     */
    protected abstract void updatePositionWhenActive();
}

package com.example.demo.actors.active.enemies;

import com.example.demo.actors.active.ActiveActor;

/**
 * The {@code Enemy} interface defines the core behaviors required for all enemy types
 * in the game. Classes implementing this interface represent enemies with distinct
 * movement, actions, and interactions, such as firing projectiles or taking damage.
 *
 * <p>
 * By providing a standardized set of methods, the {@code Enemy} interface ensures
 * consistency across various enemy implementations while allowing flexibility
 * for unique behaviors specific to each type.
 * </p>
 */
public interface Enemy {

    /**
     * Updates the position of the enemy based on its specific movement logic.
     * Implementing classes should define the movement patterns.
     */
    void updatePosition();

    /**
     * Updates the overall state of the enemy, which may include position, animations,
     * or other gameplay-related changes.
     */
    void updateActor();

    /**
     * Fires a projectile from the enemy. The specific type and behavior of the projectile
     * are determined by the implementing class.
     *
     * @return an {@link ActiveActor} representing the projectile fired by the enemy.
     */
    ActiveActor fireProjectile();

    /**
     * Applies damage to the enemy, reducing its health or triggering its destruction.
     * Implementing classes should define the logic for handling damage.
     */
    void takeDamage();

    /**
     * Destroys the enemy, removing it from the game. This method may also trigger
     * additional effects like animations or scoring.
     */
    void destroy();
}



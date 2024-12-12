package com.example.demo.actors.active.projectiles;

import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.GameStateManager;

/**
 * The {@code HomingMissile} class represents a projectile that tracks and homes in on the user's plane.
 * This class extends {@link ProjectileParent} and provides advanced targeting capabilities.
 */
public class HomingMissile extends ProjectileParent {

    /**
     * The file name of the image representing the homing missile.
     */
    private static final String IMAGE_NAME = "HomingMissile.png";

    /**
     * The height of the missile image in pixels.
     */
    private static final int IMAGE_HEIGHT = 30;

    /**
     * The constant speed at which the missile moves.
     */
    private static final double SPEED = 2.0;

    /**
     * The user plane that this missile targets.
     */
    private final UserPlane target;

    /**
     * Constructs a {@code HomingMissile} object with the specified initial position and target.
     *
     * @param initialX the initial X-coordinate of the missile.
     * @param initialY the initial Y-coordinate of the missile.
     * @param target   the {@link UserPlane} this missile tracks.
     * @throws IllegalArgumentException if the target is {@code null}.
     */
    public HomingMissile(double initialX, double initialY, UserPlane target) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, SPEED);
        if (target == null) {
            throw new IllegalArgumentException("Target user plane cannot be null.");
        }
        this.target = target;
    }

    /**
     * Updates the position of the missile, adjusting its trajectory to move toward the target.
     * The missile calculates the direction based on the target's position and moves accordingly.
     * If the game is paused, the missile does not update its position.
     */
    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;  // Skip updating position if paused
        }

        double deltaX = target.getLayoutX() + target.getTranslateX() - (getLayoutX() + getTranslateX());
        double deltaY = target.getLayoutY() + target.getTranslateY() - (getLayoutY() + getTranslateY());
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double directionX = (deltaX / distance) * SPEED;
        double directionY = (deltaY / distance) * SPEED;

        setTranslateX(getTranslateX() + directionX);
        setTranslateY(getTranslateY() + directionY);
    }

    /**
     * Updates the actor's state. Calls {@link #updatePosition()} to adjust the missile's trajectory.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}

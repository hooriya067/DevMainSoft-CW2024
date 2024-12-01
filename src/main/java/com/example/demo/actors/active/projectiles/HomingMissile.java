package com.example.demo.actors.active.projectiles;

import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.GameStateManager;

public class HomingMissile extends Projectile {

    private static final String IMAGE_NAME = "HomingMissile.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final double SPEED = 2.0;  // Speed of the homing missile
    private final UserPlane target;// Reference to the user plane (the target)


    public HomingMissile(double initialX, double initialY, UserPlane target) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, SPEED);
        if (target == null) {
            throw new IllegalArgumentException("Target user plane cannot be null.");
        }
        this.target = target; // Assign the user plane to the missile's target
    }
    @Override
    public void updatePosition() {
        if (GameStateManager.isPaused) {
            return;  // Skip updating position if paused
        }

        // Calculate direction to the target
        double deltaX = target.getLayoutX() + target.getTranslateX() - (getLayoutX() + getTranslateX());
        double deltaY = target.getLayoutY() + target.getTranslateY() - (getLayoutY() + getTranslateY());
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normalize direction and apply speed to move towards the target
        double directionX = (deltaX / distance) * SPEED;
        double directionY = (deltaY / distance) * SPEED;

        setTranslateX(getTranslateX() + directionX);
        setTranslateY(getTranslateY() + directionY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}

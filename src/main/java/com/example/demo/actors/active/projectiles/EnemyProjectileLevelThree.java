package com.example.demo.actors.active.projectiles;

public class EnemyProjectileLevelThree extends Projectile {

    private static final String IMAGE_NAME = "enemyfire2.png"; // Custom image for Level 3
    private static final int IMAGE_HEIGHT = 25; // Set a different height if needed
    private static final int HORIZONTAL_VELOCITY = -10; // A slightly faster projectile for increased difficulty

    public EnemyProjectileLevelThree(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY);
    }

}


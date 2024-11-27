package com.example.demo;

public class Meteor extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "meteor.png";
    private static final int IMAGE_HEIGHT = 50;
    private final double speed = 5; // Speed of the meteor
    private double directionX; // Horizontal direction
    private double directionY; // Vertical direction
    private final LevelParent level;

    public Meteor(LevelParent level) {
        super(IMAGE_NAME, IMAGE_HEIGHT, level.getScreenWidth() - 50, -50); // Start near top-right corner
        this.level = level;
        initializeDirection(); // Set up the diagonal movement
        System.out.println("Meteor spawned at X: " + getTranslateX() + ", Y: " + getTranslateY());
    }

    private void initializeDirection() {
        // Define target as the bottom-left corner of the screen
        double targetX = 0; // Target is the left edge of the screen
        double targetY = level.getScreenHeight(); // Bottom edge of the screen

        // Calculate direction vector
        directionX = targetX - getTranslateX(); // From current X to target X
        directionY = targetY - getTranslateY(); // From current Y to target Y

        // Normalize the vector to ensure constant speed
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
        directionX = (directionX / magnitude) * speed;
        directionY = (directionY / magnitude) * speed;

        System.out.println("Direction X: " + directionX + ", Direction Y: " + directionY);
    }

    @Override
    public void updateActor() {
        setTranslateX(getTranslateX() + directionX);
        setTranslateY(getTranslateY() + directionY);

        // Remove meteor if it leaves the screen
        if (getTranslateX() < 0 || getTranslateY() > level.getScreenHeight()) {
            destroy();
        }
    }


    @Override
    public void updatePosition() {
        // Movement is handled in updateActor
    }

    @Override
    public void takeDamage() {
        destroy(); // Meteors are destroyed on impact
    }
}

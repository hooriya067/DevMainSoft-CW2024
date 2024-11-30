package com.example.demo;

public abstract class EnemyParent extends FighterPlane implements Enemy {
    protected int health;
    protected LevelParent levelParent;

    public EnemyParent(String imageName, int imageHeight, double initialX, double initialY, int health, LevelParent levelParent) {
        super(imageName, imageHeight, initialX, initialY, health);
        this.health = health;
        this.levelParent = levelParent;
    }

    @Override
    public void takeDamage() {
        health--;
        if (health <= 0) {
            destroy();
        }
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (GameStateManager.isPaused) {
            return null; // Return null if the game is paused
        }
        return fireProjectileWhenActive(); // Delegate to abstract method
    }

    protected abstract ActiveActorDestructible fireProjectileWhenActive();

    @Override
    public void updatePosition() {
        if (GameStateManager.isPaused) {
            return; // Skip updating position if paused
        }
        updatePositionWhenActive(); // Delegate to abstract method
    }

    protected abstract void updatePositionWhenActive();
}

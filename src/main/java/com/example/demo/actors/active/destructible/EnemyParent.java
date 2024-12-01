package com.example.demo.actors.active.destructible;

import com.example.demo.actors.active.FighterPlane;
import com.example.demo.levels.LevelParent;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.active.enemies.Enemy;
import com.example.demo.core.GameStateManager;

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

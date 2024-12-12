package com.example.demo.actors.active.Factories;

import com.example.demo.actors.active.enemies.EnemyParent;
import com.example.demo.actors.active.enemies.*;
import com.example.demo.core.GameConfig;
import com.example.demo.Levels.LevelParent;

/**
 * The {@code EnemyFactory} class provides a centralized mechanism for creating various types of enemies
 * within the game. It uses the Factory design pattern to instantiate specific enemy classes based on
 * the provided type.
 */
public class EnemyFactory {

    /**
     * Constructs an {@code EnemyFactory} instance.
     *
     * @param levelParent the level context where the enemy will be placed.
     *                    This parameter cannot be {@code null}.
     * @throws IllegalArgumentException if {@code levelParent} is {@code null}.
     */
    public EnemyFactory(LevelParent levelParent) {
        if (levelParent == null) {
            throw new IllegalArgumentException("LevelParent cannot be null");
        }
    }

    /**
     * Creates an enemy of the specified type at the given coordinates within the specified level context.
     *
     * @param type         the type of enemy to create (e.g., "ENEMY1", "BOSS", "TYPEA").
     * @param x            the initial X-coordinate of the enemy.
     * @param y            the initial Y-coordinate of the enemy.
     * @param levelParent  the level context in which the enemy will exist.
     * @return an instance of {@link EnemyParent} corresponding to the specified type.
     * @throws IllegalArgumentException if the type is invalid or {@code levelParent} is {@code null}.
     */
    public static EnemyParent createEnemy(String type, double x, double y, LevelParent levelParent) {
        return switch (type.toUpperCase()) {
            case "ENEMY1" -> new EnemyPlane(x, y, levelParent);
            case "BOSS" -> new Boss(x, y, GameConfig.SCREEN_HEIGHT, levelParent);
            case "TYPEA" -> new EnemyPlaneTypeA(x, y, levelParent);
            case "TYPEB" -> new EnemyPlaneTypeB(x, y, levelParent);
            case "STEALTH" -> new StealthEnemyPlane(x, y, levelParent);
            default -> throw new IllegalArgumentException("Invalid enemy type: " + type);
        };
    }
}

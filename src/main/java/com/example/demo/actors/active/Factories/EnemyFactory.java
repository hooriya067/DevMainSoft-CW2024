package com.example.demo.actors.active.Factories;

import com.example.demo.actors.active.enemies.EnemyParent;
import com.example.demo.actors.active.enemies.*;
import com.example.demo.core.GameConfig;
import com.example.demo.levels.LevelParent;

public class EnemyFactory {

    public EnemyFactory(LevelParent levelParent) {
        if (levelParent == null) {
            throw new IllegalArgumentException("LevelParent cannot be null");
        }

    }

    // Static method to create enemies
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

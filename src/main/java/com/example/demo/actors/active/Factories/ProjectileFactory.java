package com.example.demo.actors.active.Factories;

import com.example.demo.actors.active.projectiles.*;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.actors.active.ActiveActor;
/**
 * The {@link ProjectileFactory} class provides a centralized mechanism for creating various types of projectiles
 * in the game. It utilizes the Factory design pattern to create projectiles based on the specified type.
 */
public class ProjectileFactory {

    /**
     * Creates a projectile of the specified type at the given coordinates with optional additional parameters.
     *
     * @param type              the type of projectile to create (e.g., "USER_PROJECTILE", "BOSS_PROJECTILE").
     * @param x                 the initial X-coordinate of the projectile.
     * @param y                 the initial Y-coordinate of the projectile.
     * @param additionalParams  optional parameters required for specific projectile types (e.g., {@link UserPlane} for homing missiles).
     * @return an instance of {@link ActiveActor} corresponding to the specified projectile type.
     * @throws IllegalArgumentException if the type is invalid or if required parameters are missing.
     */
    public static ActiveActor createProjectile(String type, double x, double y, Object... additionalParams) {
        return switch (type.toUpperCase()) {
            case "USER_PROJECTILE" -> new UserProjectile(x, y);
            case "BOSS_PROJECTILE" -> new BossProjectile(y);
            case "BOMB" -> new BombProjectile(x, y);
            case "ENEMY_PROJECTILE" -> new EnemyProjectile(x, y);
            case "ENEMY_PROJECTILE_LEVEL_THREE" -> new EnemyProjectileLevelThree(x, y);
            case "HOMING_MISSILE" -> {
                if (additionalParams.length > 0 && additionalParams[0] instanceof UserPlane userPlane) {
                    yield new HomingMissile(x, y, userPlane);
                } else {
                    throw new IllegalArgumentException("HOMING_MISSILE requires a UserPlane as an additional parameter.");
                }
            }
            default -> throw new IllegalArgumentException("Invalid projectile type: " + type);
        };
    }
}

package com.example.demo.actors.active.Factories;

import com.example.demo.actors.active.projectiles.*;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.actors.active.ActiveActor;

public class ProjectileFactory {

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

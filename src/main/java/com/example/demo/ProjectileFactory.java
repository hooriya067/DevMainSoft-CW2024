package com.example.demo;

public class ProjectileFactory {
//
//    public static Projectile createProjectile(String type, double x, double y, LevelParent levelParent) {
//        return switch (type.toUpperCase()) {
//            case "USER" -> new UserProjectile(x, y);
//            case "ENEMY" -> new EnemyProjectile(x, y);
//            case "BOSS" -> new BossProjectile(y); // BossProjectile uses only Y coordinate for now
//            case "BOMB" -> new BombProjectile(x, y);
//            case "LEVEL_THREE" -> new EnemyProjectileLevelThree(x, y);
//            case "HOMING" -> new HomingMissile(x, y, levelParent.getUser());
//            default -> throw new IllegalArgumentException("Invalid projectile type: " + type);
//        };
//    }
public static ActiveActorDestructible createProjectile(String type, double x, double y, Object... additionalParams) {
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

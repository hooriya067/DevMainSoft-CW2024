package com.example.demo.actors.active.Factories;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.projectiles.*;
import com.example.demo.actors.user.UserPlane;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProjectileFactoryTest {
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @Test
    void testCreateUserProjectile() {
        // Act: Create a UserProjectile
        ActiveActor projectile = ProjectileFactory.createProjectile("USER_PROJECTILE", 100, 200);

        // Assert: Verify the projectile is of the correct type
        assertNotNull(projectile, "Projectile should not be null");
        assertTrue(projectile instanceof UserProjectile, "Created projectile should be an instance of UserProjectile");
    }

    @Test
    void testCreateBossProjectile() {
        // Act: Create a BossProjectile
        ActiveActor projectile = ProjectileFactory.createProjectile("BOSS_PROJECTILE", 0, 300);

        // Assert: Verify the projectile is of the correct type
        assertNotNull(projectile, "Projectile should not be null");
        assertTrue(projectile instanceof BossProjectile, "Created projectile should be an instance of BossProjectile");
    }

    @Test
    void testCreateBombProjectile() {
        // Act: Create a BombProjectile
        ActiveActor projectile = ProjectileFactory.createProjectile("BOMB", 150, 250);

        // Assert: Verify the projectile is of the correct type
        assertNotNull(projectile, "Projectile should not be null");
        assertTrue(projectile instanceof BombProjectile, "Created projectile should be an instance of BombProjectile");
    }

    @Test
    void testCreateEnemyProjectile() {
        // Act: Create an EnemyProjectile
        ActiveActor projectile = ProjectileFactory.createProjectile("ENEMY_PROJECTILE", 50, 150);

        // Assert: Verify the projectile is of the correct type
        assertNotNull(projectile, "Projectile should not be null");
        assertTrue(projectile instanceof EnemyProjectile, "Created projectile should be an instance of EnemyProjectile");
    }

    @Test
    void testCreateEnemyProjectileLevelThree() {
        // Act: Create an EnemyProjectileLevelThree
        ActiveActor projectile = ProjectileFactory.createProjectile("ENEMY_PROJECTILE_LEVEL_THREE", 200, 300);

        // Assert: Verify the projectile is of the correct type
        assertNotNull(projectile, "Projectile should not be null");
        assertTrue(projectile instanceof EnemyProjectileLevelThree, "Created projectile should be an instance of EnemyProjectileLevelThree");
    }

    @Test
    void testCreateHomingMissile() {
        // Arrange: Mock a UserPlane instance
        UserPlane mockUserPlane = mock(UserPlane.class);

        // Act: Create a HomingMissile
        ActiveActor projectile = ProjectileFactory.createProjectile("HOMING_MISSILE", 100, 200, mockUserPlane);

        // Assert: Verify the projectile is of the correct type
        assertNotNull(projectile, "Projectile should not be null");
        assertTrue(projectile instanceof HomingMissile, "Created projectile should be an instance of HomingMissile");
    }

    @Test
    void testCreateHomingMissileWithoutUserPlane() {
        // Act & Assert: Verify an exception is thrown when UserPlane is missing
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectileFactory.createProjectile("HOMING_MISSILE", 100, 200);
        });
        assertEquals("HOMING_MISSILE requires a UserPlane as an additional parameter.", exception.getMessage());
    }

    @Test
    void testCreateInvalidProjectileType() {
        // Act & Assert: Verify an exception is thrown for an invalid projectile type
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectileFactory.createProjectile("INVALID_TYPE", 0, 0);
        });
        assertEquals("Invalid projectile type: INVALID_TYPE", exception.getMessage());
    }
}

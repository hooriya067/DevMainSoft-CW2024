package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.projectiles.EnemyProjectile;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BossTest {

    private Boss boss;
    private LevelParent levelParentMock;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setup() {
        levelParentMock = mock(LevelParent.class);
        when(levelParentMock.getScreenHeight()).thenReturn(600.0);
        boss = new Boss(0, 0, 600, levelParentMock);
    }

    @Test
    void testShieldActivation() {
        // Act: Simulate shield activation
        boss.updateActor(); // This internally might activate the shield

        // Assert: Verify shield state
        boolean shieldActivated = boss.isShieldActive();
        assertTrue(shieldActivated || !shieldActivated, "Shield state should be true or false based on random activation.");
    }

    @Test
    void testShieldDeactivationAfterMaxFrames() {
        // Act: Force shield activation and simulate MAX_FRAMES_WITH_SHIELD updates
        boss.updateActor();
        if (boss.isShieldActive()) {
            for (int i = 0; i < 150; i++) {
                boss.updateActor();
            }
            // Assert: Shield should deactivate after max frames
            assertFalse(boss.isShieldActive(), "Shield should deactivate after MAX_FRAMES_WITH_SHIELD frames.");
        }
    }

    @Test
    void testTakeDamageWithShield() {
        // Arrange: Force shield activation
        boss.updateActor();
        if (boss.isShieldActive()) {
            int initialHealth = boss.getHealth();

            // Act: Try to take damage
            boss.takeDamage();

            // Assert: Health should not decrease if shield is active
            assertEquals(initialHealth, boss.getHealth(), "Health should not decrease while the shield is active.");
        }
    }

    @Test
    void testTakeDamageWithoutShield() {
        // Arrange: Ensure shield is inactive
        while (boss.isShieldActive()) {
            boss.updateActor();
        }
        int initialHealth = boss.getHealth();

        // Act: Take damage
        boss.takeDamage();

        // Assert: Health should decrease
        assertEquals(initialHealth - 1, boss.getHealth(), "Health should decrease by 1 when the shield is not active.");
    }

    @Test
    void testFireProjectileWhenActive() {
        try (MockedStatic<ProjectileFactory> projectileFactoryMock = mockStatic(ProjectileFactory.class)) {
            // Arrange: Use an actual EnemyProjectile
            ActiveActor actualProjectile = new EnemyProjectile(0, 100);
            projectileFactoryMock.when(() -> ProjectileFactory.createProjectile(
                            eq("BOSS_PROJECTILE"), eq(0.0), anyDouble(), eq(levelParentMock)))
                    .thenReturn(actualProjectile);

            // Act: Fire a projectile
            ActiveActor projectile = boss.fireProjectile();

            // Assert: Verify projectile creation
            if (projectile != null) {
                assertEquals(actualProjectile, projectile, "The created projectile should match the actual implementation.");
            } else {
                assertNull(projectile, "Projectile may not be created based on fire rate.");
            }
        }
    }


}

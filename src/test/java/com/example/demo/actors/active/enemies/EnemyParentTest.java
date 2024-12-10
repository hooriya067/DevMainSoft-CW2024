package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.core.GameStateManager;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemyParentTest {

    private EnemyParent enemy;
    private LevelParent mockLevelParent;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setup() {
        mockLevelParent = mock(LevelParent.class);

        // Create a concrete EnemyParent for testing
        enemy = new EnemyParent("dummy.jpg", 50, 100, 100, 1, mockLevelParent) {
            @Override
            protected ActiveActor fireProjectileWhenActive() {
                return mock(ActiveActor.class); // Stub projectile creation
            }
            @Override
            public void updateActor() {}

            @Override
            protected void updatePositionWhenActive() {
                // Simulate position update logic
                setTranslateX(getTranslateX() + 5);
            }
        };
    }

    @Test
    void testTakeDamage() {
        // Act: Take damage
        enemy.takeDamage();
        assertEquals(0, enemy.getHealth(), "Health should decrease by 1 after taking damage");

        // Act: Take enough damage to destroy the enemy
        enemy.takeDamage();
        enemy.takeDamage();

        // Assert: Enemy is destroyed
        assertTrue(enemy.isDestroyed(), "Enemy should be destroyed when health reaches 0");
    }

    @Test
    void testFireProjectileWhenGamePaused() {
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            // Arrange: Mock game state as paused
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(true);

            // Act: Attempt to fire a projectile
            ActiveActor projectile = enemy.fireProjectile();

            // Assert: No projectile is fired when game is paused
            assertNull(projectile, "No projectile should be fired when the game is paused");
        }
    }
    @Test
    void testUpdatePositionWhenGamePaused() {
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            // Arrange: Mock game state as paused
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(true);

            // Record initial position
            double initialX = enemy.getTranslateX();

            // Act: Attempt to update position
            enemy.updatePosition();

            // Assert: Position does not change when game is paused
            assertEquals(initialX, enemy.getTranslateX(), "Position should not change when the game is paused");
        }
    }

    @Test
    void testUpdatePositionWhenGameActive() {
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            // Arrange: Mock game state as active
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(false);

            // Record initial position
            double initialX = enemy.getTranslateX();

            // Act: Update position
            enemy.updatePosition();

            // Assert: Position changes when the game is active
            assertNotEquals(initialX, enemy.getTranslateX(), "Position should change when the game is active");
        }
    }
//    @Test
//    void testFireProjectileWhenGameActive() {
//        try (
//                MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class);
//                MockedStatic<ProjectileFactory> projectileFactoryMock = mockStatic(ProjectileFactory.class)
//        ) {
//            // Arrange: Mock GameStateManager to indicate the game is active
//            GameStateManager gameStateManager = mock(GameStateManager.class);
//            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
//            when(gameStateManager.isGamePaused()).thenReturn(false);
//
//            // Create a lightweight subclass of ActiveActor
//            ActiveActor stubProjectile = new ActiveActor("dummy.jpg", 10, 0, 0) {
//                @Override
//                public void updatePosition() {}
//                @Override
//                public void updateActor() {}
//            };
//
//            // Mock the ProjectileFactory to return the stub
//            projectileFactoryMock.when(() -> ProjectileFactory.createProjectile(anyString(), anyDouble(), anyDouble(), any()))
//                    .thenReturn(stubProjectile);
//
//            // Act: Fire projectile
//            ActiveActor projectile = enemy.fireProjectile();
//
//            // Assert: Verify that a projectile is created
//            assertNotNull(projectile, "A projectile should be created when the game is active");
//            assertEquals(stubProjectile, projectile, "The returned projectile should match the mocked projectile");
//        }
//    }



}

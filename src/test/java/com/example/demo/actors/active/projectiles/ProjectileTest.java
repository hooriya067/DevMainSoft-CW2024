package com.example.demo.actors.active.projectiles;

import com.example.demo.core.GameStateManager;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {

    private Projectile projectile;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setup() {
        // Initialize the Projectile instance
        projectile = new Projectile("dummy.jpg", 30, 100, 200, 5.0);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        // Verify initial properties
        assertEquals(100, projectile.getLayoutX(), "Initial X position should match the constructor value.");
        assertEquals(200, projectile.getLayoutY(), "Initial Y position should match the constructor value.");
    }

    @Test
    void testProjectileMovesHorizontallyWhenGameActive() {
        // Arrange: Capture initial position
        double initialX = projectile.getLayoutX() + projectile.getTranslateX();

        // Act: Update position
        projectile.updatePosition();

        // Assert: Verify horizontal movement
        double newX = projectile.getLayoutX() + projectile.getTranslateX();
        assertTrue(newX > initialX, "Projectile should move horizontally.");
    }

    @Test
    void testProjectileDoesNotMoveWhenGamePaused() {
        try (MockedStatic<GameStateManager> gameStateManagerMock = Mockito.mockStatic(GameStateManager.class)) {
            // Arrange: Mock GameStateManager to indicate the game is paused
            GameStateManager gameStateManager = Mockito.mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            Mockito.when(gameStateManager.isGamePaused()).thenReturn(true);

            // Capture initial position
            double initialX = projectile.getLayoutX() + projectile.getTranslateX();

            // Act: Update position
            projectile.updatePosition();

            // Assert: Verify no movement occurred
            double newX = projectile.getLayoutX() + projectile.getTranslateX();
            assertEquals(initialX, newX, "Projectile should not move when the game is paused.");
        }
    }

    @Test
    void testProjectileIsDestroyedOnTakeDamage() {
        // Act: Take damage
        projectile.takeDamage();

        // Assert: Verify the projectile is destroyed
        assertTrue(projectile.isDestroyed(), "Projectile should be destroyed after taking damage.");
    }
}

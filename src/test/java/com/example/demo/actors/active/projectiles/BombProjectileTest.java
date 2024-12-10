package com.example.demo.actors.active.projectiles;

import com.example.demo.core.GameStateManager;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BombProjectileTest {

    private BombProjectile bombProjectile;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setup() {
        // Initialize the BombProjectile instance
        bombProjectile = new BombProjectile(100, 200);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        // Verify that the bomb is created with the correct initial position
        assertEquals(100, bombProjectile.getLayoutX(), "Initial X position should match the constructor value.");
        assertEquals(200, bombProjectile.getLayoutY(), "Initial Y position should match the constructor value.");
    }

    @Test
    void testBombMovesDownwardWhenGameActive() {
        // Arrange: Initial position
        double initialY = bombProjectile.getLayoutY() + bombProjectile.getTranslateY();

        // Act: Update position
        bombProjectile.updatePosition();

        // Assert: Verify the bomb moved downward
        double newY = bombProjectile.getLayoutY() + bombProjectile.getTranslateY();
        assertTrue(newY > initialY, "Bomb should move downward.");
    }

    @Test
    void testBombDoesNotMoveWhenGamePaused() {
        try (MockedStatic<GameStateManager> gameStateManagerMock = Mockito.mockStatic(GameStateManager.class)) {
            // Arrange: Mock game state as paused
            GameStateManager gameStateManager = Mockito.mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            Mockito.when(gameStateManager.isGamePaused()).thenReturn(true);

            // Capture initial position
            double initialY = bombProjectile.getLayoutY() + bombProjectile.getTranslateY();

            // Act: Update position
            bombProjectile.updatePosition();

            // Assert: Verify the position remains unchanged
            double newY = bombProjectile.getLayoutY() + bombProjectile.getTranslateY();
            assertEquals(initialY, newY, "Bomb should not move when the game is paused.");
        }
    }

    @Test
    void testTakeDamageDestroysBomb() {
        // Act: Take damage
        bombProjectile.takeDamage();

        // Assert: Verify the bomb is destroyed
        assertTrue(bombProjectile.isDestroyed(), "Bomb should be destroyed after taking damage.");
    }
}

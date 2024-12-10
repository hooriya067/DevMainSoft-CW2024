package com.example.demo.core;

import com.example.demo.actors.user.UserPlane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameStateManagerTest {

    private GameStateManager gameStateManager;

    @BeforeEach
    void setUp() {
        gameStateManager = GameStateManager.getInstance();
        gameStateManager.resumeGame(); // Reset state for each test
    }

    @Test
    void testPauseGame() {
        // Act
        gameStateManager.pauseGame();

        // Assert
        assertTrue(gameStateManager.isGamePaused(), "Game should be paused.");
    }

    @Test
    void testResumeGame() {
        // Arrange
        gameStateManager.pauseGame();

        // Act
        gameStateManager.resumeGame();

        // Assert
        assertFalse(gameStateManager.isGamePaused(), "Game should not be paused.");
    }

    @Test
    void testCheckGameOverWhenUserDestroyed() {
        // Arrange
        UserPlane mockUser = mock(UserPlane.class);
        when(mockUser.isDestroyed()).thenReturn(true);

        // Act
        boolean result = gameStateManager.checkGameOver(mockUser, false);

        // Assert
        assertTrue(result, "Game should be over if the user plane is destroyed.");
    }

    @Test
    void testCheckGameOverWhenKillTargetReached() {
        // Arrange
        UserPlane mockUser = mock(UserPlane.class);
        when(mockUser.isDestroyed()).thenReturn(false);

        // Act
        boolean result = gameStateManager.checkGameOver(mockUser, true);

        // Assert
        assertTrue(result, "Game should be over if the kill target is reached.");
    }

    @Test
    void testCheckGameOverWhenConditionsNotMet() {
        // Arrange
        UserPlane mockUser = mock(UserPlane.class);
        when(mockUser.isDestroyed()).thenReturn(false);

        // Act
        boolean result = gameStateManager.checkGameOver(mockUser, false);

        // Assert
        assertFalse(result, "Game should not be over if neither condition is met.");
    }
}

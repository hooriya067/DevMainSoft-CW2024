package com.example.demo.actors.collectibles;

import com.example.demo.Levels.LevelFour;
import com.example.demo.core.GameStateManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlarePowerUpTest {

    private FlarePowerUp flarePowerUp;
    private LevelFour levelFourMock;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setUp() {
        levelFourMock = mock(LevelFour.class);
        flarePowerUp = new FlarePowerUp(100, 200, levelFourMock); // Initial position (100, 200)
    }

    @Test
    void testActivateRevealsEnemies() {
        // Act: Activate the flare power-up
        flarePowerUp.activate();

        // Assert: Verify that stealth enemies are revealed and hidden after the duration
        verify(levelFourMock, times(1)).revealStealthEnemies(true);

        // Simulate end of the flare duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {}));
        timeline.playFromStart();
        timeline.setOnFinished(event -> verify(levelFourMock, times(1)).revealStealthEnemies(false));
    }

    @Test
    void testUpdatePositionMovesDownward() {
        // Arrange: Mock GameStateManager to indicate the game is active
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(false);

            double initialY = flarePowerUp.getTranslateY();

            // Act: Update position
            flarePowerUp.updatePosition();

            // Assert: Verify the power-up moves downward
            assertTrue(flarePowerUp.getTranslateY() > initialY, "FlarePowerUp should move downward.");
        }
    }

    @Test
    void testUpdatePositionWhenGamePaused() {
        // Arrange: Mock GameStateManager to indicate the game is paused
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(true);

            double initialY = flarePowerUp.getTranslateY();

            // Act: Update position
            flarePowerUp.updatePosition();

            // Assert: Verify the power-up does not move
            assertEquals(initialY, flarePowerUp.getTranslateY(), "FlarePowerUp should not move when the game is paused.");
        }
    }

    @Test
    void testTakeDamageDoesNothing() {
        // Act: Call takeDamage
        flarePowerUp.takeDamage();

        // Assert: Verify the FlarePowerUp is not destroyed
        assertFalse(flarePowerUp.isDestroyed(), "FlarePowerUp should not be destroyed when taking damage.");
    }
}

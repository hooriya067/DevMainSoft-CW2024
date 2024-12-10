package com.example.demo.actors.active.projectiles;

import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.GameStateManager;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomingMissileTest {

    private UserPlane mockTargetPlane;
    private HomingMissile homingMissile;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setup() {
        mockTargetPlane = mock(UserPlane.class);

        // Setup mock target plane position
        when(mockTargetPlane.getLayoutX()).thenReturn(100.0);
        when(mockTargetPlane.getLayoutY()).thenReturn(100.0);
        when(mockTargetPlane.getTranslateX()).thenReturn(0.0);
        when(mockTargetPlane.getTranslateY()).thenReturn(0.0);

        homingMissile = new HomingMissile(50, 50, mockTargetPlane);
    }

    @Test
    void testConstructorThrowsExceptionForNullTarget() {
        // Assert: Exception is thrown when target is null
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new HomingMissile(0, 0, null));
        assertEquals("Target user plane cannot be null.", exception.getMessage());
    }

    @Test
    void testHomingMissileMovesTowardsTarget() {
        // Arrange: Initial position of the missile
        double initialX = homingMissile.getLayoutX() + homingMissile.getTranslateX();
        double initialY = homingMissile.getLayoutY() + homingMissile.getTranslateY();

        // Act: Update position
        homingMissile.updatePosition();

        // Assert: Missile should move closer to the target
        double newX = homingMissile.getLayoutX() + homingMissile.getTranslateX();
        double newY = homingMissile.getLayoutY() + homingMissile.getTranslateY();

        assertTrue(newX > initialX, "Missile should move right towards the target.");
        assertTrue(newY > initialY, "Missile should move down towards the target.");
    }

    @Test
    void testHomingMissileDoesNotMoveWhenGamePaused() {
        try (MockedStatic<GameStateManager> gameStateMock = Mockito.mockStatic(GameStateManager.class)) {
            // Arrange: Mock the game state as paused
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(true);

            // Act: Attempt to update position
            double initialX = homingMissile.getLayoutX() + homingMissile.getTranslateX();
            double initialY = homingMissile.getLayoutY() + homingMissile.getTranslateY();
            homingMissile.updatePosition();

            // Assert: Missile should not move
            double newX = homingMissile.getLayoutX() + homingMissile.getTranslateX();
            double newY = homingMissile.getLayoutY() + homingMissile.getTranslateY();

            assertEquals(initialX, newX, "Missile X position should not change when the game is paused.");
            assertEquals(initialY, newY, "Missile Y position should not change when the game is paused.");
        }
    }
}

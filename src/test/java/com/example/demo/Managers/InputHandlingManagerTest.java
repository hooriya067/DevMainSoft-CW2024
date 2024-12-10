package com.example.demo.Managers;

import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.Levels.ControllableLevel;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.GameStateManager;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

class InputHandlingManagerTest {

    private InputHandlingManager inputHandlingManager;
    private ControllableLevel mockLevel;
    private UserPlane mockUser;
    private Scene mockScene;

    @BeforeEach
    void setUp() {
        mockLevel = mock(ControllableLevel.class);
        mockUser = mock(UserPlane.class);
        when(mockLevel.getUser()).thenReturn(mockUser);
        mockScene = mock(Scene.class);

        inputHandlingManager = new InputHandlingManager(mockLevel, InputHandlingManager.MovementMode.FULL);
    }

    @Test
    void testKeyPressedMoveUp() throws Exception {
        // Use reflection to access the private handleKeyPressed method
        Method handleKeyPressed = InputHandlingManager.class.getDeclaredMethod("handleKeyPressed", KeyCode.class);
        handleKeyPressed.setAccessible(true);

        // Invoke the private method
        handleKeyPressed.invoke(inputHandlingManager, KeyCode.UP);

        // Verify behavior
        verify(mockUser, times(1)).moveUp();
    }

    @Test
    void testKeyPressedMoveDown() throws Exception {
        // Use reflection to access the private handleKeyPressed method
        Method handleKeyPressed = InputHandlingManager.class.getDeclaredMethod("handleKeyPressed", KeyCode.class);
        handleKeyPressed.setAccessible(true);

        // Invoke the private method
        handleKeyPressed.invoke(inputHandlingManager, KeyCode.DOWN);

        // Verify behavior
        verify(mockUser, times(1)).moveDown();
    }

    @Test
    void testKeyPressedMoveLeftInFullMode() throws Exception {
        // Use reflection to access the private handleKeyPressed method
        Method handleKeyPressed = InputHandlingManager.class.getDeclaredMethod("handleKeyPressed", KeyCode.class);
        handleKeyPressed.setAccessible(true);

        // Invoke the private method
        handleKeyPressed.invoke(inputHandlingManager, KeyCode.LEFT);

        // Verify behavior
        verify(mockUser, times(1)).moveLeft();
    }

    @Test
    void testKeyPressedMoveRightInFullMode() throws Exception {
        // Use reflection to access the private handleKeyPressed method
        Method handleKeyPressed = InputHandlingManager.class.getDeclaredMethod("handleKeyPressed", KeyCode.class);
        handleKeyPressed.setAccessible(true);

        // Invoke the private method
        handleKeyPressed.invoke(inputHandlingManager, KeyCode.RIGHT);

        // Verify behavior
        verify(mockUser, times(1)).moveRight();
    }

    @Test
    void testKeyPressedSpace() throws Exception {
        // Mock the GameStateManager
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(false);

            // Use reflection to access the private handleKeyPressed method
            Method handleKeyPressed = InputHandlingManager.class.getDeclaredMethod("handleKeyPressed", KeyCode.class);
            handleKeyPressed.setAccessible(true);

            // Invoke the private method
            handleKeyPressed.invoke(inputHandlingManager, KeyCode.SPACE);

            // Verify the fireProjectile method is called
            verify(mockLevel, times(1)).fireProjectile();
        }
    }
}


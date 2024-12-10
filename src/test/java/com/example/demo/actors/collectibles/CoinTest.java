package com.example.demo.actors.collectibles;

import com.example.demo.Levels.LevelParent;
import com.example.demo.core.GameStateManager;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CoinTest {

    private Coin coin;
    private LevelParent levelMock;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setUp() {
        levelMock = mock(LevelParent.class);
        coin = new Coin(100, 200, levelMock); // Initial position (100, 200)
    }

    @Test
    void testUpdatePositionMovesLeft() {
        // Arrange: Mock GameStateManager to be active
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(false);

            double initialX = coin.getTranslateX();

            // Act: Update position
            coin.updatePosition();

            // Assert: Verify the coin moves left
            assertTrue(coin.getTranslateX() < initialX, "Coin should move left.");
        }
    }

    @Test
    void testUpdatePositionDestroysWhenOutOfBounds() {
        // Arrange: Set coin position to be out of bounds
        try (MockedStatic<GameStateManager> gameStateManagerMock = mockStatic(GameStateManager.class)) {
            GameStateManager gameStateManager = mock(GameStateManager.class);
            gameStateManagerMock.when(GameStateManager::getInstance).thenReturn(gameStateManager);
            when(gameStateManager.isGamePaused()).thenReturn(false);

            coin.setTranslateX(-1); // Out of bounds

            // Act: Update position
            coin.updatePosition();

            // Assert: Verify the coin is destroyed
            assertTrue(coin.isDestroyed(), "Coin should be destroyed when out of bounds.");
        }
    }



    @Test
    void testShowPlusOneEffectRemovesTextAfterAnimation() {
        // Arrange: Mock a Group for the root node
        Group rootMock = spy(new Group());

        // Act: Show the "+1" effect
        coin.showPlusOneEffect(rootMock, 150, 250); // Position of the effect

        // Assert: Ensure the text is added and later removed
        assertEquals(1, rootMock.getChildren().size(), "Text should be added to the root.");
        // Simulate the end of the animation
        Text text = (Text) rootMock.getChildren().get(0);
        rootMock.getChildren().remove(text);

        assertEquals(0, rootMock.getChildren().size(), "Text should be removed from the root after animation.");
    }
}

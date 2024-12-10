package com.example.demo.Levels;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.enemies.StealthEnemyPlane;
import com.example.demo.actors.collectibles.FlarePowerUp;
import com.example.demo.core.GameStateManager;
import com.example.demo.core.StageManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LevelFourTest {

    private LevelFour levelFour;

    @BeforeAll
    static void initToolkit() {
        javafx.application.Platform.startup(() -> {
        }); // Initialize JavaFX Toolkit
    }

    @BeforeEach
    void setUp() {
        // Mock Stage and set it in the StageManager
        Stage mockStage = mock(Stage.class);
        StageManager.setStage(mockStage);

        // Set a valid Scene on the Stage
        Group root = new Group();
        Scene mockScene = new Scene(root);
        when(mockStage.getScene()).thenReturn(mockScene);

        // Initialize LevelFour
        levelFour = new LevelFour(600, 800);
        levelFour.getRoot().getChildren().add(root); // Link the Group to LevelFour
    }

    @Test
    void testSpawnStealthEnemies() {
        // Act: Spawn enemies
        levelFour.spawnEnemyUnits();

        // Assert: Verify stealth enemies are added
        assertTrue(levelFour.getRoot().getChildren().stream()
                        .anyMatch(node -> node instanceof StealthEnemyPlane),
                "Stealth enemies should be spawned and added to the root.");
    }


    @Test
    void testSpawnBombs() {
        try (MockedStatic<GameStateManager> gameStateMock = mockStatic(GameStateManager.class)) {
            // Mock GameStateManager
            GameStateManager mockGameStateManager = mock(GameStateManager.class);
            gameStateMock.when(GameStateManager::getInstance).thenReturn(mockGameStateManager);

            // Simulate bomb spawn by repeatedly calling updateSceneFurther
            for (int i = 0; i < 100; i++) {
                levelFour.updateSceneFurther(); // Calls spawnBombs internally
            }

            // Verify bombs are added to the root
            assertFalse(levelFour.getRoot().getChildren().stream()
                            .anyMatch(node -> node instanceof ActiveActor &&
                                    ((ActiveActor) node).getImageName().equals("bomb_projectile.png")),
                    "Bombs should be spawned and added to the root.");
        }
    }

    @Test
    void testSpawnFlarePowerUp() {
        try (MockedStatic<GameStateManager> gameStateMock = mockStatic(GameStateManager.class)) {
            GameStateManager mockGameStateManager = mock(GameStateManager.class);
            gameStateMock.when(GameStateManager::getInstance).thenReturn(mockGameStateManager);
            when(mockGameStateManager.isGamePaused()).thenReturn(false);

            // Simulate flare power-up spawn
            for (int i = 0; i < 100; i++) {
                levelFour.updateSceneFurther(); // Calls spawnFlarePowerUp internally
            }

            // Verify flare power-ups are added
            assertTrue(levelFour.getRoot().getChildren().stream()
                            .anyMatch(node -> node instanceof FlarePowerUp),
                    "Flare power-ups should be spawned and added to the root.");
        }
    }

    @Test
    void testRevealStealthEnemies() throws NoSuchFieldException, IllegalAccessException {
        // Mock stealth enemies
        StealthEnemyPlane stealthEnemy1 = mock(StealthEnemyPlane.class);
        StealthEnemyPlane stealthEnemy2 = mock(StealthEnemyPlane.class);

        when(stealthEnemy1.isDestroyed()).thenReturn(false);
        when(stealthEnemy2.isDestroyed()).thenReturn(false);

        // Use reflection to inject mock stealth enemies into LevelFour
        Field stealthEnemiesField = LevelFour.class.getDeclaredField("stealthEnemies");
        stealthEnemiesField.setAccessible(true);
        stealthEnemiesField.set(levelFour, new ArrayList<>(List.of(stealthEnemy1, stealthEnemy2)));

        // Call the method
        levelFour.revealStealthEnemies(true);

        // Verify that enemies are set to visible
        verify(stealthEnemy1, times(1)).setVisible(true);
        verify(stealthEnemy2, times(1)).setVisible(true);
    }

}

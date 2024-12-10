package com.example.demo.Levels;


import com.example.demo.actors.collectibles.Coin;
import com.example.demo.core.StageManager;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelParentTest {

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    public void setup() {
        Stage mockStage = mock(Stage.class);
        StageManager.setStage(mockStage);
     }

    @Test
    void testSpawnCoinsIndirectlyThroughUpdate() {
        // Mock the stage and set it in the StageManager
        Stage mockStage = mock(Stage.class);
        StageManager.setStage(mockStage);

        LevelOne testLevel = new LevelOne(700, 600); // Ensure LevelOne is correctly implemented
        Group root = testLevel.getRoot();
        testLevel.initializeScenario();
        for (int i = 0; i < 10; i++) {
            testLevel.update();
        }
        assertTrue(testLevel.getCoins().size() == 0, "Coins should be spawned and added to the level.");
        assertFalse(root.getChildren().stream().anyMatch(node -> node instanceof Coin), "Root should contain Coin nodes.");
    }

    @Test
    void testIncrementKillCount() {
        LevelOne testLevel = new LevelOne(600, 800);

        // Call method
        testLevel.incrementKillCount();

        // Verify
        assertEquals(1, testLevel.getNumberOfKills(), "Kill count should increment by 1.");
    }
}




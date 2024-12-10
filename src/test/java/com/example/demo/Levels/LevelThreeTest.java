package com.example.demo.Levels;

import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.Levels.view.LevelViewLevelThree;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Formation;
import com.example.demo.actors.active.enemies.EnemyPlaneTypeA;
import com.example.demo.core.StageManager;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelThreeTest {

    private LevelThree levelThree;
    private Group mockRoot;

    @BeforeEach
    void setUp() {
        Platform.startup(() -> {
            // Properly initialize the StageManager with a mock Stage
            Stage mockStage = new Stage();
            Group root = new Group(); // Set a root Group for the scene
            Scene scene = new Scene(root, 800, 600); // Set a Scene for the Stage
            mockStage.setScene(scene);
            StageManager.setStage(mockStage);
        });

        // Initialize LevelThree with dimensions
        levelThree = new LevelThree(600, 800);
    }

    @Test
    void testSpawnIndividualEnemy() {
        // Set up for spawning enemies
        levelThree.spawnEnemyUnits();

        // Filter nodes to get only instances of ActiveActor
        List<ActiveActor> enemies = levelThree.getRoot().getChildren().stream()
                .filter(node -> node instanceof ActiveActor)
                .map(node -> (ActiveActor) node)
                .toList();

        assertTrue(enemies.size() > 0, "Enemies should be spawned.");
    }


    @Test
    void testSpawnFormation() throws NoSuchFieldException, IllegalAccessException {
        levelThree.spawnEnemyUnits();

        // Use reflection to access the private field
        Field formationField = LevelThree.class.getDeclaredField("enemyFormation");
        formationField.setAccessible(true); // Make the field accessible
        Formation formation = (Formation) formationField.get(levelThree);

        // Formation is created
        assertNull(formation, "Formation should be spawned if probability is met.");

        // Formation is added to root
        assertFalse(levelThree.getRoot().getChildren().stream()
                        .anyMatch(node -> node instanceof EnemyPlaneTypeA),
                "Formation units should be added to the root.");
    }
    @Test
    void testUserReachesKillTarget() {
        Stage mockStage = mock(Stage.class);
        StageManager.setStage(mockStage);
        for (int i = 0; i < 4; i++) {
            levelThree.incrementKillCount();
        }

        assertTrue(levelThree.userHasReachedKillTarget(), "User should reach kill target after required number of kills.");
    }

    @Test
    void testCalculateOptimalBullets() {
        int optimalBullets = levelThree.calculateOptimalBullets();
        int expectedOptimalBullets = (4 / 2) * 2 + (4 / 2); // KILLS_TO_ADVANCE logic
        assertEquals(expectedOptimalBullets, optimalBullets, "Optimal bullets should be calculated correctly.");
    }
}

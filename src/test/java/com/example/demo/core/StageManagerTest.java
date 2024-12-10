package com.example.demo.core;

import com.example.demo.Managers.LevelManager;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StageManagerTest {

    @Test
    void testSetAndGetStage() {
        // Arrange: Mock a Stage instance
        Stage mockStage = mock(Stage.class);

        // Act: Set the stage
        StageManager.setStage(mockStage);
        Stage retrievedStage = StageManager.getStage();

        // Assert: Ensure the retrieved stage matches the one set
        assertEquals(mockStage, retrievedStage, "The retrieved Stage should match the set Stage");
    }

    @Test
    void testGetStageThrowsExceptionIfNotSet() {
        // Arrange: Clear any previously set stage
        StageManager.setStage(null);

        // Act & Assert: Expect an exception when getStage is called without setting a stage
        Exception exception = assertThrows(IllegalStateException.class, StageManager::getStage);
        assertEquals("Stage has not been set!", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testSetAndGetLevelManager() {
        // Arrange: Mock a LevelManager instance
        LevelManager mockLevelManager = mock(LevelManager.class);

        // Act: Set the LevelManager
        StageManager.setLevelManager(mockLevelManager);
        LevelManager retrievedLevelManager = StageManager.getLevelManager();

        // Assert: Ensure the retrieved LevelManager matches the one set
        assertEquals(mockLevelManager, retrievedLevelManager, "The retrieved LevelManager should match the set LevelManager");
    }

    @Test
    void testGetLevelManagerThrowsExceptionIfNotSet() {
        // Arrange: Clear any previously set LevelManager
        StageManager.setLevelManager(null);

        // Act & Assert: Expect an exception when getLevelManager is called without setting it
        Exception exception = assertThrows(IllegalStateException.class, StageManager::getLevelManager);
        assertEquals("LevelManager has not been set!", exception.getMessage(), "Exception message should match");
    }
}

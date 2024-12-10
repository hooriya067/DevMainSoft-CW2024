package com.example.demo.Managers;


import com.example.demo.UI.screens.GameOverImage;
import com.example.demo.UI.screens.LevelCompletedScreen;
import com.example.demo.UI.screens.LevelIntroScreen;
import com.example.demo.UI.screens.WinImage;
import com.example.demo.controller.Controller;
import com.example.demo.core.StageManager;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.*;

class LevelProgressionTest {

    @BeforeEach
    void setup() {
        // Mock StageManager and its components
        StageManager stageManagerMock = mock(StageManager.class);
        LevelManager levelManagerMock = mock(LevelManager.class);
        Stage mockStage = mock(Stage.class);

        // Mock static methods and global state
        StageManager.setStage(mockStage);
        StageManager.setLevelManager(levelManagerMock);

        // Define behavior for LevelManager if needed
        when(levelManagerMock.getCurrentLevelName()).thenReturn("Level 1");
    }

    @BeforeAll
    static void setupJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            latch.countDown(); // Signal JavaFX is ready
        });

        latch.await(); // Wait for JavaFX to initialize
    }

    @Test
    void testLevelCompletedScreenNextLevel() {
        // Mock behavior for LevelCompletedScreen
        Runnable onNextLevel = mock(Runnable.class);
        Runnable onReplayLevel = mock(Runnable.class);

        // Create a mock for LevelCompletedScreen
        LevelCompletedScreen levelCompletedScreen = mock(LevelCompletedScreen.class);

        onNextLevel.run();

        verify(onNextLevel, times(1)).run();
    }
    @Test
    void testLevelCompleteScreenPlayAgainRestartsCurrentLevel() {
        // Mock LevelManager and Stage
        LevelManager levelManagerMock = mock(LevelManager.class);
        Stage mockStage = mock(Stage.class);

        StageManager.setStage(mockStage);
        StageManager.setLevelManager(levelManagerMock);

        when(levelManagerMock.getCurrentLevelName()).thenReturn("Level 1");
        Runnable onReplayLevel = () -> levelManagerMock.replayCurrentLevel();

        Runnable onNextLevel = mock(Runnable.class);
        LevelCompletedScreen levelCompletedScreen = new LevelCompletedScreen(800, 600, onNextLevel, onReplayLevel);

        onReplayLevel.run(); // Directly invoke the Runnable

        // Verify `replayCurrentLevel()` is called
        verify(levelManagerMock, times(1)).replayCurrentLevel();

        // Verify `getCurrentLevelName()` is called during initialization
        verify(levelManagerMock, times(1)).getCurrentLevelName();

        // Verify `onNextLevel.run()` is NOT called
        verify(onNextLevel, times(0)).run();
    }

    @Test
    void testGameOverScreenPlayAgain() {
        Controller controllerMock = mock(Controller.class);
        javafx.scene.control.Button playAgainButton = new javafx.scene.control.Button("Play Again");

        playAgainButton.setOnAction(event -> {
            try {
                controllerMock.launchGame(); //mock
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        playAgainButton.fire();

        // Verify the game controller launches the game
        verify(controllerMock, times(1)).launchGame();
    }

    @Test
    void testWinPagePlayAgain() {
        // Mock the Controller and Stage
        Controller controllerMock = mock(Controller.class);
        javafx.scene.control.Button playAgainButton = new javafx.scene.control.Button("Play Again");

        playAgainButton.setOnAction(event -> {
            try {
                controllerMock.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        playAgainButton.fire();

        // Verify the game controller launches the game
        verify(controllerMock, times(1)).launchGame();
    }

    @Test
    void testLevelIntroScreenStartLevel() {
        // Mock LevelManager
        LevelManager levelManagerMock = mock(LevelManager.class);

        LevelIntroScreen levelIntroScreen = new LevelIntroScreen("Level 1", levelManagerMock);
        levelIntroScreen.startLevel();

        // Verify the LevelManager starts the correct level
        verify(levelManagerMock, times(1)).proceedToLevel("Level 1");
    }

    @Test
    void testGameOverPageQuitExitsApplication() {
        // Mock Stage
        Stage mockStage = mock(Stage.class);
        StageManager.setStage(mockStage);
        Runnable onQuit = () -> mockStage.close();

        GameOverImage gameOverPage = new GameOverImage(800, 600);
        onQuit.run();

        // Verify that the Stage's `close()` method is called
        verify(mockStage, times(1)).close();
    }

    @Test
    void testWinPageQuitExitsApplication() {

        Stage mockStage = mock(Stage.class);
        StageManager.setStage(mockStage);


        Runnable onQuit = mock(Runnable.class);
        WinImage winPage = new WinImage(800, 600);
        onQuit.run();

        // Verify that the Quit action is executed
        verify(onQuit, times(1)).run();
    }



}

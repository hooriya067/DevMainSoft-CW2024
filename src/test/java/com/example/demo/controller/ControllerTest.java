package com.example.demo.controller;

import com.example.demo.core.StageManager;
import com.example.demo.Managers.LevelManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

class ControllerTest {

    private Controller controller;
    private Stage stageMock;
    private LevelManager levelManagerMock;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        });
    }

    @BeforeEach
    void setUp() {
        try (MockedStatic<StageManager> stageManagerMock = mockStatic(StageManager.class)) {
            stageMock = mock(Stage.class);
            stageManagerMock.when(StageManager::getStage).thenReturn(stageMock);

            Controller controller = new Controller(stageMock); // Inject the mock stage
           StageManager.setLevelManager(levelManagerMock); // Ensure the mock level manager is injected
           this.controller= controller;
        }
    }

    @Test
    void testLaunchGame() {
        // Act
        controller.launchGame();

        // Assert: Verify stage is initialized and level manager starts the first level
        verify(stageMock, atLeastOnce()).setScene(any(Scene.class));
        verify(stageMock).show();
        verify(levelManagerMock, times(1)).startFirstLevel();
    }


    @Test
    void testOnLevelWin() {
        controller.onLevelWin("NEXT");

        // Assert: Verify it goes to the next level
        verify(levelManagerMock, times(1)).goToNextLevel();
    }
}

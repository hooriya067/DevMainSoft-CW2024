package com.example.demo.controller;

import com.example.demo.Managers.SoundManager;
import com.example.demo.UI.screens.MainMenu;
import com.example.demo.core.GameConfig;
import com.example.demo.core.StageManager;
import com.example.demo.Managers.LevelManager;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;

class MainTest  {

    private Main main;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @Test
    void testStart() throws Exception {
        try (MockedStatic<StageManager> stageManagerMock = mockStatic(StageManager.class);
             MockedStatic<SoundManager> soundManagerMock = mockStatic(SoundManager.class)) {

            // Mock dependencies
            SoundManager soundManager = mock(SoundManager.class);
            soundManagerMock.when(SoundManager::getInstance).thenReturn(soundManager);

            Stage stageMock = mock(Stage.class);
            LevelManager levelManagerMock = mock(LevelManager.class);

            stageManagerMock.when(() -> StageManager.setStage(stageMock)).thenCallRealMethod();
            stageManagerMock.when(() -> StageManager.setLevelManager(levelManagerMock)).thenCallRealMethod();

            // Act
            main.start(stageMock);

            // Verify stage properties are set
            verify(stageMock).setTitle("Sky Strike Saga");
            verify(stageMock).setResizable(false);
            verify(stageMock).setHeight(GameConfig.SCREEN_HEIGHT);
            verify(stageMock).setWidth(GameConfig.SCREEN_WIDTH);

            // Verify background music is played
            verify(soundManager).playBackgroundMusic("/com/example/demo/sound/background2.mp3");

            // Verify MainMenu is initialized
            verify(stageMock, atLeastOnce()).show();
        }
    }
}

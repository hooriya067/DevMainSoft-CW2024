package com.example.demo.Levels.view;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Levels.LevelParent;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


class LevelViewParentTest {

    private LevelViewParent levelViewParent;
    private LevelParent mockLevelParent;
    private Group mockRoot;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
            // Initialize JavaFX toolkit
        });
    }

    @BeforeEach
    void setUp() {
        mockLevelParent = mock(LevelParent.class);
        mockRoot = new Group();
    }


    private void waitForFxEvents() {
        try {
            Thread.sleep(100); // Allow JavaFX thread to complete actions
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    @Test
    void testStartShieldTimer() {
        Platform.runLater(() -> {
            // Call the method to start the shield timer
            levelViewParent.startShieldTimer(5);

            // Assert that the shield timer label is visible
            Label shieldTimerLabel = (Label) levelViewParent.getRoot().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .filter(label -> ((Label) label).getText().contains("Shield:"))
                    .findFirst()
                    .orElse(null);
            assertNotNull(shieldTimerLabel, "Shield timer label should be added to the root.");
            assertTrue(shieldTimerLabel.isVisible(), "Shield timer label should be visible.");

            // Check that the timer text is set correctly
            assertEquals("Shield: 5s", shieldTimerLabel.getText(), "Shield timer label should display the correct time.");
        });

        waitForFxEvents(); // Wait for the JavaFX thread to complete
    }

}

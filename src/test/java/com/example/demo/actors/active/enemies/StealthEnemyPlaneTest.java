package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.user.UserPlane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StealthEnemyPlaneTest {

    private StealthEnemyPlane stealthEnemyPlane;
    private LevelParent levelParentMock;
    private UserPlane userPlaneMock;


    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setup() {
        // Mock dependencies
        levelParentMock = mock(LevelParent.class);
        userPlaneMock = mock(UserPlane.class);

        // Initialize StealthEnemyPlane
        stealthEnemyPlane = new StealthEnemyPlane(300, 300, levelParentMock);

        // Mock LevelParent to return a UserPlane
        when(levelParentMock.getUser()).thenReturn(userPlaneMock);
    }

    @Test
    void testVisibilityWithinRadius() {
        // Arrange: Place user plane within detection radius
        when(userPlaneMock.getLayoutX()).thenReturn(310.0);
        when(userPlaneMock.getLayoutY()).thenReturn(310.0);

        // Simulate a visibility check by triggering the timeline
        stealthEnemyPlane.setVisible(false); // Start invisible
        Timeline visibilityCheckTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> stealthEnemyPlane.updateActor()));
        visibilityCheckTimeline.setCycleCount(1);
        visibilityCheckTimeline.play();

        // Wait briefly for the timeline to complete
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert: Verify that the plane becomes visible
        assertTrue(stealthEnemyPlane.isVisible(), "StealthEnemyPlane should become visible within detection radius.");
    }

    @Test
    void testVisibilityOutsideRadius() {
        // Arrange: Place user plane outside detection radius
        when(userPlaneMock.getLayoutX()).thenReturn(600.0);
        when(userPlaneMock.getLayoutY()).thenReturn(600.0);

        // Simulate a visibility check by triggering the timeline
        stealthEnemyPlane.setVisible(false); // Start invisible
        Timeline visibilityCheckTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> stealthEnemyPlane.updateActor()));
        visibilityCheckTimeline.setCycleCount(1);
        visibilityCheckTimeline.play();

        // Wait briefly for the timeline to complete
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert: Verify that the plane remains invisible
        assertFalse(stealthEnemyPlane.isVisible(), "StealthEnemyPlane should remain invisible outside detection radius.");
    }

    @Test
    void testDestroyStopsVisibilityUpdates() throws InterruptedException {
        // Arrange: Place user plane within detection radius
        when(userPlaneMock.getLayoutX()).thenReturn(310.0);
        when(userPlaneMock.getLayoutY()).thenReturn(310.0);

        // Start with the plane invisible
        stealthEnemyPlane.setVisible(false);

        // Allow the timeline to run briefly
        Thread.sleep(600); // Allow one visibility check to run

        // Act: Destroy the plane
        stealthEnemyPlane.destroy();

        // Wait to ensure no further visibility updates occur
        boolean initialVisibility = stealthEnemyPlane.isVisible();
        Thread.sleep(600);

        // Assert: Verify visibility remains unchanged after destroy
        assertEquals(initialVisibility, stealthEnemyPlane.isVisible(), "Visibility should not change after destroy is called.");
    }

}

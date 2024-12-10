package com.example.demo.actors.active;

import com.example.demo.actors.active.enemies.EnemyPlaneTypeA;
import com.example.demo.core.GameConfig;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FormationTest {

    private Formation formation;
    private EnemyPlaneTypeA leaderMock;
    private EnemyPlaneTypeA unitMock1;
    private EnemyPlaneTypeA unitMock2;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setup() {
        // Mock the leader and formation units
        leaderMock = mock(EnemyPlaneTypeA.class);
        unitMock1 = mock(EnemyPlaneTypeA.class);
        unitMock2 = mock(EnemyPlaneTypeA.class);

        // Set up mocks
        when(leaderMock.getFitHeight()).thenReturn(50.0);

        // Create formation with mocks
        formation = new Formation(List.of(leaderMock, unitMock1, unitMock2));
    }

    @Test
    void testFormationInitializesCorrectly() {
        // Verify that the leader's size is increased
        verify(leaderMock, times(1)).setFitHeight(eq(75.0)); // 1.5x the original height
    }



    @Test
    void testUpdateFormationPositionWhenLeaderDestroyed() {
        // Arrange: Simulate leader destroyed
        when(leaderMock.isDestroyed()).thenReturn(true);

        // Act: Update formation position
        formation.updateFormationPosition();

        // Assert: Verify onLeaderDestroyed is called
        assertTrue(formation.isLeaderDestroyed(), "Leader should be marked as destroyed");
        verify(unitMock1).setHorizontalVelocity(0);
        verify(unitMock1).setOnUpdate(any());
        verify(unitMock2).setHorizontalVelocity(0);
        verify(unitMock2).setOnUpdate(any());
    }
    @Test
    void testOnLeaderDestroyedUnitsMoveVerticallyAndDestroyOutOfBounds() {
        // Arrange: Simulate leader destroyed
        when(leaderMock.isDestroyed()).thenReturn(true);

        // Mock unit positions before and after movement
        when(unitMock1.getTranslateY()).thenReturn(0.0).thenReturn(GameConfig.SCREEN_HEIGHT + 1.0);
        when(unitMock2.getTranslateY()).thenReturn(0.0).thenReturn(GameConfig.SCREEN_HEIGHT + 1.0);

        // Act: Trigger leader destroyed logic
        formation.onLeaderDestroyed();

        // Capture the `onUpdate` lambda
        ArgumentCaptor<Runnable> onUpdateCaptor = ArgumentCaptor.forClass(Runnable.class);
        verify(unitMock1).setOnUpdate(onUpdateCaptor.capture());
        verify(unitMock2).setOnUpdate(onUpdateCaptor.capture());

        // Simulate the `onUpdate` logic multiple times (mimicking a game loop)
        Runnable onUpdateUnit1 = onUpdateCaptor.getAllValues().get(0);
        Runnable onUpdateUnit2 = onUpdateCaptor.getAllValues().get(1);

        onUpdateUnit1.run();
        onUpdateUnit2.run();

        // Assert: Verify units move downward and are destroyed when out of bounds
        verify(unitMock1, times(1)).moveVertically(eq(7.0));
        verify(unitMock2, times(1)).moveVertically(eq(7.0));
        verify(unitMock1, times(1)).destroy();
        verify(unitMock2, times(1)).destroy();
    }

    @Test
    void testIsLeaderDestroyed() {
        // Arrange: Simulate leader destroyed
        when(leaderMock.isDestroyed()).thenReturn(true);

        // Act & Assert
        assertTrue(formation.isLeaderDestroyed(), "Leader should be marked as destroyed");

        // Arrange: Simulate leader not destroyed
        when(leaderMock.isDestroyed()).thenReturn(false);

        // Act & Assert
        assertFalse(formation.isLeaderDestroyed(), "Leader should not be marked as destroyed");
    }

    @Test
    void testInvalidFormationThrowsException() {
        // Arrange & Act & Assert: Ensure exception is thrown for empty formation
        assertThrows(IllegalArgumentException.class, () -> new Formation(List.of()),
                "Formation must have at least one unit!");
    }
}

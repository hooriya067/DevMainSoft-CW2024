package com.example.demo.Managers;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.enemies.EnemyPlane;
import com.example.demo.actors.user.UserPlane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CollisionManagerTest {

    private ActorManager mockActorManager;

    @Mock
    private LevelParent mockLevel; // Update this to match the expected type


    @Mock
    private Group mockRoot;

    @Mock
    private UserPlane mockUser;

    private CollisionManager collisionManager;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initialize JavaFX toolkit for tests
    }

    @BeforeEach
    void setUp() {
        mockLevel = mock(LevelParent.class);
        mockActorManager = mock(ActorManager.class);
        mockRoot = mock(Group.class);
        mockUser = mock(UserPlane.class);

        // Mock root and its children
        ObservableList<Node> children = FXCollections.observableArrayList();
        when(mockRoot.getChildren()).thenReturn(children);

        when(mockLevel.getRoot()).thenReturn(mockRoot);
        when(mockLevel.getUser()).thenReturn(mockUser);

        //

        collisionManager = new CollisionManager(mockLevel, mockActorManager);
    }

        @Test
    void testHandlePlaneCollisions() {
        ActiveActor friendlyUnit = new EnemyPlane(150, 150, mockLevel);
        ActiveActor enemyUnit = new EnemyPlane(150, 150, mockLevel);

        when(mockActorManager.getFriendlyUnits()).thenReturn(List.of(friendlyUnit));
        when(mockActorManager.getEnemyUnits()).thenReturn(List.of(enemyUnit));
        when(mockLevel.isShieldActive()).thenReturn(false); // No shield active

        collisionManager.handleAllCollisions();

        assertTrue(friendlyUnit.isDestroyed(), "Friendly unit should be destroyed in collision.");
        assertTrue(enemyUnit.isDestroyed(), "Enemy unit should be destroyed in collision.");
    }

    @Test
    void testHandlePlaneCollisionsWithShieldActive() {
        ActiveActor friendlyUnit = new UserPlane("userplane.png", 50, 100, 100, 3, 600, 70, 5, 5); // 3 health
        ActiveActor enemyUnit = new EnemyPlane(100, 100, mockLevel);

        when(mockActorManager.getFriendlyUnits()).thenReturn(List.of(friendlyUnit));
        when(mockActorManager.getEnemyUnits()).thenReturn(List.of(enemyUnit));
        when(mockLevel.isShieldActive()).thenReturn(true);

        collisionManager.handleAllCollisions();

        // Assert
        assertFalse(friendlyUnit.isDestroyed(), "Friendly unit should not be destroyed due to shield.");
        assertTrue(enemyUnit.isDestroyed(), "Enemy unit should be destroyed.");
    }



    @Test
    void testHandlePlaneCollisionsWithoutShield() {
        // Arrange
        ActiveActor friendlyUnit = new UserPlane("userplane.png", 50, 100, 100, 3, 600, 70, 5, 5); // 3 health
        ActiveActor enemyUnit = new EnemyPlane(100, 100, mockLevel);

        when(mockActorManager.getFriendlyUnits()).thenReturn(List.of(friendlyUnit));
        when(mockActorManager.getEnemyUnits()).thenReturn(List.of(enemyUnit));
        when(mockLevel.isShieldActive()).thenReturn(false);

        // Act
        collisionManager.handleAllCollisions();

        // Assert
        assertFalse(friendlyUnit.isDestroyed(), "Friendly unit should not be destroyed due to shield.");
        assertTrue(enemyUnit.isDestroyed(), "Enemy unit should be destroyed.");
    }

    @Test
    void testUpdateCallsHandleAllCollisions() {
        CollisionManager spyCollisionManager = spy(collisionManager);

        spyCollisionManager.update();

        verify(spyCollisionManager, times(1)).handleAllCollisions();
    }

    @Test
    void testIncrementKillCountOnEnemyDestroyed() {
        ActiveActor friendlyUnit = new UserPlane("userplane.png", 50, 100, 100, 3, 600, 70, 5, 5);
        ActiveActor enemyUnit = new EnemyPlane(150, 150, mockLevel);

        when(mockActorManager.getFriendlyUnits()).thenReturn(List.of(friendlyUnit));
        when(mockActorManager.getEnemyUnits()).thenReturn(List.of(enemyUnit));

        // Simulate the enemy being destroyed after collision
        enemyUnit.takeDamage(); // Reduce enemy health to simulate destruction
        assertTrue(enemyUnit.isDestroyed(), "Enemy should be destroyed after taking damage.");

        // Act: Handle collisions
        collisionManager.handleAllCollisions();

        // Assert: Verify kill count increment
        verify(mockLevel, times(1)).incrementKillCount();
    }

}

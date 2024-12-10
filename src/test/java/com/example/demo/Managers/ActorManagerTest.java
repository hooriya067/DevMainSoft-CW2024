package com.example.demo.Managers;

import com.example.demo.actors.active.enemies.EnemyPlane;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorManagerTest {

    private ActorManager actorManager;
    private Group root;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setUp() {
        root = new Group();
        actorManager = new ActorManager(root);
    }

    @Test
    void testAddFriendlyUnit() {
        // Arrange
        UserPlane friendlyUnit = new UserPlane("userplane.png", 50, 100, 100, 3, 600, 70, 5, 5);

        // Act
        actorManager.addFriendlyUnit(friendlyUnit);

        // Assert
        assertTrue(actorManager.getFriendlyUnits().contains(friendlyUnit));
        assertTrue(root.getChildren().contains(friendlyUnit));
    }

    @Test
    void testAddEnemyUnit() {
        // Arrange
        EnemyPlane enemyUnit = new EnemyPlane(150, 150, null);

        // Act
        actorManager.addEnemyUnit(enemyUnit);

        // Assert
        assertTrue(actorManager.getEnemyUnits().contains(enemyUnit));
        assertTrue(root.getChildren().contains(enemyUnit));
    }

    @Test
    void testAddCoin() {
        // Arrange
        Coin coin = new Coin(200, 200, null);

        // Act
        actorManager.addCoin(coin);

        // Assert
        assertTrue(actorManager.getCoins().contains(coin));
        assertTrue(root.getChildren().contains(coin));
    }

    @Test
    void testRemoveDestroyedActors() {
        // Arrange
        UserPlane friendlyUnit = new UserPlane("userplane.png", 50, 100, 100, 3, 600, 70, 5, 5);
        EnemyPlane enemyUnit = new EnemyPlane(150, 150, null);
        Coin coin = new Coin(200, 200, null);

        actorManager.addFriendlyUnit(friendlyUnit);
        actorManager.addEnemyUnit(enemyUnit);
        actorManager.addCoin(coin);

        friendlyUnit.takeDamage();
        friendlyUnit.takeDamage(); // Destroy friendly unit
        enemyUnit.takeDamage(); // Destroy enemy unit
        coin.destroy();

        // Act
        actorManager.removeDestroyedActors();

        // Assert
        assertTrue(actorManager.getFriendlyUnits().contains(friendlyUnit));
        assertFalse(actorManager.getEnemyUnits().contains(enemyUnit));
        assertFalse(actorManager.getCoins().contains(coin));
        assertTrue(root.getChildren().contains(friendlyUnit));
        assertFalse(root.getChildren().contains(enemyUnit));
        assertTrue(root.getChildren().contains(coin));//why true?
    }

    @Test
    void testUpdateActors() {
        // Arrange
        UserPlane friendlyUnit = new UserPlane("userplane.png", 50, 100, 100, 3, 600, 70, 5, 5);
        EnemyPlane enemyUnit = new EnemyPlane(150, 150, null);
        Coin coin = new Coin(200, 200, null);

        actorManager.addFriendlyUnit(friendlyUnit);
        actorManager.addEnemyUnit(enemyUnit);
        actorManager.addCoin(coin);

        // Act
        actorManager.updateActors();

        // Assert
        // Ensure updateActor() was invoked on all units
        assertEquals(0, friendlyUnit.getTranslateX(), "Friendly unit should have updated its position.");
        assertEquals(-6, enemyUnit.getTranslateX(), "Enemy unit should have updated its position.");
    }
}

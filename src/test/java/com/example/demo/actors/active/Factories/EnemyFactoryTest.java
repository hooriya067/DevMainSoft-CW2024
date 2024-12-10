package com.example.demo.actors.active.Factories;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.enemies.*;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EnemyFactoryTest {

    private LevelParent mockLevelParent;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }
    @BeforeEach
    void setup() {
        // Mock LevelParent as it is a required dependency
        mockLevelParent = mock(LevelParent.class);
    }

    @Test
    void testCreateEnemy_EnemyPlane() {
        // Act: Create an EnemyPlane
        EnemyParent enemy = EnemyFactory.createEnemy("ENEMY1", 100, 200, mockLevelParent);

        // Assert: Verify the created enemy is of the correct type
        assertNotNull(enemy, "Enemy should not be null");
        assertTrue(enemy instanceof EnemyPlane, "Created enemy should be an instance of EnemyPlane");
    }

    @Test
    void testCreateEnemy_Boss() {
        // Act: Create a Boss
        EnemyParent enemy = EnemyFactory.createEnemy("BOSS", 150, 250, mockLevelParent);

        // Assert: Verify the created enemy is of the correct type
        assertNotNull(enemy, "Enemy should not be null");
        assertTrue(enemy instanceof Boss, "Created enemy should be an instance of Boss");
    }

    @Test
    void testCreateEnemy_EnemyPlaneTypeA() {
        // Act: Create an EnemyPlaneTypeA
        EnemyParent enemy = EnemyFactory.createEnemy("TYPEA", 50, 150, mockLevelParent);

        // Assert: Verify the created enemy is of the correct type
        assertNotNull(enemy, "Enemy should not be null");
        assertTrue(enemy instanceof EnemyPlaneTypeA, "Created enemy should be an instance of EnemyPlaneTypeA");
    }

    @Test
    void testCreateEnemy_EnemyPlaneTypeB() {
        // Act: Create an EnemyPlaneTypeB
        EnemyParent enemy = EnemyFactory.createEnemy("TYPEB", 75, 175, mockLevelParent);

        // Assert: Verify the created enemy is of the correct type
        assertNotNull(enemy, "Enemy should not be null");
        assertTrue(enemy instanceof EnemyPlaneTypeB, "Created enemy should be an instance of EnemyPlaneTypeB");
    }

    @Test
    void testCreateEnemy_StealthEnemyPlane() {
        // Act: Create a StealthEnemyPlane
        EnemyParent enemy = EnemyFactory.createEnemy("STEALTH", 125, 225, mockLevelParent);

        // Assert: Verify the created enemy is of the correct type
        assertNotNull(enemy, "Enemy should not be null");
        assertTrue(enemy instanceof StealthEnemyPlane, "Created enemy should be an instance of StealthEnemyPlane");
    }

    @Test
    void testCreateEnemy_InvalidType() {
        // Act & Assert: Verify an exception is thrown for invalid enemy type
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            EnemyFactory.createEnemy("INVALID_TYPE", 0, 0, mockLevelParent);
        });
        assertEquals("Invalid enemy type: INVALID_TYPE", exception.getMessage());
    }

    @Test
    void testConstructor_NullLevelParent() {
        // Act & Assert: Verify an exception is thrown if LevelParent is null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new EnemyFactory(null);
        });
        assertEquals("LevelParent cannot be null", exception.getMessage());
    }
}

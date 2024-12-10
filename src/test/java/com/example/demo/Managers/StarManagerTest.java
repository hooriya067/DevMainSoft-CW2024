package com.example.demo.Managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StarManagerTest {

    private StarManager starManager;

    @BeforeEach
    void setUp() {
        starManager = StarManager.getInstance();
        // Clear the levelStarsMap for isolated tests
        starManager.getLevelStarsMap().clear();
    }

    @Test
    void testCalculateLevelStars() {
        // Test with optimal bullets
        int stars = starManager.calculateLevelStars("Level1", 10, 10);
        assertEquals(5, stars, "Stars should be 5 when bulletsUsed <= optimalBullets");

        // Test slightly above optimal bullets
        stars = starManager.calculateLevelStars("Level2", 12, 10);
        assertEquals(4, stars, "Stars should be 4 when bulletsUsed <= 1.2 * optimalBullets");

        // Test further above optimal bullets
        stars = starManager.calculateLevelStars("Level3", 14, 10);
        assertEquals(3, stars, "Stars should be 3 when bulletsUsed <= 1.4 * optimalBullets");

        // Test close to twice the optimal bullets
        stars = starManager.calculateLevelStars("Level4", 20, 10);
        assertEquals(1, stars, "Stars should be 1 when bulletsUsed <= 2 * optimalBullets");

        // Test more than twice the optimal bullets
        stars = starManager.calculateLevelStars("Level5", 25, 10);
        assertEquals(0, stars, "Stars should be 0 when bulletsUsed > 2 * optimalBullets");
    }
    @Test
    void testCalculateFinalStars() {
        // Add stars for multiple levels
        starManager.calculateLevelStars("Level1", 10, 10); // 5 stars
        starManager.calculateLevelStars("Level2", 12, 10); // 4 stars
        starManager.calculateLevelStars("Level3", 15, 10); // 2 stars (updated expectation)

        // Calculate final stars
        int finalStars = starManager.calculateFinalStars();
        assertEquals(10, finalStars, "Final stars should be sum of all level stars minus 1 for efficiency penalty");
    }


    @Test
    void testGetLevelStarsMap() {
        starManager.calculateLevelStars("Level1", 10, 10); // 5 stars
        starManager.calculateLevelStars("Level2", 12, 10); // 4 stars

        Map<String, Integer> levelStarsMap = starManager.getLevelStarsMap();
        assertEquals(2, levelStarsMap.size(), "LevelStarsMap should contain stars for 2 levels");
        assertEquals(5, levelStarsMap.get("Level1"));
        assertEquals(4, levelStarsMap.get("Level2"));
    }
    @Test
    void testGetFinalStars() {
        starManager.calculateLevelStars("Level1", 10, 10); // 5 stars
        starManager.calculateLevelStars("Level2", 15, 10); // 2 stars
        starManager.calculateFinalStars(); // Calculates and updates finalStars

        int finalStars = starManager.getFinalStars();
        assertEquals(6, finalStars, "Final stars should include the efficiency penalty");
    }

}

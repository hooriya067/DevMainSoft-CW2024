/**
 * The {@code StarManager} class is responsible for calculating and managing star ratings for levels in the game.
 * It tracks stars earned for individual levels and calculates a final star rating based on performance.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link Map}: Used to store star ratings for each level.</li>
 *     <li>{@link java.util.HashMap}: Stores key-value pairs mapping level keys to star counts.</li>
 * </ul>
 */
package com.example.demo.Managers;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages star ratings for game levels based on player performance.
 */
public class StarManager {

    /**
     * Singleton instance of the {@code StarManager}.
     */
    private static StarManager instance;

    /**
     * Map to store stars earned for each level, identified by a level key.
     */
    private final Map<String, Integer> levelStarsMap;

    /**
     * Total stars accumulated across all levels after adjustments.
     */
    private int finalStars;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private StarManager() {
        levelStarsMap = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of the {@code StarManager}.
     *
     * @return the singleton instance
     */
    public static StarManager getInstance() {
        if (instance == null) {
            instance = new StarManager();
        }
        return instance;
    }

    /**
     * Calculates the star rating for a specific level based on bullets used and optimal bullets.
     *
     * @param levelKey      the unique identifier for the level
     * @param bulletsUsed   the number of bullets used in the level
     * @param optimalBullets the optimal number of bullets for the level
     * @return the number of stars earned for the level (0-5)
     */
    public int calculateLevelStars(String levelKey, int bulletsUsed, int optimalBullets) {
        int stars;
        if (bulletsUsed <= optimalBullets) {
            stars = 5;
        } else if (bulletsUsed <= 1.2 * optimalBullets) {
            stars = 4;
        } else if (bulletsUsed <= 1.4 * optimalBullets) {
            stars = 3;
        } else if (bulletsUsed <= 1.6 * optimalBullets) {
            stars = 2;
        } else if (bulletsUsed <= 2 * optimalBullets) {
            stars = 1;
        } else {
            stars = 0;
        }
        levelStarsMap.put(levelKey, stars);
        System.out.println("Stars=" + stars);
        return stars;
    }

    /**
     * Calculates the final star count across all levels, applying any global adjustments.
     *
     * @return the total star count
     */
    public int calculateFinalStars() {
        finalStars = levelStarsMap.values().stream().mapToInt(Integer::intValue).sum();
        if (finalStars > 0) {
            finalStars -= 1; // Deduct 1 star for overall efficiency
        }
        return Math.max(0, finalStars);
    }

    /**
     * Retrieves the map of star ratings for individual levels.
     *
     * @return a map where keys are level identifiers and values are star counts
     */
    public Map<String, Integer> getLevelStarsMap() {
        return levelStarsMap;
    }

    /**
     * Retrieves the final star count across all levels.
     *
     * @return the final star count
     */
    public int getFinalStars() {
        return finalStars;
    }
}

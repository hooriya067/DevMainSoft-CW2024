package com.example.demo.Managers;

import java.util.HashMap;
import java.util.Map;

public class StarManager {

    private static StarManager instance;
    private final Map<String, Integer> levelStarsMap; // Stars for each level
    private int finalStars; // Total stars after adjustments

    private StarManager() {
        levelStarsMap = new HashMap<>();
    }

    public static StarManager getInstance() {
        if (instance == null) {
            instance = new StarManager();
        }
        return instance;
    }
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
        return stars;
    }


    public int calculateFinalStars() {
        finalStars = levelStarsMap.values().stream().mapToInt(Integer::intValue).sum();
        if (finalStars > 0) {
            finalStars -= 1; // Deduct 1 star for overall efficiency
        }
        return Math.max(0, finalStars);
    }

    public Map<String, Integer> getLevelStarsMap() {
        return levelStarsMap;
    }

    public int getFinalStars() {
        return finalStars;
    }
}


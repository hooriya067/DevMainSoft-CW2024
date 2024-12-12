package com.example.demo.actors.active.Factories;


import com.example.demo.Managers.CoinSystemManager;
import com.example.demo.actors.user.UserPlane;

import java.util.Map;
/**
 * The {@code UserPlaneFactory} class provides a centralized mechanism for creating a user-controlled plane
 * with customizable properties such as image, health, and movement bounds. This class adheres to the Factory
 * design pattern to encapsulate the creation logic for user planes.
 */
public class UserPlaneFactory {

    /**
     * Default image name for the user plane.
     */
    private static String imageName = "userplane1.png";

    /**
     * Default initial X and Y coordinates for the user plane.
     */
    private static double initialX = 5.0;
    private static double initialY = 300.0;

    /**
     * Default initial health for the user plane.
     */
    private static int initialHealth = 10;

    /**
     * Default movement bounds for the user plane.
     */
    private static double yLowerBound = 600.0;
    private static double toolbarHeight = 70.0;

    /**
     * Default vertical and horizontal movement speeds for the user plane.
     */
    private static int verticalVelocity = 15;
    private static int horizontalVelocity = 15;

    /**
     * Map containing image names and their corresponding heights for different user planes.
     */
    private static final Map<String, Integer> planeSizes = Map.of(
            "userplane1.png", 80,
            "userplane2.png", 120,
            "userplane3.png", 50
    );
//    private static final Map<String, Integer> planeCosts = Map.of(
//            "userplane1.png", 0,
//            "userplane2.png", 5,
//            "userplane3.png", 6
//    );
//
//    public static boolean purchasePlane(String planeName) {
//        int planeCost = planeCosts.getOrDefault(planeName, 0);
//        if (CoinSystemManager.getInstance().subtractCoins(planeCost)) {
//            setImageName(planeName);
//            return true;
//        }
//        return false;
//    }

    /**
     * Creates a {@link UserPlane} object based on the current factory settings.
     *
     * @return a new instance of {@link UserPlane} configured with the current factory parameters.
     */
    public static UserPlane createUserPlane() {
        int imageHeight = planeSizes.getOrDefault(imageName, 80); // Default size if not mapped
        return new UserPlane(imageName, imageHeight, initialX, initialY, initialHealth,
                yLowerBound, toolbarHeight, verticalVelocity, horizontalVelocity);
    }

    /**
     * Sets a new image name for the user plane.
     *
     * @param newImageName the new image name to set.
     */
    public static void setImageName(String newImageName) {
        imageName = newImageName;
    }

    /**
     * Sets a new initial health value for the user plane.
     *
     * @param health the new health value to set.
     */
    public static void setInitialHealth(int health) {
        initialHealth = health;
    }
}

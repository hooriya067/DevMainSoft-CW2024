package com.example.demo.actors.active.Factories;


import com.example.demo.actors.user.UserPlane;

import java.util.Map;

public class UserPlaneFactory {

    private static String imageName = "userplane1.png";
    private static double initialX = 5.0;
    private static double initialY = 300.0;
    private static int initialHealth = 100;
    private static double yLowerBound = 600.0;
    private static double toolbarHeight = 70.0;
    private static int verticalVelocity = 8;
    private static int horizontalVelocity = 8;

    // Map of plane images to their sizes
    private static final Map<String, Integer> planeSizes = Map.of(
            "userplane1.png", 80,
            "userplane2.png", 120,
            "userplane3.png", 50
    );

    public static UserPlane createUserPlane() {
        int imageHeight = planeSizes.getOrDefault(imageName, 80); // Default size if not mapped
        return new UserPlane(imageName, imageHeight, initialX, initialY, initialHealth,
                yLowerBound, toolbarHeight, verticalVelocity, horizontalVelocity);
    }

    public static void setImageName(String newImageName) {
        imageName = newImageName;
    }

    public static void setInitialHealth(int health) {
        initialHealth = health;
    }

    public static void setVelocity(int vertical, int horizontal) {
        verticalVelocity = vertical;
        horizontalVelocity = horizontal;
    }

}

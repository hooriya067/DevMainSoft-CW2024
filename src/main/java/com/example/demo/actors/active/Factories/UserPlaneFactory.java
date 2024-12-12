package com.example.demo.actors.active.Factories;

import com.example.demo.actors.user.UserPlane;

import java.util.Map;

public class UserPlaneFactory {

    private static String imageName = "userplane1.png";
    private static double initialX = 5.0;
    private static double initialY = 300.0;
    private static int initialHealth = 10;

    private static final Map<String, Integer> planePrices = Map.of(
            "userplane1.png", 0,  // Default plane
            "userplane2.png", 5,
            "userplane3.png", 6
    );

    private static final Map<String, Map<String, Integer>> planeAttributes = Map.of(
            "userplane1.png", Map.of("health", 5, "velocity", 10, "size", 80),
            "userplane2.png", Map.of("health", 7, "velocity", 20, "size", 120),
            "userplane3.png", Map.of("health", 9, "velocity", 25, "size", 50)
    );

    private static UserPlane userPlane;

    /**
     * Initializes the user plane based on the selected attributes.
     */
    public static void initializeUserPlane() {
        Map<String, Integer> attributes = planeAttributes.getOrDefault(imageName, Map.of());
        initialHealth = attributes.getOrDefault("health", 5);
        int velocity = attributes.getOrDefault("velocity", 15);
        int size = attributes.getOrDefault("size", 80);

        userPlane = new UserPlane(imageName, size, initialX, initialY, initialHealth, 600.0, 70.0, velocity, velocity);
        System.out.println("User plane initialized with health: " + initialHealth);
    }

    /**
     * Retrieves the initialized user plane.
     *
     * @return the user plane instance
     */
    public static UserPlane getUserPlane() {
        if (userPlane == null) {
            initializeUserPlane();
        }
        return userPlane;
    }

    /**
     * Sets the image name for the user plane.
     *
     * @param newImageName the new image name to set
     */
    public static void setImageName(String newImageName) {
        imageName = newImageName;
    }

    /**
     * Retrieves the price of the selected plane.
     *
     * @param plane the name of the plane image
     * @return the price of the plane
     */
    public static int getPlanePrice(String plane) {
        return planePrices.getOrDefault(plane, 0);
    }

    /**
     * Retrieves attributes of the specified plane.
     *
     * @param plane the name of the plane image
     * @return a map of attributes (health, velocity, size)
     */
    public static Map<String, Integer> getPlaneAttributes(String plane) {
        return planeAttributes.getOrDefault(plane, Map.of());
    }
    public static int getInitialHealth() {
        return planeAttributes.getOrDefault(imageName, Map.of("health", 5)).getOrDefault("health", 5);
    }

    public static void setInitialHealth(int i) { initialHealth = i;
    }
}

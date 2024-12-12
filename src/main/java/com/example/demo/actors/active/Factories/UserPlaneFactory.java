package com.example.demo.actors.active.Factories;

import com.example.demo.actors.user.UserPlane;

import java.util.Map;

/**
 * The {@code UserPlaneFactory} {@link UserPlaneFactory}class is responsible for creating and managing
 * user-controlled planes in the game. This factory provides methods to
 * dynamically configure the attributes of user planes based on user selection
 * or customization.
 *
 * <p>Features include:</p>
 * <ul>
 *     <li>Initializing user planes with specific attributes such as health, velocity, and size.</li>
 *     <li>Supporting plane customization by selecting different images and configurations.</li>
 *     <li>Maintaining a centralized mechanism for user plane creation and pricing.</li>
 * </ul>
 *
 * <p><b>Usage:</b> Call {@link #initializeUserPlane()} to initialize the user plane,
 * and {@link #getUserPlane()} to retrieve the initialized instance.</p>
 */
public class UserPlaneFactory {

    /**
     * The default image name for the player's plane.
     */
    private static String imageName = "userplane1.png";

    /**
     * The default X-coordinate for the user's plane position.
     */
    private static double initialX = 5.0;

    /**
     * The default Y-coordinate for the user's plane position.
     */
    private static double initialY = 300.0;

    /**
     * The initial health value for the user's plane.
     */
    private static int initialHealth = 10;

    /**
     * A map containing the price of each plane.
     * Key: Plane image name, Value: Price in coins.
     */
    private static final Map<String, Integer> planePrices = Map.of(
            "userplane1.png", 0,  // Default plane
            "userplane2.png", 10,
            "userplane3.png", 15 );

    /**
     * A map containing the attributes of each plane.
     * Key: Plane image name, Value: A map of attributes (health, velocity, size).
     */
    private static final Map<String, Map<String, Integer>> planeAttributes = Map.of(
            "userplane1.png", Map.of("health", 5, "velocity", 10, "size", 80),
            "userplane2.png", Map.of("health", 7, "velocity", 20, "size", 120),
            "userplane3.png", Map.of("health", 9, "velocity", 25, "size", 50)
    );

    /**
     * A singleton instance of the {@link UserPlane}.
     */
    private static UserPlane userPlane;

    /**
     * Initializes the user plane based on the currently selected attributes.
     * If no plane is selected, defaults are applied.
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
     * Retrieves the initialized {@link UserPlane} instance.
     * If the plane is not yet initialized, it initializes the plane first.
     *
     * @return the {@link UserPlane} instance.
     */
    public static UserPlane getUserPlane() {
        if (userPlane == null) {
            initializeUserPlane();
        }
        return userPlane;
    }

    /**
     * Sets the image name for the player's plane, updating the plane selection.
     *
     * @param newImageName the new image name to set.
     */
    public static void setImageName(String newImageName) {
        imageName = newImageName;
    }

    /**
     * Retrieves the price of a specific plane.
     *
     * @param plane the name of the plane image.
     * @return the price of the plane in coins.
     */
    public static int getPlanePrice(String plane) {
        return planePrices.getOrDefault(plane, 0);
    }

    /**
     * Retrieves the attributes of a specific plane.
     *
     * @param plane the name of the plane image.
     * @return a map of attributes (health, velocity, size) for the specified plane.
     */
    public static Map<String, Integer> getPlaneAttributes(String plane) {
        return planeAttributes.getOrDefault(plane, Map.of());
    }

    /**
     * Retrieves the initial health value for the currently selected plane.
     *
     * @return the initial health value.
     */
    public static int getInitialHealth() {
        return planeAttributes.getOrDefault(imageName, Map.of("health", 5)).getOrDefault("health", 5);
    }

    /**
     * Sets the initial health value for the user plane.
     *
     * @param health the initial health to set.
     */
    public static void setInitialHealth(int health) {
        initialHealth = health;
    }
}

package com.example.demo.core;

/**
 * The {@code Updatable} interface provides a standard method for objects that require periodic updates
 * during the game loop. Classes implementing this interface are expected to define the behavior
 * for their update cycle.
 *
 * <p>
 * This interface is utilized within the {@link com.example.demo.core.GameLoop} to ensure that all
 * game components, such as actors, managers, and levels, adhere to a consistent update structure.
 * </p>
 */
public interface Updatable {

    /**
     * Updates the state of the implementing object.
     *
     * <p>
     * This method is called on every game loop iteration and should handle tasks such as
     * movement, collision detection, or any other logic that needs to run continuously.
     * </p>
     */
    void update();
}



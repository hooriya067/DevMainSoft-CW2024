package com.example.demo.UI.buttons;

/**
 * The {@code PauseButtonDisplay} class represents a specific type of button
 * used for pausing the game. This class extends {@link ButtonParent}
 * to inherit enhanced visual effects and functionality.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Custom image as a button graphic.</li>
 *     <li>Allows setting a position on the screen.</li>
 *     <li>Provides a functional interface to define a pause action.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link ButtonParent}: Base class providing core button functionality.</li>
 * </ul>
 */
public class PauseButtonDisplay extends ButtonParent {

    /**
     * Constructs a {@code PauseButtonDisplay} with a predefined image and size.
     */
    public PauseButtonDisplay() {
        super("/com/example/demo/images/pause_icon.png", 60, true);
    }

    /**
     * Sets the position of the pause button on the screen.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public void setPosition(double x, double y) {
        getButton().setLayoutX(x);
        getButton().setLayoutY(y);
    }

    /**
     * Functional interface for defining a pause action.
     */
    @FunctionalInterface
    public interface PauseAction {
        /**
         * Defines the action to execute when the button is clicked.
         */
        void execute();
    }

    /**
     * Sets the action to be executed when the pause button is clicked.
     *
     * @param action the action to execute
     */
    public void setOnPause(PauseAction action) {
        setOnClick(() -> action.execute());
    }
}

package com.example.demo.UI.buttons;
/**
 * The {@code PowerUpButton} class represents a specific type of button
 * used for activating power-ups in the game. This class extends {@link ButtonParent}
 * to inherit enhanced visual effects and functionality.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Custom image as a button graphic.</li>
 *     <li>Allows setting a position on the screen.</li>
 *     <li>Provides a functional interface to define a power-up action.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link ButtonParent}: Base class providing core button functionality.</li>
 * </ul>
 */
public class PowerUpButton extends ButtonParent {

    /**
     * Constructs a {@code PowerUpButton} with a predefined image and size.
     */
    public PowerUpButton() {
        super("/com/example/demo/images/powerup_icon.png", 60, true);
    }

    /**
     * Sets the position of the power-up button on the screen.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public void setPosition(double x, double y) {
        getButton().setLayoutX(x);
        getButton().setLayoutY(y);
    }

    /**
     * Sets the action to be executed when the power-up button is clicked.
     *
     * @param action the action to execute
     */
    public void setOnPowerUp(PauseButtonDisplay.PauseAction action) {
        setOnClick(() -> action.execute());
    }
}


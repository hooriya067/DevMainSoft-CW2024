/**
 * The {@code CustomizePlaneButton} class represents a specific type of button
 * used for customizing the player's plane in the game. This class extends
 * {@link ButtonParent} to inherit enhanced visual effects and functionality.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Custom image as a button graphic.</li>
 *     <li>Enhanced hover and bounce effects for better user interaction.</li>
 *     <li>Allows binding a custom action when the button is clicked.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link ButtonParent}: Base class providing core button functionality.</li>
 * </ul>
 */
package com.example.demo.UI.buttons;

/**
 * Represents a button specifically for customizing the player's plane.
 */
public class CustomizePlaneButton extends ButtonParent {

    /**
     * Constructs a {@code CustomizePlaneButton} with a predefined image and size.
     */
    public CustomizePlaneButton() {
        // Pass the image path and dimensions to the parent class
        super("/com/example/demo/images/customise.png", 300, true);
    }

    /**
     * Sets the action to be executed when the customize button is clicked.
     *
     * @param action the action to execute on button click
     */
    public void setOnCustomize(Runnable action) {
        setOnClick(action);
    }
}
package com.example.demo.UI.buttons;
/**
 * The {@code StartGameButton} class represents a specific type of button
 * used for navigating to the main menu. This class extends {@link ButtonParent}
 * to inherit enhanced visual effects and functionality.
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
public class StartGameButton extends ButtonParent {
    /**
     * Constructs a {@code StartGameButton} with a predefined image and size.
     */
    public StartGameButton() {
        super("/com/example/demo/images/start_icon.png", 300, true);
    }
    /**
     * Sets the action to be executed when the power-up button is clicked.
     *
     * @param action the action to execute
     */
    public void setOnStartGame(Runnable action) {
        setOnClick(action);
    }
}

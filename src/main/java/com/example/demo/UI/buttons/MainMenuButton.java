package com.example.demo.UI.buttons;

/**
 * The {@code MainMenuButton} class represents a specific type of button
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
public class MainMenuButton extends ButtonParent {

    /**
     * Constructs a {@code MainMenuButton} with a predefined image and size.
     */
    public MainMenuButton() {
        super("/com/example/demo/images/mainmenu_icon.png", 70, true);
    }

    /**
     * Sets the action to be executed when the main menu button is clicked.
     *
     * @param action the action to execute on button click
     */
    public void setOnMainMenu(Runnable action) {
        setOnClick(action);
    }
}

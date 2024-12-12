package com.example.demo.UI.buttons;

/**
 * The {@code ResumeButton} class represents a specific type of button
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
public class ResumeButton extends ButtonParent {
    /**
     * Constructs a {@code ResumeButton} with a predefined image and size.
     */
    public ResumeButton() {
        super("/com/example/demo/images/resume_icon.png", 70, true); // Initialize with image path and size
    }
    /**
     * Sets the action to be executed when the resume button is clicked.
     *
     * @param action the action to execute on button click
     */
    public void setOnResume(Runnable action) {
        setOnClick(action); // Pass the Runnable directly
    }

}

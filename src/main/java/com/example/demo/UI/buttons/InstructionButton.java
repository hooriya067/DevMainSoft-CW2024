package com.example.demo.UI.buttons;

/**
 * The {@code InstructionButton} class represents a specific type of button
 * used to display game instructions. This class extends {@link ButtonParent}
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
public class InstructionButton extends ButtonParent {

    /**
     * Constructs an {@code InstructionButton} with a predefined image and size.
     */
    public InstructionButton() {
        super("/com/example/demo/images/Instructions.png", 300, true);
    }

    /**
     * Sets the action to be executed when the instruction button is clicked.
     *
     * @param action the action to execute on button click
     */
    public void setOnInstructions(Runnable action) {
        setOnClick(action);
    }
}

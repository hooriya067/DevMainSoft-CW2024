package com.example.demo.UI.buttons;


/**
 * The {@code NextButton} class represents a specific type of button
 * used for navigating to the next stage or screen in the game. This class extends {@link ButtonParent}
 * to inherit enhanced visual effects and functionality.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Custom image as a button graphic.</li>
 *     <li>Enhanced hover and bounce effects for better user interaction.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link ButtonParent}: Base class providing core button functionality.</li>
 * </ul>
 */
public class NextButton extends ButtonParent {

    /**
     * The file path of the image used for the button's graphic.
     */
    private static final String IMAGE_PATH = "/com/example/demo/images/next_button.png";

    /**
     * Constructs a {@code NextButton} with a predefined image and size.
     */
    public NextButton() {
        super(IMAGE_PATH, 300, true);
    }
}


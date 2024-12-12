package com.example.demo.UI.buttons;

/**
 * The {@code PlayAgainButton} class represents a specific type of button
 * used for restarting the game or a level. This class extends {@link ButtonParent}
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
public class PlayAgainButton extends ButtonParent {

    /**
     * The file path of the image used for the button's graphic.
     */
    private static final String IMAGE_NAME = "/com/example/demo/images/playagain.png";

    /**
     * The width of the button.
     */
    private static final double BUTTON_WIDTH = 300;

    /**
     * Constructs a {@code PlayAgainButton} with a predefined image and size.
     */
    public PlayAgainButton() {
        super(IMAGE_NAME, BUTTON_WIDTH, true);
    }

    /**
     * Sets the action to be executed when the play again button is clicked.
     *
     * @param action the action to execute on button click
     */
    public void setOnPlayAgain(Runnable action) {
        setOnClick(action);
    }
}

package com.example.demo.UI.buttons;

import javafx.stage.Stage;
/**
 * The {@code QuitButton} class represents quit button
 * used for quitting the game whenever user wishes. This class extends {@link ButtonParent}
 * to inherit enhanced visual effects and functionality.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Custom image as a button graphic.</li>
 *     <li>Provides a functional interface to define a power-up action.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link ButtonParent}: Base class providing core button functionality.</li>
 * </ul>
 */
public class QuitButton extends ButtonParent {
    /**
     * Constructs a {@code QuitButton} with a predefined image and size.
     */
    public QuitButton() {
        super("/com/example/demo/images/quit_icon.png", 300, true);
    }
    /**
     * Sets the game to be closed when the quit button is clicked.

     */
    public void setOnQuit(Stage stage) {
        setOnClick(stage::close);
    }

}

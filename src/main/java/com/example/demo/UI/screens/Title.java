
package com.example.demo.UI.screens;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
/**
 * The {@link Title} class represents the title graphic for the game's main menu or other screens.
 * It encapsulates the logic for loading and displaying the title image with predefined dimensions.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Loads a title image from a specified file path.</li>
 *     <li>Adjusts the image's dimensions for consistent display.</li>
 *     <li>Provides access to the {@link ImageView} instance for integration into other UI elements.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>{@code
 * Title title = new Title();
 * ImageView titleImage = title.getTitleImage();
 * // Add titleImage to the UI layout
 * }</pre>
 */
public class Title {
    /**
     * The {@link ImageView} that holds and displays the title image.
     * This image serves as the visual representation of the title on the screen.
     */
    private final ImageView titleImage;

    /**
     * Constructs a {@link Title} object and initializes the title image.
     * The image is loaded from the specified resource path and resized to fit predefined dimensions.
     */
    public Title() {
        // Load the title image
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/title.png")).toExternalForm());
        titleImage = new ImageView(image);
        titleImage.setFitWidth(550);
        titleImage.setFitHeight(200);
        titleImage.setPreserveRatio(true);
    }

    /**
     * Returns the {@link ImageView} containing the title image.
     *
     * @return the {@code ImageView} object displaying the title image
     */
    public ImageView getTitleImage() {
        return titleImage;
    }
}


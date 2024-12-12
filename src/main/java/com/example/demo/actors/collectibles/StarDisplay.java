package com.example.demo.actors.collectibles;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * The {@code StarDisplay} class is responsible for rendering a visual display of earned stars in the game.
 * It provides a graphical representation of stars with a background and descriptive text.
 */
public class StarDisplay {

    /**
     * The file path to the star image resource.
     */
    private static final String STAR_IMAGE_NAME = "/com/example/demo/images/star.png";

    /**
     * The file path to the background image resource.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/finalstar_container.png";

    /**
     * The height of each star image in pixels.
     */
    private static final int STAR_HEIGHT = 50;

    /**
     * The container holding the star display components.
     */
    private final VBox container;

    /**
     * Constructs a {@code StarDisplay} object at a specified position with a given number of stars to display.
     *
     * @param xPosition      the X-coordinate of the display.
     * @param yPosition      the Y-coordinate of the display.
     * @param starsToDisplay the number of stars to be displayed.
     */
    public StarDisplay(double xPosition, double yPosition, int starsToDisplay) {
        container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10); // Space between stars and the descriptive text
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        StackPane imageBackground = createImageBackground();
        HBox starsContainer = new HBox();
        starsContainer.setAlignment(Pos.CENTER);
        imageBackground.getChildren().add(starsContainer);

        initializeStars(starsToDisplay, starsContainer);

        Text starText = createStarText(starsToDisplay);
        container.getChildren().addAll(imageBackground, starText);
        container.setTranslateY(-30); // Adjust position upwards
    }

    /**
     * Creates a background image container for the star display.
     *
     * @return a {@code StackPane} containing the background image.
     */
    private StackPane createImageBackground() {
        StackPane imageBackground = new StackPane();
        imageBackground.setPrefSize(300, 100);

        try {
            ImageView backgroundImage = new ImageView(
                    new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_IMAGE_NAME)).toExternalForm())
            );
            backgroundImage.setFitWidth(300);
            backgroundImage.setFitHeight(100);
            backgroundImage.setPreserveRatio(false);
            imageBackground.getChildren().add(backgroundImage);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
        }

        return imageBackground;
    }

    /**
     * Initializes and adds the specified number of stars to the display.
     *
     * @param starsToDisplay the number of stars to display.
     * @param starsContainer the container to which the stars will be added.
     */
    private void initializeStars(int starsToDisplay, HBox starsContainer) {
        for (int i = 0; i < starsToDisplay; i++) {
            try {
                ImageView star = new ImageView(new Image(
                        Objects.requireNonNull(getClass().getResource(STAR_IMAGE_NAME)).toExternalForm()));
                star.setFitHeight(STAR_HEIGHT);
                star.setPreserveRatio(true);
                starsContainer.getChildren().add(star);
                starsContainer.setTranslateY(-10);
            } catch (Exception e) {
                System.err.println("Error adding star: " + e.getMessage());
                e.printStackTrace();
            }
        }

        if (starsToDisplay == 0) {
            Text emptyMessage = new Text("No Stars Earned:/");
            emptyMessage.setFont(Font.font("Roboto", FontWeight.BOLD, 16));
            emptyMessage.setFill(Color.DARKGRAY);
            starsContainer.getChildren().add(emptyMessage);
            emptyMessage.setTranslateY(-10);
        }
    }

    /**
     * Creates a descriptive text indicating the number of stars earned.
     *
     * @param starsToDisplay the number of stars earned.
     * @return a {@code Text} object containing the descriptive text.
     */
    private Text createStarText(int starsToDisplay) {
        Text starText = new Text(starsToDisplay + (starsToDisplay == 1 ? " STAR" : " STARS"));
        starText.setFont(Font.font("Roboto", FontWeight.BOLD, 20));
        starText.setFill(Color.YELLOW);
        return starText;
    }

    /**
     * Retrieves the container holding the star display components.
     *
     * @return a {@code VBox} containing the display elements.
     */
    public VBox getContainer() {
        return container;
    }
}

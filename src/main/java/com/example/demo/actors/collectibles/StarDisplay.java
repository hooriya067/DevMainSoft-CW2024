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

public class StarDisplay {

    private static final String STAR_IMAGE_NAME = "/com/example/demo/images/star.png";
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/finalstar_container.png"; // Your custom container image
    private static final int STAR_HEIGHT = 50;
    private final VBox container;

    public StarDisplay(double xPosition, double yPosition, int starsToDisplay) {
        container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10); // Space between stars and the text
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        // Create an image-based background container
        StackPane imageBackground = createImageBackground();
        HBox starsContainer = new HBox();
        starsContainer.setAlignment(Pos.CENTER);
        imageBackground.getChildren().add(starsContainer);

        initializeStars(starsToDisplay, starsContainer);

        Text starText = createStarText(starsToDisplay);

        container.getChildren().addAll(imageBackground, starText);
       container.setTranslateY(-30); // Negative value moves it up
    }

    private StackPane createImageBackground() {
        StackPane imageBackground = new StackPane();
        imageBackground.setPrefSize(300, 100); // Adjust size to fit the background image

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

    private void initializeStars(int starsToDisplay, HBox starsContainer) {

        for (int i = 0; i < starsToDisplay; i++) {
            try {
                ImageView star = new ImageView(new Image(
                        Objects.requireNonNull(getClass().getResource(STAR_IMAGE_NAME)).toExternalForm()));
                star.setFitHeight(STAR_HEIGHT);
                star.setPreserveRatio(true);
                starsContainer.getChildren().add(star);
                starsContainer.setTranslateY(-10); // Negative value moves it u
            } catch (Exception e) {
                System.err.println("Error adding star: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // If there are zero stars, leave the background empty
        if (starsToDisplay == 0) {
            Text emptyMessage = new Text("No Stars Earned:/");
            emptyMessage.setFont(Font.font("Roboto", FontWeight.BOLD, 16));
            emptyMessage.setFill(Color.DARKGRAY);
            starsContainer.getChildren().add(emptyMessage);
            emptyMessage.setTranslateY(-10); // Negative value moves it u
        }
    }

    private Text createStarText(int starsToDisplay) {
        Text starText = new Text(starsToDisplay + (starsToDisplay == 1 ? " STAR" : " STARS"));
        starText.setFont(Font.font("Roboto", FontWeight.BOLD, 20));
        starText.setFill(Color.YELLOW);
        return starText;
    }

    public VBox getContainer() {
        return container;
    }
}

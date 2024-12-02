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
    private static final int STAR_HEIGHT = 50;
    private final VBox container; // Main container for the 3D box and text

    public StarDisplay(double xPosition, double yPosition, int starsToDisplay) {
        container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10); // Space between stars and the text
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        // Add a 3D white box
        StackPane whiteBox = createWhiteBox();
        HBox starsContainer = new HBox();
        starsContainer.setAlignment(Pos.CENTER);
        whiteBox.getChildren().add(starsContainer);

        initializeStars(starsToDisplay, starsContainer);

        Text starText = createStarText(starsToDisplay);

        container.getChildren().addAll(whiteBox, starText);
    }

    private StackPane createWhiteBox() {
        StackPane whiteBox = new StackPane();
        whiteBox.setPrefSize(300, 50); // Size of the white box
        whiteBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-width: 3.5; -fx-background-radius: 10; -fx-border-radius: 10;");
        return whiteBox;
    }

    private void initializeStars(int starsToDisplay, HBox starsContainer) {

        for (int i = 0; i < starsToDisplay; i++) {
            try {
                ImageView star = new ImageView(new Image(
                        Objects.requireNonNull(getClass().getResource(STAR_IMAGE_NAME)).toExternalForm()));
                star.setFitHeight(STAR_HEIGHT);
                star.setPreserveRatio(true);
                starsContainer.getChildren().add(star);
            } catch (Exception e) {
                System.err.println("Error adding star: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // If there are zero stars, leave the white box empty
        if (starsToDisplay == 0) {
            Text emptyMessage = new Text("No Stars Earned:/");
            emptyMessage.setFont(Font.font("Roboto", FontWeight.BOLD, 16));
            emptyMessage.setFill(Color.GRAY);
            starsContainer.getChildren().add(emptyMessage);
        }
    }

    private Text createStarText(int starsToDisplay) {
        Text starText = new Text(starsToDisplay + (starsToDisplay == 1 ? " STAR" : " STARS"));
        starText.setFont(Font.font("Roboto", FontWeight.BOLD, 20));
        starText.setFill(Color.BLACK);
        return starText;
    }

    public VBox getContainer() {
        return container;
    }
}

package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class PauseButtonDisplay {

    private static final String PAUSE_BUTTON_IMAGE_NAME = "/com/example/demo/images/pause_icon.png";
    private static final int PAUSE_BUTTON_SIZE = 50;

    private HBox container;
    private ImageView pauseButton;

    public PauseButtonDisplay(double xPosition, double yPosition) {
        initializeContainer(xPosition, yPosition);
    }

    private void initializeContainer(double xPosition, double yPosition) {
        container = new HBox();
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        try {
            pauseButton = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(PAUSE_BUTTON_IMAGE_NAME)).toExternalForm()));
            pauseButton.setFitHeight(PAUSE_BUTTON_SIZE);
            pauseButton.setFitWidth(PAUSE_BUTTON_SIZE);
            pauseButton.setVisible(true);
            System.out.println("Pause button image loaded successfully.");
            container.getChildren().add(pauseButton);  // Add pause button to container
        } catch (Exception e) {
            System.out.println("Error loading pause button image: " + e.getMessage());
        }
    }

    public HBox getContainer() {
        return container;
    }
}


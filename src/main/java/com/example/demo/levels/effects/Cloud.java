package com.example.demo.levels.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Cloud {

    private static final String CLOUD_IMAGE_NAME = "/com/example/demo/images/cloud.png";
    private static final double DEFAULT_CLOUD_WIDTH = 300; // Adjusted default size
    private final Pane container;
    private boolean movingLeft; // Tracks direction of movement (specific to each cloud)
    private final double buffer; // Buffer for movement boundaries

    private final double cloudWidth;

    public Cloud(double initialX, double initialY, boolean movingLeft, double buffer, double cloudWidth) {
        this.container = new Pane();
        this.movingLeft = movingLeft;
        this.buffer = buffer;
        this.cloudWidth = cloudWidth; // Set custom width
        initializeCloud(initialX, initialY);
    }

    private void initializeCloud(double xPosition, double yPosition) {
        ImageView cloudImage = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource(CLOUD_IMAGE_NAME)).toExternalForm()
        ));

        cloudImage.setFitWidth(cloudWidth); // Use dynamic width
        cloudImage.setPreserveRatio(true);

        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);
        container.getChildren().add(cloudImage);
    }

    public Pane getContainer() {
        return container;
    }

    public void move(double deltaX, double screenWidth) {
        double leftLimit = buffer; // Set left boundary with buffer
        double rightLimit = screenWidth - DEFAULT_CLOUD_WIDTH - buffer; // Set right boundary with buffer

        if (movingLeft) {
            container.setLayoutX(container.getLayoutX() - deltaX);
            if (container.getLayoutX() <= leftLimit) {
                movingLeft = false; // Reverse direction at the left boundary
            }
        } else {
            container.setLayoutX(container.getLayoutX() + deltaX);
            if (container.getLayoutX() >= rightLimit) {
                movingLeft = true; // Reverse direction at the right boundary
            }
        }
    }
}

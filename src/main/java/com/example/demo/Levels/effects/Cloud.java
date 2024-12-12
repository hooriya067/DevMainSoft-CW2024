package com.example.demo.Levels.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

/**
 * The {@code Cloud} class represents a graphical cloud element that moves dynamically across the screen.
 * It provides functionality to initialize, render, and animate the cloud with customizable movement boundaries.
 *
 * <p>
 * This class is primarily used for creating visual effects in game levels, enhancing the background with
 * smooth-moving clouds.
 * </p>
 */
public class Cloud {

    /**
     * Path to the cloud image resource.
     */
    private static final String CLOUD_IMAGE_NAME = "/com/example/demo/images/cloud.png";

    /**
     * Default width of the cloud image.
     */
    private static final double DEFAULT_CLOUD_WIDTH = 300;

    /**
     * The container for holding the cloud image.
     */
    private final Pane container;

    /**
     * Tracks whether the cloud is moving left.
     */
    private boolean movingLeft;

    /**
     * The buffer zone for cloud movement boundaries.
     */
    private final double buffer;

    /**
     * The width of the cloud.
     */
    private final double cloudWidth;

    /**
     * Constructs a {@code Cloud} object with specified position, direction, buffer, and width.
     *
     * @param initialX    the initial X-coordinate of the cloud.
     * @param initialY    the initial Y-coordinate of the cloud.
     * @param movingLeft  the initial movement direction of the cloud.
     * @param buffer      the buffer zone for movement boundaries.
     * @param cloudWidth  the custom width of the cloud image.
     */
    public Cloud(double initialX, double initialY, boolean movingLeft, double buffer, double cloudWidth) {
        this.container = new Pane();
        this.movingLeft = movingLeft;
        this.buffer = buffer;
        this.cloudWidth = cloudWidth; // Set custom width
        initializeCloud(initialX, initialY);
    }

    /**
     * Initializes the cloud image and sets its position.
     *
     * @param xPosition the initial X-coordinate of the cloud.
     * @param yPosition the initial Y-coordinate of the cloud.
     */
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

    /**
     * Retrieves the {@link Pane} container holding the cloud.
     *
     * @return the cloud container.
     */
    public Pane getContainer() {
        return container;
    }

    /**
     * Moves the cloud horizontally, reversing direction at boundaries.
     *
     * @param deltaX       the amount to move the cloud.
     * @param screenWidth  the width of the screen, used to calculate boundaries.
     */
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

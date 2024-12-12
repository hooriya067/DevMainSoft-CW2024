/**
 * The {@code CustomizationScreen} class represents a screen in the game where players can select their desired plane
 * from a set of available options. It provides a user-friendly interface with a visually appealing design, including
 * a blurred background, selectable plane images, and a confirmation button.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Displays a selection screen with a blurred background and a styled selection box.</li>
 *     <li>Allows players to choose a plane by clicking on an image, which visually highlights the selected plane.</li>
 *     <li>Includes a confirmation button that triggers the customization completion process.</li>
 *     <li>Integrates with {@link UserPlaneFactory} to set the selected plane image.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link UserPlaneFactory}: Manages the user's selected plane image.</li>
 * </ul>
 */
package com.example.demo.UI.screens;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.demo.actors.active.Factories.UserPlaneFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomizationScreen extends Group {

    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private String selectedPlaneImage;

    /**
     * Constructs a {@code CustomizationScreen} and initializes the UI components.
     *
     * @param stage                 the {@link Stage} on which the screen is displayed
     * @param onCustomizationComplete a {@link Runnable} action to be executed when the customization is complete
     */
    public CustomizationScreen(Stage stage, Runnable onCustomizationComplete) {
        // Add blurred background
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + "background0.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setEffect(new BoxBlur(10, 10, 3)); // Apply Gaussian blur

        // Grey box for selection area
        VBox greyBox = new VBox(20);
        greyBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-padding: 20; -fx-border-radius: 10;");
        greyBox.setMaxWidth(600);
        greyBox.setLayoutX((stage.getWidth() - greyBox.getMaxWidth()) / 2 + 30);
        greyBox.setLayoutY(stage.getHeight() * 0.30);
        greyBox.setAlignment(Pos.CENTER);

        Label title = new Label("Select Your Plane");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Available planes
        List<String> planeImages = List.of(
                "userplane1.png",
                "userplane2.png",
                "userplane3.png"
        );

        HBox planeSelectionBox = new HBox(20);
        planeSelectionBox.setAlignment(Pos.CENTER);
        List<ImageView> planeViews = new ArrayList<>();

        // Create clickable images
        for (String imageName : planeImages) {
            try {
                String fullPath = Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm();

                VBox planeContainer = new VBox(5);
                planeContainer.setAlignment(Pos.CENTER);

                ImageView planeView = new ImageView(new Image(fullPath));
                planeView.setFitWidth(150); // Increased size
                planeView.setPreserveRatio(true);
                planeView.setOnMouseClicked(e -> {
                    selectedPlaneImage = imageName;
                    highlightSelectedPlane(planeViews, planeView);
                });
                planeViews.add(planeView);

                planeContainer.getChildren().add(planeView);
                planeSelectionBox.getChildren().add(planeContainer);

            } catch (NullPointerException e) {
                System.err.println("Image resource not found: " + imageName);
            }
        }

        // Confirm Button as an Image
        Image confirmImage = new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + "confirm_button.png")).toExternalForm());
        ImageView confirmImageView = getImageView(stage, onCustomizationComplete, confirmImage);

        greyBox.getChildren().addAll(title, planeSelectionBox);
        this.getChildren().addAll(backgroundImageView, greyBox, confirmImageView);
    }

    /**
     * Creates the confirmation button with the specified image and behavior.
     *
     * @param stage                 the {@link Stage} on which the button is displayed
     * @param onCustomizationComplete the {@link Runnable} to execute on confirmation
     * @param confirmImage          the {@link Image} for the confirmation button
     * @return the {@link ImageView} representing the confirmation button
     */
    private ImageView getImageView(Stage stage, Runnable onCustomizationComplete, Image confirmImage) {
        ImageView confirmImageView = new ImageView(confirmImage);
        confirmImageView.setFitWidth(300);
        confirmImageView.setPreserveRatio(true);
        confirmImageView.setLayoutX((stage.getWidth() - 280) / 2);
        confirmImageView.setLayoutY(stage.getHeight() * 0.7);
        confirmImageView.setOnMouseClicked(e -> {
            if (selectedPlaneImage != null) {
                System.out.println("Selected Plane: " + selectedPlaneImage);
                updateUserPlaneImage(selectedPlaneImage);
                onCustomizationComplete.run(); // Return to main game or next screen
            }
        });
        return confirmImageView;
    }

    /**
     * Highlights the selected plane by applying a visual effect to the corresponding {@link ImageView}.
     *
     * @param planeViews   the list of all plane {@link ImageView}s
     * @param selectedPlane the {@link ImageView} of the selected plane
     */
    private void highlightSelectedPlane(List<ImageView> planeViews, ImageView selectedPlane) {
        for (ImageView planeView : planeViews) {
            planeView.setStyle("");
        }
        selectedPlane.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.5, 0, 0);");
    }

    /**
     * Updates the user's plane image in {@link UserPlaneFactory} with the selected plane.
     *
     * @param imageName the name of the selected plane image
     */
    private void updateUserPlaneImage(String imageName) {
        UserPlaneFactory.setImageName(imageName);
    }
}

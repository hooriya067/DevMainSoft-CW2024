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
        greyBox.setLayoutX((stage.getWidth() - greyBox.getMaxWidth()) / 2+30);
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

                // Add only the image to the container
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

    private void highlightSelectedPlane(List<ImageView> planeViews, ImageView selectedPlane) {
        for (ImageView planeView : planeViews) {
            planeView.setStyle("");
        }
        selectedPlane.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.5, 0, 0);");
    }

    private void updateUserPlaneImage(String imageName) {
        UserPlaneFactory.setImageName(imageName);
    }
}

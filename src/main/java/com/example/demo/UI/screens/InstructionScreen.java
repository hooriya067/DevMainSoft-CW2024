package com.example.demo.UI.screens;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class InstructionScreen extends Group {

    private int currentSlideIndex = 0;
    private final List<String> slideImages = List.of(
            "/com/example/demo/images/instruction_manual/1.png",
            "/com/example/demo/images/instruction_manual/2.png",
            "/com/example/demo/images/instruction_manual/3.png",
            "/com/example/demo/images/instruction_manual/4.png",
            "/com/example/demo/images/instruction_manual/5.png",
            "/com/example/demo/images/instruction_manual/6.png",
            "/com/example/demo/images/instruction_manual/7.png",
            "/com/example/demo/images/instruction_manual/8.png"
    );

    private ImageView slideView;

    public InstructionScreen(Stage stage, Runnable onBackToMenu) {
        // Blurred background
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/background0.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setEffect(new BoxBlur(10, 10, 3)); // Apply Gaussian blur

        // Slide container
        slideView = new ImageView();
        slideView.setFitWidth(800); //slide size
        slideView.setPreserveRatio(true);
        updateSlide();

        StackPane slideContainer = new StackPane(slideView);
        slideContainer.setAlignment(Pos.CENTER);
        slideContainer.setLayoutX((stage.getWidth() - 800) / 2);
        slideContainer.setLayoutY(stage.getHeight() * 0.2);

        // Navigation buttons with images
        ImageView leftArrow = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/left_arrow.png")).toExternalForm()));
        leftArrow.setFitWidth(70);
        leftArrow.setPreserveRatio(true);
        leftArrow.setOnMouseClicked(e -> showPreviousSlide());
        leftArrow.setLayoutX(50); // Adjust position
        leftArrow.setLayoutY(stage.getHeight() / 2 - 25);

        ImageView rightArrow = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/right_arrow.png")).toExternalForm()));
        rightArrow.setFitWidth(70);
        rightArrow.setPreserveRatio(true);
        rightArrow.setOnMouseClicked(e -> showNextSlide());
        rightArrow.setLayoutX(stage.getWidth() - 100); // Adjust position
        rightArrow.setLayoutY(stage.getHeight() / 2 - 25);

        // Close button
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-font-size: 16px; -fx-background-color: red;-fx-font-weight: bold; -fx-text-fill: white;");
        closeButton.setOnAction(e -> onBackToMenu.run());
        closeButton.setLayoutX(stage.getWidth() - 50); // Top-right corner
        closeButton.setLayoutY(20);

        // Add everything to the group
        this.getChildren().addAll(backgroundImageView, slideContainer, leftArrow, rightArrow, closeButton);
    }

    private void showPreviousSlide() {
        if (currentSlideIndex > 0) {
            currentSlideIndex--;
            updateSlide();
        }
    }

    private void showNextSlide() {
        if (currentSlideIndex < slideImages.size() - 1) {
            currentSlideIndex++;
            updateSlide();
        }
    }

    private void updateSlide() {
        String slidePath = slideImages.get(currentSlideIndex);
        slideView.setImage(new Image(Objects.requireNonNull(getClass().getResource(slidePath)).toExternalForm()));
    }
}

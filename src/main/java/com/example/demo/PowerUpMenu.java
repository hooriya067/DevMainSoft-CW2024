package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PowerUpMenu {
    private final Stage stage;
    private StackPane overlayLayout;
    private boolean overlayActive = false;

    public PowerUpMenu(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null in PowerUpMenu.");
        }
        this.stage = stage;
    }

    public void displayOverlay() {
        if (overlayActive) {
            System.err.println("PowerUp menu is already active! overlayActive = true");
            return;
        }

        overlayActive = true;
        GameStateManager.getInstance().pauseGame();

        // Create overlay background
        Pane overlayBackground = new Pane();
        overlayBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlayBackground.setPrefSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        // Create VBox for the menu
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-padding: 20;");
        menuBox.setMaxWidth(500);
        menuBox.setMaxHeight(400);

        // Add title
        Text title = new Text("POWERUP");
        title.setFont(Font.font("Roboto", 36));
        title.setFill(Color.GOLD);
        menuBox.getChildren().add(title);

        // Create HBox for the power-up buttons
        HBox powerUpBox = new HBox(30); // Spacing between the power-ups
        powerUpBox.setAlignment(Pos.CENTER);

        // Health Power-Up Button
        ImageView healthIcon = new ImageView(new Image(getClass().getResource("/com/example/demo/images/heart.png").toExternalForm()));
        healthIcon.setFitWidth(60);
        healthIcon.setFitHeight(60);
        healthIcon.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0.5, 0, 0);");
        Button healthButton = new Button("", healthIcon);
        healthButton.setStyle("-fx-background-color: transparent;");

        Text healthLabel = new Text("EXTRA LIFE");
        healthLabel.setFont(Font.font("Roboto", 18));
        healthLabel.setFill(Color.LIGHTBLUE);

        Text healthPrice = new Text("10 Coins");
        healthPrice.setFont(Font.font("Roboto", 14));
        healthPrice.setFill(Color.WHITE);

        VBox healthBox = new VBox(5); // Vertical box for health power-up
        healthBox.setAlignment(Pos.CENTER);
        healthBox.getChildren().addAll(healthButton, healthLabel, healthPrice);

        healthButton.setOnAction(e -> {
            if (PowerUpManager.getInstance().purchaseExtraLife()) {
                System.out.println("Extra life purchased!");
            } else {
                showAlert("Not enough coins for Extra Life!");
            }
            removeOverlay();
            GameStateManager.getInstance().resumeGame();
        });


        // Shield Power-Up Button
        ImageView shieldIcon = new ImageView(new Image(getClass().getResource("/com/example/demo/images/shield2.png").toExternalForm()));
        shieldIcon.setFitWidth(60);
        shieldIcon.setFitHeight(60);
        shieldIcon.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0.5, 0, 0);");
        Button shieldButton = new Button("", shieldIcon);
        shieldButton.setStyle("-fx-background-color: transparent;");

        Text shieldLabel = new Text("SHIELD");
        shieldLabel.setFont(Font.font("Roboto", 18));
        shieldLabel.setFill(Color.LIGHTBLUE);

        Text shieldPrice = new Text("20 Coins");
        shieldPrice.setFont(Font.font("Roboto", 14));
        shieldPrice.setFill(Color.WHITE);

        VBox shieldBox = new VBox(5); // Vertical box for shield power-up
        shieldBox.setAlignment(Pos.CENTER);
        shieldBox.getChildren().addAll(shieldButton, shieldLabel, shieldPrice);

        shieldButton.setOnAction(e -> {
            if (PowerUpManager.getInstance().purchaseShield()) {
                System.out.println("Shield purchased!");
            } else {
                showAlert("Not enough coins for Shield OR its already active!!");
            }
            removeOverlay();
            GameStateManager.getInstance().resumeGame();
        });
        // Add both power-ups to the horizontal box
        powerUpBox.getChildren().addAll(healthBox, shieldBox);


        // Resume Button
        Button resumeButton = new Button("BACK");
        resumeButton.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: darkblue;");
        resumeButton.setOnAction(e -> {
            System.out.println("Resume button clicked!");
            removeOverlay();
            GameStateManager.getInstance().resumeGame();
        });

        // Add elements to menuBox
        menuBox.getChildren().addAll(powerUpBox, resumeButton);

        // Add overlay to layout
        overlayLayout = new StackPane();
        overlayLayout.getChildren().addAll(overlayBackground, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER);

        if (stage.getScene().getRoot() instanceof Group) {
            Group currentRoot = (Group) stage.getScene().getRoot();
            if (!currentRoot.getChildren().contains(overlayLayout)) {
                currentRoot.getChildren().add(overlayLayout);
            }
        }
    }

    public void removeOverlay() {
        if (overlayLayout == null) {
            System.err.println("Cannot remove overlay: overlayLayout is null!");
            return;
        }

        if (overlayLayout.getParent() instanceof Group) {
            Group currentRoot = (Group) overlayLayout.getParent();
            currentRoot.getChildren().remove(overlayLayout);
            overlayActive = false;
        } else {
            System.err.println("Cannot remove overlay: Unexpected parent type: " + overlayLayout.getParent());
        }
    }
    private void showAlert(String message) {
        Text alertText = new Text(message);
        alertText.setFont(Font.font("Roboto", 22));
        alertText.setFill(Color.RED);
        alertText.setStyle("-fx-font-weight: bold;");

        alertText.setLayoutX(GameConfig.SCREEN_WIDTH / 2 - 100);
        alertText.setLayoutY(GameConfig.SCREEN_HEIGHT / 2 - 100);

        if (stage.getScene().getRoot() instanceof Group) {
            Group root = (Group) stage.getScene().getRoot();
            root.getChildren().add(alertText);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), alertText);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> root.getChildren().remove(alertText));
            fadeOut.play();
        }
    }
}

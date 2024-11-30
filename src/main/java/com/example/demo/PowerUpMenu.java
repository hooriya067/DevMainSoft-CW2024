package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PowerUpMenu extends OnScreenMenu {

    public PowerUpMenu(Stage stage) {
        super(stage);
    }

    public void displayOverlay() {
        VBox menuContent = createMenuContent();
        super.displayOverlay(menuContent);
    }

    @Override
    protected VBox createMenuContent() {
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-padding: 20;");
        menuBox.setMaxWidth(500);
        menuBox.setMaxHeight(400);

        Text title = new Text("POWERUP");
        title.setStyle("-fx-font-size: 36px; -fx-fill: gold;");

        HBox powerUpBox = new HBox(30);
        powerUpBox.setAlignment(Pos.CENTER);

        // Health Power-Up
        VBox healthBox = createPowerUpButtonWithLabels("heart.png", "EXTRA LIFE", "10 Coins", () -> {
            if (PowerUpManager.getInstance().purchaseExtraLife()) {
              removeOverlay();
              GameStateManager.getInstance().resumeGame();
            } else {
                showAlert("Not enough coins for Extra Life!");
            }
        });

        // Shield Power-Up
        VBox shieldBox = createPowerUpButtonWithLabels("shield2.png", "SHIELD", "20 Coins", () -> {
            if (PowerUpManager.getInstance().purchaseShield()) {
                removeOverlay();
                GameStateManager.getInstance().resumeGame();
            } else {
                showAlert("Not enough coins for Shield \n " +
                        "   OR it's already active!");
            }
        });

        // Adding to powerUpBox
        powerUpBox.getChildren().addAll(healthBox, shieldBox);

        Button resumeButton = new Button("BACK");
        resumeButton.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: darkblue;");
        resumeButton.setOnAction(e -> {
            removeOverlay();
            startResumeCountdown();
        });


        menuBox.getChildren().addAll(title, powerUpBox, resumeButton);
        return menuBox;
    }

    private VBox createPowerUpButtonWithLabels(String imagePath, String label, String price, Runnable action) {
        // Create icon for the button
        ImageView icon = new ImageView(new Image(getClass().getResource("/com/example/demo/images/" + imagePath).toExternalForm()));
        icon.setFitWidth(60);
        icon.setFitHeight(60);

        // Create button with icon
        Button button = new Button("", icon);
        button.setStyle("-fx-background-color: transparent;");
        button.setOnAction(e -> action.run());

        // Create labels for the button
        Text nameLabel = new Text(label);
        nameLabel.setStyle("-fx-font-size: 18px; -fx-fill: lightblue;");

        Text priceLabel = new Text(price);
        priceLabel.setStyle("-fx-font-size: 14px; -fx-fill: white;");

        // Wrap button and labels in a VBox
        VBox powerUpBox = new VBox(5);
        powerUpBox.setAlignment(Pos.CENTER);
        powerUpBox.getChildren().addAll(button, nameLabel, priceLabel);

        return powerUpBox;
    }
}

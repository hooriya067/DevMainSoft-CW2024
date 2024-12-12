package com.example.demo.UI.screens;

import com.example.demo.Managers.AlertManager;
import com.example.demo.actors.active.Factories.UserPlaneFactory;
import com.example.demo.Managers.CoinSystemManager;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomizationScreen extends Group {

    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private String selectedPlaneImage;
    private final Label coinLabel = new Label();

    public CustomizationScreen(Stage stage, Runnable onCustomizationComplete) {
        // Blurred background
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + "background0.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setEffect(new BoxBlur(10, 10, 3));

        VBox greyBox = new VBox(20);
        greyBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-padding: 20; -fx-border-radius: 10;");
        greyBox.setMaxWidth(600);
        greyBox.setLayoutX((stage.getWidth() - greyBox.getMaxWidth()) / 2);
        greyBox.setLayoutY(stage.getHeight() * 0.3);
        greyBox.setAlignment(Pos.CENTER);

        Label title = new Label("Select Your Plane");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: yellow;");

        coinLabel.setText("Coins: " + CoinSystemManager.getInstance().getCoins());
        coinLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        List<String> planeImages = List.of("userplane1.png", "userplane2.png", "userplane3.png");

        HBox planeSelectionBox = new HBox(20);
        planeSelectionBox.setAlignment(Pos.CENTER);

        List<ImageView> planeViews = new ArrayList<>();
        for (String plane : planeImages) {
            VBox planeContainer = new VBox(10);
            planeContainer.setAlignment(Pos.CENTER);

            ImageView planeView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + plane)).toExternalForm()));
            planeView.setFitWidth(150);
            planeView.setPreserveRatio(true);

            int price = UserPlaneFactory.getPlanePrice(plane);

            Label priceLabel = new Label("Price: " + price + " coins");
            priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: yellow;");

            planeView.setOnMouseClicked(e -> {
                selectedPlaneImage = plane;
                highlightSelectedPlane(planeViews, planeView);
            });

            planeViews.add(planeView);
            planeContainer.getChildren().addAll(planeView, priceLabel);
            planeSelectionBox.getChildren().add(planeContainer);
        }
        Image confirmImage = new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + "confirm_button.png")).toExternalForm());
        ImageView confirmImageView = new ImageView(confirmImage);
        confirmImageView.setFitWidth(150); // Adjust size
        confirmImageView.setPreserveRatio(true);
        confirmImageView.setLayoutX(stage.getWidth() / 2 - 75); // Center horizontally
        confirmImageView.setLayoutY(stage.getHeight() * 0.75); // Position below the grey box
        confirmImageView.setOnMouseClicked(e -> {
            if (selectedPlaneImage != null) {
                int price = UserPlaneFactory.getPlanePrice(selectedPlaneImage);
                if (CoinSystemManager.getInstance().getCoins() >= price) {
                    CoinSystemManager.getInstance().subtractCoins(price);
                    UserPlaneFactory.setImageName(selectedPlaneImage);
                    onCustomizationComplete.run();
                } else {
                    AlertManager.getInstance().showAlert("Not Enough Coins!!");
                }
            }
        });


        greyBox.getChildren().addAll(title, coinLabel, planeSelectionBox);
        this.getChildren().addAll(backgroundImageView, greyBox, confirmImageView);
    }

    private void highlightSelectedPlane(List<ImageView> planeViews, ImageView selectedPlane) {
        for (ImageView planeView : planeViews) {
            planeView.setStyle("");
        }
        selectedPlane.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.5, 0, 0);");
    }
}

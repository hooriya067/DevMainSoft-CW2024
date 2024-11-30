package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class OnScreenMenu {
    Stage stage = StageManager.getStage();
    private StackPane overlayLayout;
    private boolean overlayActive = false;

    public OnScreenMenu(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null in OnScreenMenu.");
        }
    }

    protected void displayOverlay(VBox menuContent) {
        if (overlayActive) {
            System.err.println(getClass().getSimpleName() + " menu is already active! overlayActive = true");
            return;
        }

        overlayActive = true;
        GameStateManager.getInstance().pauseGame();

        Pane overlayBackground = new Pane();
        overlayBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlayBackground.setPrefSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        overlayLayout = new StackPane();
        overlayLayout.getChildren().addAll(overlayBackground, menuContent);
        StackPane.setAlignment(menuContent, Pos.CENTER);

        if (stage.getScene().getRoot() instanceof Group) {
            Group currentRoot = (Group) stage.getScene().getRoot();
            if (!currentRoot.getChildren().contains(overlayLayout)) {
                currentRoot.getChildren().add(overlayLayout);
            }
        }
    }

    protected void removeOverlay() {
        if (overlayLayout == null) return;

        if (overlayLayout.getParent() instanceof Group) {
            Group currentRoot = (Group) overlayLayout.getParent();
            currentRoot.getChildren().remove(overlayLayout);
            overlayActive = false;
        }
    }

    protected void startResumeCountdown() {
        removeOverlay();
        Label countdownLabel = new Label("3");
        countdownLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white; -fx-font-weight: bold;");

        if (stage.getScene().getRoot() instanceof Group) {
            Group root = (Group) stage.getScene().getRoot();
            root.getChildren().add(countdownLabel);

            countdownLabel.setLayoutX(GameConfig.SCREEN_WIDTH / 2 - 20);
            countdownLabel.setLayoutY(GameConfig.SCREEN_HEIGHT / 2 - 50);

            Timeline countdownTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        int currentValue = Integer.parseInt(countdownLabel.getText());
                        if (currentValue > 1) {
                            countdownLabel.setText(String.valueOf(currentValue - 1));
                        } else {
                            GameStateManager.getInstance().resumeGame();
                            root.getChildren().remove(countdownLabel);
                        }
                    })
            );
            countdownTimeline.setCycleCount(3);
            countdownTimeline.play();
        } else {
            System.err.println("Unexpected root type for the scene.");
        }
    }
    protected void showAlert(String message) {
        Text alertText = new Text(message);
        alertText.setFont(Font.font("Roboto", 22));
        alertText.setStyle("-fx-fill: red; -fx-font-weight: bold;");

        alertText.setLayoutX(GameConfig.SCREEN_WIDTH / 2 - 150);
        alertText.setLayoutY(GameConfig.SCREEN_HEIGHT / 2 - 200);

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

    protected abstract VBox createMenuContent();
}

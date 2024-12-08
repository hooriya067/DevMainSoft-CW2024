package com.example.demo.levels.view;

import com.example.demo.core.GameConfig;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.effects.Cloud;
import com.example.demo.levels.effects.RainEffect;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class LevelVeiwLevelFive extends LevelView {

    private Label killsLabel;
    private final Cloud cloud1;
    private final Cloud cloud2;
  //  private final RainEffect rainEffect;
    public LevelVeiwLevelFive(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent levelParent) {
        super(root, heartsToDisplay, screenWidth, screenHeight, levelParent);

        double buffer = 200; // Set buffer for movement boundaries
        cloud1 = new Cloud(screenWidth - 800, 0, false, buffer, 400);
        cloud2 = new Cloud(screenWidth - 500, 0, true, buffer, 400);
        //rainEffect = new RainEffect(screenWidth, screenHeight); // Rain effect
        root.getChildren().addAll(cloud1.getContainer(), cloud2.getContainer());
        //root.getChildren().addAll(cloud1.getContainer(), cloud2.getContainer(), rainEffect.getContainer());
    }

    public void updateClouds() {
        double deltaX = 2;
        cloud1.move(deltaX, GameConfig.SCREEN_WIDTH);
        cloud2.move(deltaX, GameConfig.SCREEN_WIDTH);
    }

    @Override
    protected void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX(getLevelParent().getScreenWidth() / 2 - 100);
        killsLabel.setLayoutY(20);

        String labelStyle = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(#ff0000, #ff5500); -fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);";
        killsLabel.setStyle(labelStyle);

        getRoot().getChildren().addAll(killsLabel);
        killsLabel.toFront();
    }

    @Override
    public void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getLevelParent().getNumberOfKills());
            killsLabel.toFront();
        });
    }

    @Override
    public void AddUI() {
        super.AddUI();
        showClouds(); // Add clouds to the UI
    }
    protected void showClouds() {
        cloud1.getContainer().toFront();
        cloud2.getContainer().toFront();
    }
}

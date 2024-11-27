package com.example.demo;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class LevelFive extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background5.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 8;
    private static final int KILLS_TO_ADVANCE = 15;

    private Label killsLabel;
    private boolean meteorStormActive = true; // Flag to handle meteor storm phase

    public LevelFive(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        System.out.println("LevelFive initialized with ScreenWidth: " + getScreenWidth() + ", ScreenHeight: " + getScreenHeight());
        initializeWinningParameter();
        displayShowdownText();
    }
    @Override
    protected void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX(getScreenWidth() / 2 - 100);
        killsLabel.setLayoutY(20);

        String labelStyle = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(#ff0000, #ff5500); -fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);";
        killsLabel.setStyle(labelStyle);

        getRoot().getChildren().addAll(killsLabel);
        killsLabel.toFront();
    }

    @Override
    protected void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getNumberOfKills());
            killsLabel.toFront();
        });
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }
    @Override
    protected void spawnEnemyUnits() {
        // Handle Meteor Storm Phase
        if (meteorStormActive) {
            spawnMeteors();
        }
        // Handle Showdown Phase
        double spawnProbability = 0.25; // 10% chance to spawn an enemy per update

        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = 40 + (Math.random() * (getEnemyMaximumYPosition() - 40));
            ActiveActorDestructible newEnemy;

            double enemyTypeChance = Math.random();
            if (enemyTypeChance < 0.6) {
                newEnemy = new EnemyPlane(getScreenWidth(), randomYPosition);
            } else if (enemyTypeChance < 0.8) {
                newEnemy = new EnemyPlaneTypeA(getScreenWidth(), randomYPosition, this);
            } else{
                newEnemy = new EnemyPlaneTypeB(getScreenWidth(), randomYPosition, this);
            }
            addEnemyUnit(newEnemy);
        }
    }

    private void spawnMeteors() {
        double meteorProbability = 0.2; // 20% chance to spawn a meteor
        if (Math.random() < meteorProbability) {
            Meteor meteor = new Meteor(this); // Pass the level reference
            addProjectileToLevel(meteor); // Add meteor to the level
            System.out.println("Meteor spawned at X: " + meteor.getTranslateX() + ", Y: " + meteor.getTranslateY());
        }
    }
    private void displayShowdownText() {
        // Create a PauseTransition to delay the appearance of the showdown text
        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(Duration.seconds(3));

        delay.setOnFinished(event -> {
            // Run the following code on the JavaFX Application Thread
            Platform.runLater(() -> {
                Label showdownLabel = new Label("SHOWDOWN!!");
                showdownLabel.setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-text-fill: red;");
                showdownLabel.setLayoutX(getScreenWidth() / 2 - 200);
                showdownLabel.setLayoutY(getScreenHeight() / 2 - 50);
                getRoot().getChildren().add(showdownLabel);

                // Create another PauseTransition to remove the text after 3 seconds
                javafx.animation.PauseTransition removeDelay = new javafx.animation.PauseTransition(Duration.seconds(3));
                removeDelay.setOnFinished(removeEvent -> getRoot().getChildren().remove(showdownLabel));
                removeDelay.play(); // Explicitly start the second PauseTransition
            });
        });
        delay.play(); // Explicitly start the initial PauseTransition
    }
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelVeiwLevelFive(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight());
    }

    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    @Override
    protected void misc() {
        // Handle meteor storm end
        if (getNumberOfKills() > 5) {
            meteorStormActive = false; // Disable meteor storm after 5 kills
        }
    }
    @Override
    protected void initializeBackground() {
        super.initializeBackground(); // Call the original to set up basic behavior

        // Override and extend with additional controls for left and right movement
        background.setFocusTraversable(true);

        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (GameStateManager.getInstance().isGamePaused()) {
                    return; // Do nothing if the game is paused
                }

                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP) {
                    getUser().moveUp();
                } else if (kc == KeyCode.DOWN) {
                    getUser().moveDown();
                } else if (kc == KeyCode.LEFT) {
                    getUser().moveLeft();
                } else if (kc == KeyCode.RIGHT) {
                    getUser().moveRight();
                } else if (kc == KeyCode.SPACE) {
                    fireProjectile();
                }
            }
        });

        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (GameStateManager.getInstance().isGamePaused()) {
                    return; // Do nothing if the game is paused
                }

                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP || kc == KeyCode.DOWN) {
                    getUser().stopVerticalMovement();
                } else if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) {
                    getUser().stopHorizontalMovement();
                }
            }
        });
    }
}


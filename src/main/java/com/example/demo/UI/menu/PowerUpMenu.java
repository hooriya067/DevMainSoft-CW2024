
package com.example.demo.UI.menu;

import com.example.demo.Managers.AlertManager;
import com.example.demo.Managers.PowerUpManager;
import com.example.demo.core.GameStateManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * The {@link PowerUpMenu} class represents an in-game menu for purchasing power-ups.
 * Players can select from options like extra life, shield, or additional bullets.
 * This menu pauses the game and allows players to enhance their gameplay with power-ups.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Provides options for purchasing power-ups like extra life, shield, and bullets.</li>
 *     <li>Displays custom icons, labels, and prices for each power-up option.</li>
 *     <li>Integrates with {@link PowerUpManager}, {@link AlertManager}, and {@link GameStateManager} for functionality.</li>
 *     <li>Offers a "BACK" button to resume the game.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link InGameMenuParent}: Base class providing core overlay functionalities.</li>
 *     <li>{@link PowerUpManager}: Manages power-up purchases and their effects.</li>
 *     <li>{@link AlertManager}: Displays alerts to players for feedback.</li>
 *     <li>{@link GameStateManager}: Manages the game's state (paused or active).</li>
 * </ul>
 */
public class PowerUpMenu extends InGameMenuParent {

    /**
     * Constructs a {@code PowerUpMenu} with the specified stage.
     *
     * @param stage the {@link Stage} on which the power-up menu is displayed
     */
    public PowerUpMenu(Stage stage) {
        super(stage);
    }

    /**
     * Displays the power-up menu overlay.
     * Pauses the game and shows available power-up options.
     */
    public void displayOverlay() {
        VBox menuContent = createMenuContent();
        super.displayOverlay(menuContent);
    }

    /**
     * Creates the content of the power-up menu.
     * Includes options for purchasing extra life, shield, and bullets, as well as a back button.
     *
     * @return a {@link VBox} containing the menu's content
     */
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
        VBox healthBox = createPowerUpButtonWithLabels("heart.png", "EXTRA LIFE", "5 Coins", () -> {
            if (PowerUpManager.getInstance().purchaseExtraLife()) {
                removeOverlay();
                GameStateManager.getInstance().resumeGame();
                AlertManager.getInstance().showAlert("You are now One Heart Stronger!!!");
            } else {
                AlertManager.getInstance().showAlert("Not Enough coins for Extra Health");
            }
        });

        // Shield Power-Up
        VBox shieldBox = createPowerUpButtonWithLabels("shield2.png", "SHIELD", "7 Coins", () -> {
            if (PowerUpManager.getInstance().purchaseShield()) {
                removeOverlay();
                GameStateManager.getInstance().resumeGame();
                AlertManager.getInstance().showAlert("Shield is Fully Loaded!!!!");
            } else {
                AlertManager.getInstance().showAlert("You don't have enough coins OR Shield is already active.");
            }
        });

        // Bullets Power-Up
        VBox bulletsBox = createPowerUpButtonWithLabels("bulletbelt.png", "10 BULLETS", "8 Coins", () -> {
            if (PowerUpManager.getInstance().purchaseBullets()) {
                removeOverlay();
                GameStateManager.getInstance().resumeGame();
                AlertManager.getInstance().showAlert("Bullets Reloaded successfully Let’s go!");
            } else {
                AlertManager.getInstance().showAlert("You don't have enough coins for Bullets Belts");
            }
        });

        powerUpBox.getChildren().addAll(healthBox, shieldBox, bulletsBox);

        // Back button
        Button resumeButton = new Button("BACK");
        resumeButton.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: darkblue;");
        resumeButton.setOnAction(e -> {
            removeOverlay();
            startResumeCountdown();
        });

        menuBox.getChildren().addAll(title, powerUpBox, resumeButton);
        return menuBox;
    }

    /**
     * Creates a button with an icon, label, and price for a power-up option.
     *
     * @param imagePath the file path of the power-up icon
     * @param label     the name of the power-up
     * @param price     the price of the power-up
     * @param action    the action to execute when the button is clicked
     * @return a {@link VBox} containing the button and its associated labels
     */
    private VBox createPowerUpButtonWithLabels(String imagePath, String label, String price, Runnable action) {
        ImageView icon = new ImageView(new Image(getClass().getResource("/com/example/demo/images/" + imagePath).toExternalForm()));
        icon.setFitWidth(60);
        icon.setFitHeight(60);

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

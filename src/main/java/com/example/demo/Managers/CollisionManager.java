/**
 * The {@code CollisionManager} class is responsible for handling all collision-related logic in the game.
 * It manages interactions between friendly units, enemies, projectiles, and collectible items like coins.
 * By centralizing collision handling, this class improves code modularity and maintains the game's logic
 * cleanly separated from the UI and actor management.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link ControllableLevel}: Provides the level's main gameplay elements, including the root node and user-controlled actors.</li>
 *     <li>{@link ActorManager}: Supplies the lists of actors (e.g., enemies, friendly units, projectiles) involved in collisions.</li>
 *     <li>{@link Updatable}: Ensures the collision manager is called in the game loop for consistent updates.</li>
 *     <li>{@link CoinSystemManager}: Handles coin-related logic when coins are collected by the player.</li>
 *     <li>{@link SoundManager}: Plays sound effects when collisions occur (e.g., coin collection).</li>
 * </ul>
 */
package com.example.demo.Managers;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.Updatable;
import com.example.demo.Levels.ControllableLevel;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.scene.image.ImageView;


import java.util.ArrayList;
import java.util.List;

/**
 * Manages all collision logic, including detecting and resolving collisions between actors and items.
 */
public class CollisionManager implements Updatable {

    /**
     * The level associated with this collision manager.
     */
    private final ControllableLevel level;

    /**
     * The actor manager supplying actors for collision detection.
     */
    private final ActorManager actorManager;

    /**
     * Constructs a {@code CollisionManager} instance with the specified level and actor manager.
     *
     * @param level        the level containing the actors and gameplay elements
     * @param actorManager the actor manager managing active actors
     */
    public CollisionManager(ControllableLevel level, ActorManager actorManager) {
        this.level = level;
        this.actorManager = actorManager;
    }

    /**
     * Updates the collision manager by handling all collision logic.
     */
    @Override
    public void update() {
        handleAllCollisions();
    }

    /**
     * Handles all collision types in the game, including planes, projectiles, coins, and enemy penetration.
     */
    public void handleAllCollisions() {
        handlePlaneCollisions();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleCoinCollisions();
        handleEnemyPenetration(actorManager.getEnemyUnits());
    }

    /**
     * Handles collisions between friendly units and enemy units.
     */
    private void handlePlaneCollisions() {
        handleCollisions(actorManager.getFriendlyUnits(), actorManager.getEnemyUnits());
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     */
    private void handleUserProjectileCollisions() {
        handleCollisions(actorManager.getUserProjectiles(), actorManager.getEnemyUnits());
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    private void handleEnemyProjectileCollisions() {
        handleCollisions(actorManager.getEnemyProjectiles(), actorManager.getFriendlyUnits());
    }

    /**
     * Handles collisions between the user and collectible coins.
     */
    public void handleCoinCollisions() {
        List<Node> coinsToRemove = new ArrayList<>();

        for (Node node : new ArrayList<>(level.getRoot().getChildren())) { // Iterate over a copy
            if (node instanceof Coin coin && coin.getBoundsInParent().intersects(level.getUser().getBoundsInParent())) {
                SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/coin.mp3");
                CoinSystemManager.getInstance().addCoins(1);

                double userX = level.getUser().getLayoutX() + level.getUser().getTranslateX() + 20;
                double userY = level.getUser().getLayoutY() + level.getUser().getTranslateY();
                coin.showPlusOneEffect(level.getRoot(), userX, userY);
                coinsToRemove.add(coin);
            }
        }
        Platform.runLater(() -> {
            level.getRoot().getChildren().removeAll(coinsToRemove);
            level.getCoins().removeAll(coinsToRemove);
        });
    }

    /**
     * Handles enemy units that penetrate beyond the screen boundaries, destroying them.
     *
     * @param enemies the list of enemy units to check for penetration
     */
    public void handleEnemyPenetration(List<ActiveActor> enemies) {
        double screenWidth = level.getScreenWidth();
        for (ActiveActor enemy : enemies) {
            if (Math.abs(enemy.getTranslateX()) > screenWidth) {
                enemy.destroy();
            }
        }
    }

    /**
     * Handles collisions between two groups of actors.
     *
     * @param actors1 the first group of actors
     * @param actors2 the second group of actors
     */
    private void handleCollisions(List<ActiveActor> actors1, List<ActiveActor> actors2) {
        boolean isShieldActive = level.isShieldActive();
        for (ActiveActor actor : actors2) {
            for (ActiveActor otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    if ((actor instanceof UserPlane && isShieldActive) || (otherActor instanceof UserPlane && isShieldActive)) {
                        handleShieldCollision(actor, otherActor);
                    } else {
                        handleRegularCollision(actor, otherActor);
                    }
                }
            }
        }
    }
    /**
     * Handles a collision where a shield absorbs the damage.
     *
     * @param actor      the first actor involved in the collision
     * @param otherActor the second actor involved in the collision
     */
    private void handleShieldCollision(ActiveActor actor, ActiveActor otherActor) {
        if (!(actor instanceof UserPlane)) {
            actor.takeDamage();
        }
        if (!(otherActor instanceof UserPlane)) {
            otherActor.takeDamage();
        }

        if (actor.isDestroyed()) {
            level.incrementKillCount();
        }
    }

    /**
     * Handles a regular collision where both actors take damage.
     *
     * @param actor      the first actor involved in the collision
     * @param otherActor the second actor involved in the collision
     */
    private void handleRegularCollision(ActiveActor actor, ActiveActor otherActor) {
        actor.takeDamage();
        otherActor.takeDamage();

        // Display explosion effect at the collision point
        if (actor.isDestroyed() || otherActor.isDestroyed()) {
            double collisionX = (actor.getLayoutX() + actor.getTranslateX() + otherActor.getLayoutX() + otherActor.getTranslateX()) / 2;
            double collisionY = (actor.getLayoutY() + actor.getTranslateY() + otherActor.getLayoutY() + otherActor.getTranslateY()) / 2;
            showExplosionEffect(collisionX, collisionY);
        }

        if (actor.isDestroyed()) {
            level.incrementKillCount();
        }

    }

    /**
     * Displays an explosion effect at the specified position.
     *
     * @param x the X-coordinate of the explosion
     * @param y the Y-coordinate of the explosion
     */
    private void showExplosionEffect(double x, double y) {
        // Load the explosion image
        Image explosionImage = new Image(getClass().getResource("/com/example/demo/images/explosion.png").toExternalForm());
        ImageView explosionView = new ImageView(explosionImage);

        // Set explosion image properties
        explosionView.setFitWidth(100);
        explosionView.setPreserveRatio(true);
        explosionView.setLayoutX(x - 20);
        explosionView.setLayoutY(y - 50);

        // Add explosion to the game root
        Platform.runLater(() -> level.getRoot().getChildren().add(explosionView));

        // Create a fade-out animation
        FadeTransition fade = new FadeTransition(Duration.seconds(1), explosionView);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> Platform.runLater(() -> level.getRoot().getChildren().remove(explosionView)));
        fade.play();
    }

}

package com.example.demo.Managers;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.Updatable;
import com.example.demo.Levels.ControllableLevel;
import javafx.application.Platform;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager implements Updatable {
    private final ControllableLevel level;
    private final ActorManager actorManager;

    public CollisionManager(ControllableLevel level, ActorManager actorManager) {
        this.level = level;
        this.actorManager = actorManager;
    }

    @Override
    public void update() {
        handleAllCollisions();
    }

    public void handleAllCollisions() {
        handlePlaneCollisions();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleCoinCollisions();
        handleEnemyPenetration(actorManager.getEnemyUnits());
    }

    private void handlePlaneCollisions() {
        handleCollisions(actorManager.getFriendlyUnits(), actorManager.getEnemyUnits());
    }

    private void handleUserProjectileCollisions() {
        handleCollisions(actorManager.getUserProjectiles(), actorManager.getEnemyUnits());
    }

    private void handleEnemyProjectileCollisions() {
        handleCollisions(actorManager.getEnemyProjectiles(), actorManager.getFriendlyUnits());
    }
    public void handleCoinCollisions() {
        // Create a separate list for nodes to remove after iteration
        List<Node> coinsToRemove = new ArrayList<>();

        for (Node node : new ArrayList<>(level.getRoot().getChildren())) { // Iterate over a copy
            if (node instanceof Coin coin && coin.getBoundsInParent().intersects(level.getUser().getBoundsInParent())) {
                SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/coin.mp3");
                CoinSystemManager.getInstance().addCoins(1);

                double userX = level.getUser().getLayoutX() + level.getUser().getTranslateX()+20;
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
    public void handleEnemyPenetration(List<ActiveActor> enemies) {
        double screenWidth = level.getScreenWidth();
        for (ActiveActor enemy : enemies) {
            if (Math.abs(enemy.getTranslateX()) > screenWidth) {
                enemy.destroy();
            }
        }
    }

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

    private void handleShieldCollision(ActiveActor actor, ActiveActor otherActor) {
        System.out.println("Shield absorbed collision! No damage to user.");
        if (!(actor instanceof UserPlane)) {
            actor.takeDamage();
        }
        if (!(otherActor instanceof UserPlane)) {
            otherActor.takeDamage();
        }
    }

    private void handleRegularCollision(ActiveActor actor, ActiveActor otherActor) {
        actor.takeDamage();
        otherActor.takeDamage();

        if (actor.isDestroyed()) {
            level.incrementKillCount();
        }
    }
}

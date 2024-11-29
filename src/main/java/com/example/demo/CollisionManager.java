package com.example.demo;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    private final LevelParent levelParent;

    public CollisionManager(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    public void handleAllCollisions() {
        handlePlaneCollisions();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleCoinCollisions();
        handleEnemyPenetration(levelParent.getEnemyUnits());
    }


    private void handlePlaneCollisions() {
        handleCollisions(levelParent.getFriendlyUnits(), levelParent.getEnemyUnits());
    }

    private void handleUserProjectileCollisions() {
        handleCollisions(levelParent.getUserProjectiles(), levelParent.getEnemyUnits());
    }

    private void handleEnemyProjectileCollisions() {
        handleCollisions(levelParent.getEnemyProjectiles(), levelParent.getFriendlyUnits());
    }

    public void handleCoinCollisions() {
        List<Node> coinsToRemove = new ArrayList<>();

        for (Node node : levelParent.getRoot().getChildren()) { // Iterate over the scene graph
            if (node instanceof Coin) {
                Coin coin = (Coin) node;
                if (coin.getBoundsInParent().intersects(levelParent.getUser().getBoundsInParent())) {
                    CoinSystem.getInstance().addCoins(1); // Update the coin system
                    coinsToRemove.add(coin); // Mark the coin for removal
                    System.out.println("Coin collected! Total coins: " + CoinSystem.getInstance().getCoins());
                }
            }
        }
        levelParent.getRoot().getChildren().removeAll(coinsToRemove);
        levelParent.getCoins().removeAll(coinsToRemove);
    }


    public void handleEnemyPenetration(List<ActiveActorDestructible> enemies) {
        double screenWidth = levelParent.getScreenWidth(); // Access screen width from LevelParent
        for (ActiveActorDestructible enemy : enemies) {
            if (Math.abs(enemy.getTranslateX()) > screenWidth) { // Logic moved from enemyHasPenetratedDefenses
                enemy.destroy();
            }
        }
    }


    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        boolean isShieldActive = levelParent.isShieldActive(); // Check if shield is active
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
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

    private void handleShieldCollision(ActiveActorDestructible actor, ActiveActorDestructible otherActor) {
        System.out.println("Shield absorbed collision! No damage to user.");
        if (!(actor instanceof UserPlane)) {
            actor.takeDamage();
        }
        if (!(otherActor instanceof UserPlane)) {
            otherActor.takeDamage();
        }
    }

    private void handleRegularCollision(ActiveActorDestructible actor, ActiveActorDestructible otherActor) {
        actor.takeDamage();
        otherActor.takeDamage();

        if (actor.isDestroyed()) {
            levelParent.incrementKillCount();
        }
    }
}


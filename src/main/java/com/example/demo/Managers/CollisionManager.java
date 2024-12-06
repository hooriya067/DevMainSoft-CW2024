package com.example.demo.Managers;

import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.Updatable;
import com.example.demo.levels.ControllableLevel;
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
        List<Node> coinsToRemove = new ArrayList<>();

        for (Node node : level.getRoot().getChildren()) {
            if (node instanceof Coin coin && coin.getBoundsInParent().intersects(level.getUser().getBoundsInParent())) {
                CoinSystemManager.getInstance().addCoins(1);
                coinsToRemove.add(coin);
                System.out.println("Coin collected! Total coins: " + CoinSystemManager.getInstance().getCoins());
            }
        }
        level.getRoot().getChildren().removeAll(coinsToRemove);
        level.getCoins().removeAll(coinsToRemove);
    }

    public void handleEnemyPenetration(List<ActiveActorDestructible> enemies) {
        double screenWidth = level.getScreenWidth();
        for (ActiveActorDestructible enemy : enemies) {
            if (Math.abs(enemy.getTranslateX()) > screenWidth) {
                enemy.destroy();
            }
        }
    }

    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        boolean isShieldActive = level.isShieldActive();
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
            level.incrementKillCount();
        }
    }
}

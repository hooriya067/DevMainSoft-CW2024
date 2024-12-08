package com.example.demo.Managers;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.core.Updatable;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActorManager implements Updatable {

    private final List<ActiveActor> friendlyUnits = new ArrayList<>();
    private final List<ActiveActor> enemyUnits = new ArrayList<>();
    private final List<ActiveActor> userProjectiles = new ArrayList<>();
    private final List<ActiveActor> enemyProjectiles = new ArrayList<>();
    private final List<Coin> coins = new ArrayList<>();
    private final Group root;

    public ActorManager(Group root) {
        this.root = root;
    }
    @Override
    public void update() {
        updateActors();
        removeDestroyedActors();
    }

    // Add methods
    public void addFriendlyUnit(ActiveActor unit) {
        if (friendlyUnits.contains(unit)) {
            System.out.println("Duplicate friendly unit detected: " + unit);
            return; // Prevent adding duplicate
        }
        friendlyUnits.add(unit);
        root.getChildren().add(unit);
    }
    public void addEnemyUnit(ActiveActor unit) {
        if (unit == null) {
            System.err.println("Attempted to add a null enemy!");
            return;
        }
        // System.out.println("Adding enemy: " + unit); // Debug: Check enemy addition
        enemyUnits.add(unit);
        root.getChildren().add(unit);
    }

    public void addUserProjectile(ActiveActor projectile) {
        if (projectile == null) {
            System.err.println("Attempted to add a null projectile to the root!");
            return;
        }
        userProjectiles.add(projectile);
        root.getChildren().add(projectile);
    }
    public void addEnemyProjectile(ActiveActor projectile) {
        if (!enemyProjectiles.contains(projectile)) { // Prevent duplicate addition
            enemyProjectiles.add(projectile);
            root.getChildren().add(projectile);
        } else {
            System.out.println("Duplicate projectile detected: " + projectile);
        }
    }
    public void addCoin(Coin coin) {
        coins.add(coin);
        root.getChildren().add(coin);
        coin.toFront();
    }
    public void removeDestroyedActors() {
        removeDestroyed(friendlyUnits);
        removeDestroyed(enemyUnits);
        removeDestroyed(userProjectiles);
        enemyProjectiles.removeIf(projectile -> {
            if (projectile.isDestroyed()) {
                root.getChildren().remove(projectile);
                return true;
            }
            return false;
        });
        coins.removeIf(Coin::isDestroyed);
    }

    private void removeDestroyed(List<ActiveActor> actors) {
        List<ActiveActor> destroyedActors = actors.stream()
                .filter(ActiveActor::isDestroyed)
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    public void updateActors() {
        friendlyUnits.forEach(ActiveActor::updateActor);
        enemyUnits.forEach(ActiveActor::updateActor);
        userProjectiles.forEach(ActiveActor::updateActor);
        enemyProjectiles.forEach(ActiveActor::updateActor);
        root.getChildren().stream()
                .filter(node -> node instanceof Coin)
                .map(node -> (Coin) node)
                .forEach(Coin::updateActor); // Ensure coins are updated
    }


    // Getters for actor lists
    public List<ActiveActor> getFriendlyUnits() {
        return friendlyUnits;
    }

    public List<ActiveActor> getEnemyUnits() {
        return enemyUnits;
    }

    public List<ActiveActor> getUserProjectiles() {
        return userProjectiles;
    }

    public List<ActiveActor> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    public List<Coin> getCoins() {
        return coins;
    }
}

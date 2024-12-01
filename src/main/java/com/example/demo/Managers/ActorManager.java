package com.example.demo.Managers;

import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.collectibles.Coin;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActorManager {

    private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
    private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
    private final List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
    private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
    private final List<Coin> coins = new ArrayList<>();
    private final Group root;

    public ActorManager(Group root) {
        this.root = root;
    }
    public void handleAllActors() {
        updateActors();
        removeDestroyedActors();
    }

    // Add methods
    public void addFriendlyUnit(ActiveActorDestructible unit) {
        if (friendlyUnits.contains(unit)) {
            System.out.println("Duplicate friendly unit detected: " + unit);
            return; // Prevent adding duplicate
        }
        friendlyUnits.add(unit);
        root.getChildren().add(unit);
    }


    public void addEnemyUnit(ActiveActorDestructible unit) {
        enemyUnits.add(unit);
        root.getChildren().add(unit);
    }

    public void addUserProjectile(ActiveActorDestructible projectile) {
        userProjectiles.add(projectile);
        root.getChildren().add(projectile);
    }

    public void addEnemyProjectile(ActiveActorDestructible projectile) {
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

    private void removeDestroyed(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    public void updateActors() {
        friendlyUnits.forEach(ActiveActorDestructible::updateActor);
        enemyUnits.forEach(ActiveActorDestructible::updateActor);
        userProjectiles.forEach(ActiveActorDestructible::updateActor);
        enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
        root.getChildren().stream()
                .filter(node -> node instanceof Coin)
                .map(node -> (Coin) node)
                .forEach(Coin::updateActor); // Ensure coins are updated
    }


    // Getters for actor lists
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    public List<Coin> getCoins() {
        return coins;
    }
}

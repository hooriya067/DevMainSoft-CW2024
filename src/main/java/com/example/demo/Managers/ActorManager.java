/**
 * The {@code ActorManager} class is responsible for managing all active actors in the game,
 * including friendly units, enemy units, projectiles, and coins. It centralizes the logic for
 * adding, updating, and removing actors while maintaining a clean separation from the game levels.
 *
 * <p>This class was derived from the {@link LevelParent} class by refactoring its actor management
 * functionality into a dedicated manager. The primary goals were to improve maintainability,
 * scalability, and modularity by adhering to the Single Responsibility Principle.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link LevelParent}: The class from which actor management logic was extracted.</li>
 *     <li>{@link Updatable}: Ensures the manager is updated during the game loop.</li>
 *     <li>{@link ActiveActor}: Represents active entities such as friendly units, enemies, and projectiles.</li>
 *     <li>{@link Coin}: Represents collectible coin objects.</li>
 * </ul>
 */
package com.example.demo.Managers;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.core.Updatable;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all active actors in the game, including adding, updating, and removing them from the scene.
 */
public class ActorManager implements Updatable {

    /**
     * List of friendly units controlled by the player.
     */
    private final List<ActiveActor> friendlyUnits = new ArrayList<>();

    /**
     * List of enemy units in the game.
     */
    private final List<ActiveActor> enemyUnits = new ArrayList<>();

    /**
     * List of projectiles fired by the player.
     */
    private final List<ActiveActor> userProjectiles = new ArrayList<>();

    /**
     * List of projectiles fired by enemies.
     */
    private final List<ActiveActor> enemyProjectiles = new ArrayList<>();

    /**
     * List of collectible coins.
     */
    private final List<Coin> coins = new ArrayList<>();

    /**
     * The root group for managing the scene graph.
     */
    private final Group root;

    /**
     * Constructs an ActorManager instance with the specified root group.
     *
     * @param root the root group for managing the scene graph
     */
    public ActorManager(Group root) {
        this.root = root;
    }

    /**
     * Updates all actors by invoking their respective update methods and removes destroyed actors.
     */
    @Override
    public void update() {
        updateActors();
        removeDestroyedActors();
    }

    /**
     * Adds a friendly unit to the game.
     *
     * @param unit the friendly unit to add
     */
    public void addFriendlyUnit(ActiveActor unit) {
        if (friendlyUnits.contains(unit)) {
            System.out.println("Duplicate friendly unit detected: " + unit);
            return; // Prevent adding duplicate
        }
        friendlyUnits.add(unit);
        root.getChildren().add(unit);
    }

    /**
     * Adds an enemy unit to the game.
     *
     * @param unit the enemy unit to add
     */
    public void addEnemyUnit(ActiveActor unit) {
        if (unit == null) {
            System.err.println("Attempted to add a null enemy!");
            return;
        }
        enemyUnits.add(unit);
        root.getChildren().add(unit);
    }

    /**
     * Adds a projectile fired by the player to the game.
     *
     * @param projectile the user projectile to add
     */
    public void addUserProjectile(ActiveActor projectile) {
        if (projectile == null) {
            System.err.println("Attempted to add a null projectile to the root!");
            return;
        }
        userProjectiles.add(projectile);
        root.getChildren().add(projectile);
    }

    /**
     * Adds a projectile fired by an enemy to the game.
     *
     * @param projectile the enemy projectile to add
     */
    public void addEnemyProjectile(ActiveActor projectile) {
        if (!enemyProjectiles.contains(projectile)) { // Prevent duplicate addition
            enemyProjectiles.add(projectile);
            root.getChildren().add(projectile);
        } else {
            System.out.println("Duplicate projectile detected: " + projectile);
        }
    }

    /**
     * Adds a coin to the game.
     *
     * @param coin the coin to add
     */
    public void addCoin(Coin coin) {
        coins.add(coin);
        root.getChildren().add(coin);
        coin.toFront();
    }

    /**
     * Removes all destroyed actors from the game and their respective lists.
     */
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

    /**
     * Helper method to remove destroyed actors from a given list.
     *
     * @param actors the list of actors to check for removal
     */
    private void removeDestroyed(List<ActiveActor> actors) {
        List<ActiveActor> destroyedActors = actors.stream()
                .filter(ActiveActor::isDestroyed)
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Updates all active actors.
     */
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

    /**
     * Retrieves the list of friendly units.
     *
     * @return the list of friendly units
     */
    public List<ActiveActor> getFriendlyUnits() {
        return friendlyUnits;
    }

    /**
     * Retrieves the list of enemy units.
     *
     * @return the list of enemy units
     */
    public List<ActiveActor> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Retrieves the list of projectiles fired by the player.
     *
     * @return the list of user projectiles
     */
    public List<ActiveActor> getUserProjectiles() {
        return userProjectiles;
    }

    /**
     * Retrieves the list of projectiles fired by enemies.
     *
     * @return the list of enemy projectiles
     */
    public List<ActiveActor> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Retrieves the list of collectible coins.
     *
     * @return the list of coins
     */
    public List<Coin> getCoins() {
        return coins;
    }
}

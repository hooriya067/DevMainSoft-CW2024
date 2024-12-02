package com.example.demo.levels;

import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.List;

public interface ControllableLevel {
    void fireProjectile();
    Scene initializeScenario();
    void startGame();
    Group getRoot();
    UserPlane getUser();
    double getScreenWidth();
    boolean isShieldActive();
    void incrementKillCount();
    List<Coin> getCoins();

}

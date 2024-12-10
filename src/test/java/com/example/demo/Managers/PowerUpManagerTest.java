package com.example.demo.Managers;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.collectibles.ShieldImage;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.Levels.view.LevelViewParent;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PowerUpManagerTest {

    private PowerUpManager powerUpManager;
    private LevelParent mockLevelParent;
    private UserPlane mockUser;
    private LevelViewParent mockLevelView;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() {
        powerUpManager = PowerUpManager.getInstance();
        mockLevelParent = mock(LevelParent.class);
        mockUser = mock(UserPlane.class);
        mockLevelView = mock(LevelViewParent.class);

        // Set up the level parent mocks
        when(mockLevelParent.getUser()).thenReturn(mockUser);
        when(mockLevelParent.getLevelView()).thenReturn(mockLevelView);

        // Set the level parent in the PowerUpManager
        powerUpManager.setLevelParent(mockLevelParent);
    }

    @Test
    void testPurchaseExtraLifeSuccess() {
        // Mock coin system
        CoinSystemManager mockCoinSystem = mock(CoinSystemManager.class);
        when(mockCoinSystem.subtractCoins(2)).thenReturn(true);

        try (MockedStatic<CoinSystemManager> coinSystemMock = mockStatic(CoinSystemManager.class)) {
            coinSystemMock.when(CoinSystemManager::getInstance).thenReturn(mockCoinSystem);

            // Perform purchase
            boolean result = powerUpManager.purchaseExtraLife();

            // Verify
            assertTrue(result, "Purchase of extra life should succeed when coins are sufficient.");
            verify(mockUser, times(1)).addHealth(1);
            verify(mockLevelView, times(1)).addHeart();
        }
    }


    @Test
    void testPurchaseShieldSuccess() {
        // Mock coin system and shield status
        CoinSystemManager mockCoinSystem = mock(CoinSystemManager.class);
        when(mockCoinSystem.subtractCoins(2)).thenReturn(true);
        when(mockLevelParent.isShieldActive()).thenReturn(false);

        try (MockedStatic<CoinSystemManager> coinSystemMock = mockStatic(CoinSystemManager.class)) {
            coinSystemMock.when(CoinSystemManager::getInstance).thenReturn(mockCoinSystem);

            // Mock shield and level view
            ShieldImage mockUserShield = mock(ShieldImage.class);
            LevelViewParent mockLevelView = mock(LevelViewParent.class);
            when(mockLevelParent.getUserShield()).thenReturn(mockUserShield);
            when(mockLevelParent.getLevelView()).thenReturn(mockLevelView);

            boolean result = powerUpManager.purchaseShield();

            assertTrue(result, "Purchase of shield should succeed when coins are sufficient.");
            verify(mockUserShield, times(1)).showShield();
            verify(mockLevelView, times(1)).startShieldTimer(20);
        }
    }

    @Test
    void testPurchaseShieldInsufficientCoins() {
        CoinSystemManager mockCoinSystem = mock(CoinSystemManager.class);
        when(mockCoinSystem.subtractCoins(2)).thenReturn(false);

        try (MockedStatic<CoinSystemManager> coinSystemMock = mockStatic(CoinSystemManager.class)) {
            coinSystemMock.when(CoinSystemManager::getInstance).thenReturn(mockCoinSystem);

            boolean result = powerUpManager.purchaseShield();

            assertFalse(result, "Purchase of shield should fail when coins are insufficient.");
        }
    }

    @Test
    void testPurchaseBulletsSuccess() {
        // Mock coin and bullet systems
        CoinSystemManager mockCoinSystem = mock(CoinSystemManager.class);
        when(mockCoinSystem.subtractCoins(3)).thenReturn(true);
        BulletSystemManager mockBulletSystem = mock(BulletSystemManager.class);

        try (MockedStatic<CoinSystemManager> coinSystemMock = mockStatic(CoinSystemManager.class);
             MockedStatic<BulletSystemManager> bulletSystemMock = mockStatic(BulletSystemManager.class)) {
            coinSystemMock.when(CoinSystemManager::getInstance).thenReturn(mockCoinSystem);
            bulletSystemMock.when(BulletSystemManager::getInstance).thenReturn(mockBulletSystem);

            boolean result = powerUpManager.purchaseBullets();

            assertTrue(result, "Purchase of bullets should succeed when coins are sufficient.");
            verify(mockBulletSystem, times(1)).addBullets(10);
        }
    }

    @Test
    void testPurchaseBulletsInsufficientCoins() {
        CoinSystemManager mockCoinSystem = mock(CoinSystemManager.class);
        when(mockCoinSystem.subtractCoins(3)).thenReturn(false);

        try (MockedStatic<CoinSystemManager> coinSystemMock = mockStatic(CoinSystemManager.class)) {
            coinSystemMock.when(CoinSystemManager::getInstance).thenReturn(mockCoinSystem);

            boolean result = powerUpManager.purchaseBullets();

            assertFalse(result, "Purchase of bullets should fail when coins are insufficient.");
        }
    }

    @Test
    void testActivateShield() {
        CoinSystemManager mockCoinSystem = mock(CoinSystemManager.class);
        when(mockCoinSystem.subtractCoins(2)).thenReturn(true);

        try (MockedStatic<CoinSystemManager> coinSystemMock = mockStatic(CoinSystemManager.class)) {
            coinSystemMock.when(CoinSystemManager::getInstance).thenReturn(mockCoinSystem);

            ShieldImage mockUserShield = mock(ShieldImage.class);
            LevelViewParent mockLevelView = mock(LevelViewParent.class);
            when(mockLevelParent.getUserShield()).thenReturn(mockUserShield);
            when(mockLevelParent.getLevelView()).thenReturn(mockLevelView);

            when(mockLevelParent.isShieldActive()).thenReturn(false);

            powerUpManager.purchaseShield();

            verify(mockUserShield, times(1)).showShield();
            verify(mockLevelView, times(1)).startShieldTimer(20);
        }
    }

    /**
     * /MAKE deactivateShield(); PUBLIC AND IT PASSES THE TEST BUT BECAUSE OF ENCAPSULATION ITS COMMENTED OUT.
     */
//    @Test
//    void testDeactivateShield() {
//        // Mock necessary objects
//        ShieldImage mockUserShield = mock(ShieldImage.class);
//        LevelViewParent mockLevelView = mock(LevelViewParent.class);
//
//        // Set up mocks for LevelParent
//        when(mockLevelParent.getUserShield()).thenReturn(mockUserShield);
//        when(mockLevelParent.getLevelView()).thenReturn(mockLevelView);
//        when(mockLevelParent.isShieldActive()).thenReturn(false);
//
//        try (MockedStatic<CoinSystemManager> coinSystemMock = mockStatic(CoinSystemManager.class)) {
//            CoinSystemManager mockCoinSystem = mock(CoinSystemManager.class);
//            coinSystemMock.when(CoinSystemManager::getInstance).thenReturn(mockCoinSystem);
//
//            when(mockCoinSystem.subtractCoins(2)).thenReturn(true);
//
//            // Activate shield
//            powerUpManager.purchaseShield();
//
//            // Deactivate shield
//            powerUpManager.deactivateShield();
//
//            // Verify hideShield() is called
//            verify(mockUserShield, times(1)).hideShield();
//        }
//    }

}
package com.example.demo.Managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class CoinSystemManagerTest {

    @BeforeEach
    void resetInstance() {
        // Reset the singleton instance for testing purposes
        CoinSystemManager instance = CoinSystemManager.getInstance();
        while (instance.getCoins() > 0) {
            instance.subtractCoins(instance.getCoins());
        }
    }

    @Test
    void testSingletonBehavior() {
        CoinSystemManager instance1 = CoinSystemManager.getInstance();
        CoinSystemManager instance2 = CoinSystemManager.getInstance();
        assertSame(instance1, instance2, "Both instances should refer to the same object");
    }

    @Test
    void testAddCoins() {
        CoinSystemManager coinManager = CoinSystemManager.getInstance();
        coinManager.addCoins(10);
        assertEquals(10, coinManager.getCoins(), "Total coins should be 10 after adding 10 coins");
    }

    @Test
    void testSubtractCoinsSufficientBalance() {
        CoinSystemManager coinManager = CoinSystemManager.getInstance();
        coinManager.addCoins(20);
        boolean result = coinManager.subtractCoins(15);
        assertTrue(result, "Transaction should be successful");
        assertEquals(5, coinManager.getCoins(), "Remaining coins should be 5");
    }

    @Test
    void testSubtractCoinsInsufficientBalance() {
        CoinSystemManager coinManager = CoinSystemManager.getInstance();
        coinManager.addCoins(10);
        boolean result = coinManager.subtractCoins(15);
        assertFalse(result, "Transaction should fail due to insufficient balance");
        assertEquals(10, coinManager.getCoins(), "Total coins should remain unchanged");
    }

    @Test
    void testListenerNotification() {
        CoinSystemManager coinManager = CoinSystemManager.getInstance();
        AtomicInteger lastNotifiedValue = new AtomicInteger(-1);

        coinManager.addListener(lastNotifiedValue::set);
        coinManager.addCoins(10);

        assertEquals(10, lastNotifiedValue.get(), "Listener should be notified with the updated coin count");
    }
}

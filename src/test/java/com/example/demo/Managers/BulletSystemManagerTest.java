package com.example.demo.Managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class BulletSystemManagerTest {

    @BeforeEach
    void resetInstance() {
        // Reset the singleton instance for testing purposes
        BulletSystemManager bulletSystemManager = BulletSystemManager.getInstance();
        bulletSystemManager.setBullets(120); // Reset to default bullets
        bulletSystemManager.setBulletsUsed(0); // Reset used bullets
    }

    @Test
    void testSingletonBehavior() {
        BulletSystemManager instance1 = BulletSystemManager.getInstance();
        BulletSystemManager instance2 = BulletSystemManager.getInstance();
        assertSame(instance1, instance2, "Both instances should refer to the same object");
    }

    @Test
    void testAddBullets() {
        BulletSystemManager bulletSystemManager = BulletSystemManager.getInstance();
        bulletSystemManager.addBullets(50);
        assertEquals(150, bulletSystemManager.getBullets(), "Total bullets should be 150 after adding 50 bullets");
    }

    @Test
    void testSubtractBulletsSufficient() {
        BulletSystemManager bulletSystemManager = BulletSystemManager.getInstance();
        boolean result = bulletSystemManager.subtractBullets(20);
        assertTrue(result, "Should successfully subtract bullets");
        assertEquals(80, bulletSystemManager.getBullets(), "Remaining bullets should be 80");
    }

    @Test
    void testSubtractBulletsInsufficient() {
        BulletSystemManager bulletSystemManager = BulletSystemManager.getInstance();
        boolean result = bulletSystemManager.subtractBullets(120);
        assertFalse(result, "Should not subtract bullets if insufficient");
        assertEquals(100, bulletSystemManager.getBullets(), "Total bullets should remain unchanged");
    }

    @Test
    void testBulletsUsedIncrement() {
        BulletSystemManager bulletSystemManager = BulletSystemManager.getInstance();
        bulletSystemManager.incrementBulletsUsed();
        bulletSystemManager.incrementBulletsUsed();
        assertEquals(2, bulletSystemManager.getBulletsUsed(), "Bullets used should be incremented to 2");
    }

    @Test
    void testListenerNotification() {
        BulletSystemManager bulletSystemManager = BulletSystemManager.getInstance();
        AtomicInteger notifiedValue = new AtomicInteger(-1);

        bulletSystemManager.addListener(notifiedValue::set);
        bulletSystemManager.subtractBullets(10);

        assertEquals(90, notifiedValue.get(), "Listener should be notified with the updated bullet count");
    }
}

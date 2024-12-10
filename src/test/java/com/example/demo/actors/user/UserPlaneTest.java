package com.example.demo.actors.user;

import com.example.demo.Managers.BulletSystemManager;
import com.example.demo.Managers.SoundManager;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.Factories.UserPlaneFactory;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.mockito.MockedStatic;


class UserPlaneTest {

    private UserPlane userPlane;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setup() {
        userPlane = UserPlaneFactory.createUserPlane(); // Create UserPlane

        if (userPlane == null) {
            throw new RuntimeException("UserPlane creation failed!"); // Add debug error if null
        }

        // Add UserPlane to a Scene
        Pane root = new Pane();
        root.getChildren().add(userPlane); // Attach UserPlane to a container
        Scene scene = new Scene(root, 800, 600); // Create Scene

        if (userPlane.getScene() == null) {
            throw new RuntimeException("UserPlane is not attached to a Scene!");
        }
    }

    @Test
    void testUserPlaneMovementVertical() {
        // Arrange: Initial positions
        double initialY = userPlane.getLayoutY();

        // Act: Move up
        userPlane.moveUp();
        userPlane.updatePosition();

        // Assert: Ensure Y-position decreases (moves up)
        assertTrue(userPlane.getTranslateY() < initialY, "UserPlane should move up");

        // Act: Stop vertical movement and update position
        userPlane.stopVerticalMovement();
        double positionAfterStop = userPlane.getTranslateY();
        userPlane.updatePosition();

        // Assert: Ensure it stops moving
        assertEquals(positionAfterStop, userPlane.getTranslateY(), "UserPlane should stop moving vertically");
    }

    @Test
    void testUserPlaneMovementHorizontal() {
        // Arrange: Initial positions
        double initialX = userPlane.getLayoutX();

        // Act: Move right
        userPlane.moveRight();
        userPlane.updatePosition();

        // Assert: Ensure X-position increases (moves right)
        assertTrue(userPlane.getTranslateX() > initialX, "UserPlane should move right");

        // Act: Move left
        userPlane.moveLeft();
        userPlane.updatePosition();

        // Assert: Ensure X-position decreases (moves left)
        assertTrue(userPlane.getTranslateX() < initialX, "UserPlane should move left");
    }

    @Test
    void testUserPlaneTakeDamage() {
        // Arrange: Initial health
        int initialHealth = userPlane.getHealth();

        // Act: Take damage
        userPlane.takeDamage();

        // Assert: Ensure health decreases
        assertEquals(initialHealth - 1, userPlane.getHealth(), "Health should decrease by 1 after taking damage");

        // Act: Add health
        userPlane.addHealth(10);

        // Assert: Ensure health increases
        assertEquals(initialHealth + 9, userPlane.getHealth(), "Health should increase by 10");
    }
    @Test
    void testUserPlaneFireProjectile() {
        try (
                MockedStatic<BulletSystemManager> bulletSystemMock = mockStatic(BulletSystemManager.class);
                MockedStatic<ProjectileFactory> projectileFactoryMock = mockStatic(ProjectileFactory.class);
                MockedStatic<SoundManager> soundManagerMock = mockStatic(SoundManager.class)
        ) {
            // Mock BulletSystemManager
            BulletSystemManager bulletSystemManager = mock(BulletSystemManager.class);
            bulletSystemMock.when(BulletSystemManager::getInstance).thenReturn(bulletSystemManager);
            when(bulletSystemManager.subtractBullets(1)).thenReturn(true);
            // Mock SoundManager
            SoundManager soundManager = mock(SoundManager.class);
            soundManagerMock.when(SoundManager::getInstance).thenReturn(soundManager);

            ActiveActor stubProjectile = new ActiveActor("dummy.jpg", 10, 0, 0) {
                @Override
                public void updatePosition() {}
                @Override
                public void updateActor() {}
            };
            projectileFactoryMock.when(() -> ProjectileFactory.createProjectile(anyString(), anyDouble(), anyDouble(), any()))
                    .thenReturn(stubProjectile);

            //verify Fire projectile
            ActiveActor projectile = userPlane.fireProjectile();

            //  Verify projectile creation and sound effect
            assertNotNull(projectile, "ProjectileParent should be created if bullets are available");
            verify(soundManager, times(1)).playSoundEffect(anyString());

            // verify Simulate no bullets available
            when(bulletSystemManager.subtractBullets(1)).thenReturn(false);
            projectile = userPlane.fireProjectile();

            //  Verify no projectile is created when bullets are unavailable
            assertNull(projectile, "No projectile should be created if bullets are unavailable");
        }
    }

}
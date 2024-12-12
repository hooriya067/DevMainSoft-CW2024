package com.example.demo.actors.active.Factories;

import com.example.demo.actors.user.UserPlane;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UserPlaneFactoryTest {
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }


    @Test
    void testSetImageName() {
        // Arrange: Set a new image name
        UserPlaneFactory.setImageName("userplane2.png");

        // Act: Create a user plane
        UserPlane userPlane = UserPlaneFactory.getUserPlane();

        // Assert: Verify the image name and corresponding height
        assertEquals("userplane2.png", userPlane.getImageName(), "Image name should be 'userplane2.png'");
        assertEquals(120, userPlane.getFitHeight(), "Image height should correspond to 'userplane2.png'");
    }

    @Test
    void testSetInitialHealth() {
        // Arrange: Set a new health value
        UserPlaneFactory.setInitialHealth(150);

        // Act: Create a user plane
        UserPlane userPlane = UserPlaneFactory.getUserPlane();

        // Assert: Verify the health value
        assertEquals(150, userPlane.getHealth(), "Health should be set to 150");
    }

}


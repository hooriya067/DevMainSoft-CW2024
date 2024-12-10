package com.example.demo.actors.collectibles;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class HeartDisplayTest  {

    private HeartDisplay heartDisplay;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setUp() {
        // Initialize HeartDisplay with 3 hearts at position (100, 100)
        heartDisplay = new HeartDisplay(100, 100, 3);
    }

    @Test
    void testInitializeContainerPosition() {
        // Assert: Verify the container's initial position
        HBox container = heartDisplay.getContainer();
        assertEquals(100, container.getLayoutX(), "Container X position should be 100.");
        assertEquals(100, container.getLayoutY(), "Container Y position should be 100.");
    }

    @Test
    void testInitializeHearts() {
        // Assert: Verify the correct number of hearts are displayed initially
        HBox container = heartDisplay.getContainer();
        assertEquals(3, container.getChildren().size(), "Container should initially display 3 hearts.");
        assertTrue(container.getChildren().get(0) instanceof ImageView, "Each child should be an instance of ImageView.");
    }

    @Test
    void testRemoveHeart() {
        // Act: Remove a heart
        heartDisplay.removeHeart();

        // Assert: Verify the number of hearts decreases
        HBox container = heartDisplay.getContainer();
        assertEquals(2, container.getChildren().size(), "Container should display 2 hearts after removing one.");

        // Act: Remove all hearts
        heartDisplay.removeHeart();
        heartDisplay.removeHeart();
        heartDisplay.removeHeart(); // Removing more than available hearts

        // Assert: Ensure no error and container is empty
        assertEquals(0, container.getChildren().size(), "Container should be empty after removing all hearts.");
    }

    @Test
    void testAddHeart() {
        // Act: Add a heart
        heartDisplay.addHeart();

        // Assert: Verify the number of hearts increases
        HBox container = heartDisplay.getContainer();
        assertEquals(4, container.getChildren().size(), "Container should display 4 hearts after adding one.");

        // Assert: Verify the added heart is an ImageView
        assertTrue(container.getChildren().get(3) instanceof ImageView, "The added heart should be an instance of ImageView.");
    }
}

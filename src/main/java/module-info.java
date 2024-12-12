/**
 * The {@code  module-info} file defines the module structure for the `com.example.demo` project.
 * It specifies the required modules, exports, and opens directives, ensuring proper encapsulation
 * and accessibility for the JavaFX application.
 *
 * <p><b>Module Description:</b></p>
 * <ul>
 *     <li>Requires essential modules for JavaFX and other libraries.</li>
 *     <li>Exports specific packages for external use, such as controllers.</li>
 *     <li>Opens packages to the JavaFX framework for reflection-based operations like FXML loading.</li>
 * </ul>
 */
module com.example.demo {
    /**
     * Requires the JavaFX Controls module for UI components like buttons, labels, and layouts.
     */
    requires javafx.controls;

    /**
     * Requires the JavaFX FXML module for loading FXML files and enabling UI layouts defined in XML.
     */
    requires javafx.fxml;

    /**
     * Requires the Java Desktop module for AWT-based utilities like images and sound.
     */
    requires java.desktop;

    /**
     * Requires the JavaFX Media module for playing media files, including background music and sound effects.
     */
    requires javafx.media;

    /**
     * Opens the `com.example.demo` package to the JavaFX framework for reflective access to its classes.
     */
    opens com.example.demo to javafx.fxml;

    /**
     * Exports the `com.example.demo.controller` package for external use, enabling interaction with controllers.
     */
    exports com.example.demo.controller;

    /**
     * Opens the `com.example.demo.core` package to the JavaFX framework for FXML-related operations.
     */
    opens com.example.demo.core to javafx.fxml;

    /**
     * Opens the `com.example.demo.actors.active` package for reflective access to active game actors.
     */
    opens com.example.demo.actors.active to javafx.fxml;

    /**
     * Opens the `com.example.demo.actors.active.destructible` package for FXML processing of destructible actors.
     */
    opens com.example.demo.actors.active.destructible to javafx.fxml;

    /**
     * Opens the `com.example.demo.actors.active.enemies` package for FXML processing of enemy actors.
     */
    opens com.example.demo.actors.active.enemies to javafx.fxml;

    /**
     * Opens the `com.example.demo.actors.active.projectiles` package for FXML processing of projectile actors.
     */
    opens com.example.demo.actors.active.projectiles to javafx.fxml;

    /**
     * Opens the `com.example.demo.actors.user` package for FXML processing of user-related actors.
     */
    opens com.example.demo.actors.user to javafx.fxml;

    /**
     * Opens the `com.example.demo.Levels` package for reflective access to level-related classes.
     */
    opens com.example.demo.Levels to javafx.fxml;

    /**
     * Opens the `com.example.demo.Levels.view` package for FXML processing of level-specific views.
     */
    opens com.example.demo.Levels.view to javafx.fxml;

    /**
     * Opens the `com.example.demo.UI.menu` package for FXML processing of UI menu components.
     */
    opens com.example.demo.UI.menu to javafx.fxml;

    /**
     * Opens the `com.example.demo.UI.buttons` package for FXML processing of UI button components.
     */
    opens com.example.demo.UI.buttons to javafx.fxml;

    /**
     * Opens the `com.example.demo.UI.screens` package for FXML processing of UI screen components.
     */
    opens com.example.demo.UI.screens to javafx.fxml;

    /**
     * Opens the `com.example.demo.Managers` package for FXML processing of manager classes.
     */
    opens com.example.demo.Managers to javafx.fxml;

    /**
     * Opens the `com.example.demo.actors.collectibles` package for FXML processing of collectible actors.
     */
    opens com.example.demo.actors.collectibles to javafx.fxml;

    /**
     * Opens the `com.example.demo.actors.active.Factories` package for FXML processing of actor factory classes.
     */
    opens com.example.demo.actors.active.Factories to javafx.fxml;
}

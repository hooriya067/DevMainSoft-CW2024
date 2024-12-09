module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.core to javafx.fxml;
    opens com.example.demo.actors.active to javafx.fxml;
    opens com.example.demo.actors.active.destructible to javafx.fxml;
    opens com.example.demo.actors.active.enemies to javafx.fxml;
    opens com.example.demo.actors.active.projectiles to javafx.fxml;
    opens com.example.demo.actors.user to javafx.fxml;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.Levels.view to javafx.fxml;
    opens com.example.demo.UI.menu to javafx.fxml;
    opens com.example.demo.UI.buttons to javafx.fxml;
    opens com.example.demo.UI.screens to javafx.fxml;
    opens com.example.demo.Managers to javafx.fxml;
    opens com.example.demo.actors.collectibles to javafx.fxml;
    opens com.example.demo.actors.active.Factories to javafx.fxml;

}
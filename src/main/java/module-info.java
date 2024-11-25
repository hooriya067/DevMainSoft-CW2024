module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.whatever to javafx.fxml;
}
module main.hotel.hotelalura {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports main.hotel.hotelalura;
    exports main.hotel.hotelalura.model;
    exports main.hotel.hotelalura.utils;
    exports main.hotel.hotelalura.viewController;
    exports main.hotel.hotelalura.controller;
    opens main.hotel.hotelalura.viewController to javafx.fxml;
    opens main.hotel.hotelalura.model to javafx.base;
    opens main.hotel.hotelalura to javafx.fxml;
}
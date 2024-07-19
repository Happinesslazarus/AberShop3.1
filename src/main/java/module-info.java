module com.example.abershop {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens com.example.abershop to javafx.fxml;
    exports com.example.abershop;
    exports com.example.abershop.controllers;
    exports com.example.abershop.models;
    exports com.example.abershop.views;
    exports com.example.abershop.core;
    exports com.example.abershop.controllers.employee;
    exports com.example.abershop.controllers.manager;
    exports com.example.abershop.controllers.dashboard;
}
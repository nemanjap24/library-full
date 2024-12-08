module com.polo.libraryui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports com.polo.libraryui;
    exports com.polo.libraryui.model;
    exports com.polo.libraryui.view;
    exports com.polo.libraryui.controller;
    exports com.polo.libraryui.util;

    opens com.polo.libraryui.dto to com.fasterxml.jackson.databind;
    opens com.polo.libraryui.model to com.fasterxml.jackson.databind;
}
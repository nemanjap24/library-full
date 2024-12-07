package com.polo.libraryui;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginScene {
    public static Scene createLoginScene() {
        VBox loginForm = new VBox(15);
        loginForm.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: #f9f9f9; -fx-border-radius: 10px; -fx-border-color: #dddddd; -fx-border-width: 2;");

        // Title Label
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Input fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 5;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 5;");

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-family: 'Verdana';");

        // Add elements to layout
        loginForm.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton);

        // Return Scene
        return new Scene(loginForm, 300, 250);
    }
}

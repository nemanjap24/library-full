package com.polo.libraryui;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScene {
    public static Scene createLoginScene(Stage stage, Scene previousScene) {
        VBox loginForm = new VBox(20);
        loginForm.getStyleClass().add("root");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");

        Button backButton = BackButtonUtil.createBackButton(stage, previousScene, "Back");
        loginForm.getChildren().addAll(usernameField, passwordField, loginButton, backButton);

        loginForm.setStyle("-fx-alignment: center");

        Scene loginScene = new Scene(loginForm, 400, 300);
        loginScene.getStylesheets().add(LoginScene.class.getResource("styles.css").toExternalForm());

        return loginScene;
    }
}
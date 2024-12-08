package com.polo.libraryui.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {
    private VBox root;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button backButton;

    public LoginView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("form-container");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");
        backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");

        root.getChildren().addAll(usernameField, passwordField, loginButton, backButton);
    }

    public Parent getView() { return root; }
    public TextField getUsernameField() { return usernameField; }
    public PasswordField getPasswordField() { return passwordField; }
    public Button getLoginButton() { return loginButton; }
    public Button getBackButton() { return backButton; }
}
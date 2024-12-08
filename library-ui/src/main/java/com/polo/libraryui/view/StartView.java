package com.polo.libraryui.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StartView {
    private VBox root;
    private Button loginButton;
    private Button signupButton;

    public StartView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("start-page");

        loginButton = new Button("Login");
        signupButton = new Button("Signup");

        root.getChildren().addAll(loginButton, signupButton);
    }

    public Parent getView() { return root; }
    public Button getLoginButton() { return loginButton; }
    public Button getSignupButton() { return signupButton; }
}
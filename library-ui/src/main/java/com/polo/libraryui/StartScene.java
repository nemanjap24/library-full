package com.polo.libraryui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScene {

    public static Parent createStartScene(Stage stage){
        VBox startPage = new VBox(10);

        startPage.getStyleClass().add("start-page");
        startPage.setId("start-page");

        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        Button signupButton = new Button("Signup");
        signupButton.setId("signupButton");

        startPage.getChildren().addAll(loginButton, signupButton);
        startPage.setStyle("-fx-alignment: center;");

        return startPage;
    }

}

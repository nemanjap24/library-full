package com.polo.libraryui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterScene {
    public static Scene createRegisterScene(Stage stage, Scene previousScene){
        GridPane signupForm = new GridPane();
        signupForm.setId("form-container");
        signupForm.setVgap(10);
        signupForm.setHgap(10);

        signupForm.add(new Label("Username:"), 0, 0);
        signupForm.add(new TextField(), 1, 0);
        signupForm.add(new Label("Email:"), 0, 1);
        signupForm.add(new TextField(), 1, 1);
        signupForm.add(new Label("Password:"), 0, 2);
        signupForm.add(new PasswordField(), 1, 2);
        signupForm.add(new Label("Re-enter Password:"), 0, 3);
        signupForm.add(new PasswordField(), 1, 3);

        Button registerButton = new Button("Register");
        registerButton.setId("registerButton");
        signupForm.add(registerButton, 1, 4);

        Button backButton = BackButtonUtil.createBackButton(stage, previousScene, "Back");
        backButton.setId("backButton");
        signupForm.add(backButton, 0, 4);

        signupForm.setStyle("-fx-alignment: center;");

        Scene scene2 = new Scene(signupForm, 800, 600);
        scene2.getStylesheets().add(RegisterScene.class.getResource("styles.css").toExternalForm());
        return scene2;

    }
}

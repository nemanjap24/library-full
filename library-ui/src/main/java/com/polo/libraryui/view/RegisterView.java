package com.polo.libraryui.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RegisterView {
    private VBox root;
    private TextField usernameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Button registerButton;
    private Button backButton;

    public RegisterView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("form-container");

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        formGrid.add(new TextField("Username:"), 0, 0);
        formGrid.add(usernameField, 1, 0);
        formGrid.add(new TextField("Email:"), 0, 1);
        formGrid.add(emailField, 1, 1);
        formGrid.add(new TextField("Password:"), 0, 2);
        formGrid.add(passwordField, 1, 2);
        formGrid.add(new TextField("Confirm Password:"), 0, 3);
        formGrid.add(confirmPasswordField, 1, 3);

        registerButton = new Button("Register");
        backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");

        root.getChildren().addAll(formGrid, registerButton, backButton);
    }

    public Parent getView() { return root; }
    public TextField getUsernameField() { return usernameField; }
    public TextField getEmailField() { return emailField; }
    public PasswordField getPasswordField() { return passwordField; }
    public PasswordField getConfirmPasswordField() { return confirmPasswordField; }
    public Button getRegisterButton() { return registerButton; }
    public Button getBackButton() { return backButton; }
}
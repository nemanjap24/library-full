package com.polo.libraryui.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterBookFormView {
    private GridPane root;
    private TextField titleField;
    private TextField authorField;
    private TextField isbnField;
    private TextField copiesField;
    private Button submitButton;

    public RegisterBookFormView() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(10);
        root.setHgap(10);

        titleField = new TextField();
        titleField.setPromptText("Book Title");

        authorField = new TextField();
        authorField.setPromptText("Author");

        isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        copiesField = new TextField();
        copiesField.setPromptText("Number of Copies");

        titleField.getStyleClass().add("login-field");
        authorField.getStyleClass().add("login-field");
        isbnField.getStyleClass().add("login-field");
        copiesField.getStyleClass().add("login-field");

        submitButton = new Button("Register");
        submitButton.getStyleClass().add("button-register");

        root.add(new Label("Title:"), 0, 0);
        root.add(titleField, 1, 0);
        root.add(new Label("Author:"), 0, 1);
        root.add(authorField, 1, 1);
        root.add(new Label("ISBN:"), 0, 2);
        root.add(isbnField, 1, 2);
        root.add(new Label("Copies:"), 0, 3);
        root.add(copiesField, 1, 3);
        root.add(submitButton, 1, 4);

        titleField.getStyleClass().add("login-field");
        authorField.getStyleClass().add("login-field");
        isbnField.getStyleClass().add("login-field");
        copiesField.getStyleClass().add("login-field");

        //root.getStyleClass().add("form-container");
        root.getStyleClass().add("root");

    }

    public Parent getView() { return root; }
    public TextField getTitleField() { return titleField; }
    public TextField getAuthorField() { return authorField; }
    public TextField getIsbnField() { return isbnField; }
    public TextField getCopiesField() { return copiesField; }
    public Button getSubmitButton() { return submitButton; }
}
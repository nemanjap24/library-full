package com.polo.libraryui.view;

import com.polo.libraryui.model.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UpdateBookFormView {
    private VBox root;
    private TextField titleField;
    private TextField authorField;
    private TextField isbnField;
    private TextField copiesField;
    private Button submitButton;
    private Button backButton;

    public UpdateBookFormView(Book book) {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        titleField = new TextField(book.getTitle());
        titleField.setPromptText("Title");

        authorField = new TextField(book.getAuthor());
        authorField.setPromptText("Author");

        isbnField = new TextField(book.getIsbn());
        isbnField.setPromptText("ISBN");

        copiesField = new TextField(String.valueOf(book.getAvailableCopies()));
        copiesField.setPromptText("Copies");

        submitButton = new Button("Update");
        backButton = new Button("Back");

        formGrid.add(new Label("Title:"), 0, 0);
        formGrid.add(titleField, 1, 0);
        formGrid.add(new Label("Author:"), 0, 1);
        formGrid.add(authorField, 1, 1);
        formGrid.add(new Label("ISBN:"), 0, 2);
        formGrid.add(isbnField, 1, 2);
        formGrid.add(new Label("Copies:"), 0, 3);
        formGrid.add(copiesField, 1, 3);

        root.getChildren().addAll(formGrid, submitButton, backButton);
    }

    public Parent getView() {
        return root;
    }

    public TextField getTitleField() { return titleField; }
    public TextField getAuthorField() { return authorField; }
    public TextField getIsbnField() { return isbnField; }
    public TextField getCopiesField() { return copiesField; }
    public Button getSubmitButton() { return submitButton; }
    public Button getBackButton() { return backButton; }
}
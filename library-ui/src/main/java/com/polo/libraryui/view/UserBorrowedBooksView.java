package com.polo.libraryui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class UserBorrowedBooksView {
    private VBox root;
    private ListView<String> borrowedBooksListView;
    private Button returnBookButton;
    private Button backButton;

    public UserBorrowedBooksView() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        borrowedBooksListView = new ListView<>();
        borrowedBooksListView.setPrefSize(600, 400);

        returnBookButton = new Button("Return Selected Book");
        returnBookButton.getStyleClass().add("button-remove");

        backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");

        root.getChildren().addAll(borrowedBooksListView, returnBookButton, backButton);
    }

    public Parent getView() { return root; }
    public ListView<String> getBorrowedBooksListView() { return borrowedBooksListView; }
    public Button getReturnBookButton() { return returnBookButton; }
    public Button getBackButton() { return backButton; }
}
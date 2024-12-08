package com.polo.libraryui.view;

import com.polo.libraryui.model.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class UserDashboardView {
    private VBox root;
    private ListView<String> availableBooksListView;
    private ListView<String> borrowedBooksListView;
    private Button borrowBookButton;
    private Button returnBookButton;
    private Button registerBookButton;
    private Button refreshButton;
    private Button logoutButton;
    private Text availableBooksLabel;
    private Text borrowedBooksLabel;

    public UserDashboardView(List<Book> books) {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        // Available Books Section
        availableBooksLabel = new Text("Available Books");
        availableBooksLabel.getStyleClass().add("section-header");
        availableBooksListView = new ListView<>();
        availableBooksListView.setPrefHeight(200);
        availableBooksListView.getStyleClass().add("book-list");

        // Borrowed Books Section
        borrowedBooksLabel = new Text("Your Borrowed Books");
        borrowedBooksLabel.getStyleClass().add("section-header");
        borrowedBooksListView = new ListView<>();
        borrowedBooksListView.setPrefHeight(200);
        borrowedBooksListView.getStyleClass().add("book-list");

        // Buttons
        borrowBookButton = new Button("Borrow Selected Book");
        borrowBookButton.getStyleClass().add("action-button");

        returnBookButton = new Button("Return Selected Book");
        returnBookButton.getStyleClass().add("action-button");

        registerBookButton = new Button("Register New Book");
        registerBookButton.getStyleClass().add("action-button");

        refreshButton = new Button("Refresh");
        refreshButton.getStyleClass().add("refresh-button");

        logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("logout-button");

        // Action Buttons Container
        HBox actionButtons = new HBox(10);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.getChildren().addAll(borrowBookButton, returnBookButton, registerBookButton, refreshButton);

        // Add all components to root
        root.getChildren().addAll(
                availableBooksLabel,
                availableBooksListView,
                borrowedBooksLabel,
                borrowedBooksListView,
                actionButtons,
                logoutButton
        );

        refreshBookLists(books);
    }

    public void refreshBookLists(List<Book> books) {
        availableBooksListView.getItems().clear();
        borrowedBooksListView.getItems().clear();

        for (Book book : books) {
            String bookInfo = String.format("ID: %d - %s by %s (Available: %d)",
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getAvailableCopies());
            if (book.getAvailableCopies() > 0) {
                availableBooksListView.getItems().add(bookInfo);
            }
        }
    }

    // Getters
    public Parent getView() { return root; }
    public ListView<String> getAvailableBooksListView() { return availableBooksListView; }
    public ListView<String> getBorrowedBooksListView() { return borrowedBooksListView; }
    public Button getBorrowBookButton() { return borrowBookButton; }
    public Button getReturnBookButton() { return returnBookButton; }
    public Button getRegisterBookButton() { return registerBookButton; }
    public Button getRefreshButton() { return refreshButton; }
    public Button getLogoutButton() { return logoutButton; }
}
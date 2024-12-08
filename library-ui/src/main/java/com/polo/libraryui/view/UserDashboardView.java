package com.polo.libraryui.view;

import com.polo.libraryui.model.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class UserDashboardView {
    private VBox root;
    private ListView<String> bookListView;
    private Button lendBookButton;
    private Button borrowBookButton;
    private Button logoutButton;
    private Button refreshButton;

    public UserDashboardView(List<Book> books) {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getStyleClass().add("root");

        bookListView = new ListView<>();
        refreshBookList(books);
        bookListView.getStyleClass().add("book-list");
        bookListView.setPrefHeight(400);

        refreshButton = new Button("Refresh Books");
        refreshButton.getStyleClass().add("button-refresh");

        lendBookButton = new Button("Lend a Book");
        borrowBookButton = new Button("Borrow a Book");
        logoutButton = new Button("Logout");

        HBox actionButtons = new HBox(20);
        actionButtons.getChildren().addAll(borrowBookButton, lendBookButton, refreshButton);
        actionButtons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(bookListView, actionButtons, logoutButton);
        VBox.setMargin(logoutButton, new Insets(10, 0, 20, 0));
    }

    public Button getRefreshButton() {
        return refreshButton;
    }
    public void refreshBookList(List<Book> books) {
        bookListView.getItems().clear();
        for (Book book : books) {
            String bookInfo = String.format("ID: %d - %s by %s (Available: %d)",
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getAvailableCopies());
            bookListView.getItems().add(bookInfo);
        }
    }

    public Parent getView() {
        return root;
    }

    public Button getLendBookButton() {
        return lendBookButton;
    }

    public Button getBorrowBookButton() {
        return borrowBookButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}
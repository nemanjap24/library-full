package com.polo.libraryui.view;

import com.polo.libraryui.model.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;

public class UserDashboardView {
    private VBox root;
    private ListView<String> bookListView;
    private Button lendBookButton;
    private Button borrowBookButton;
    private Button logoutButton;

    public UserDashboardView(List<Book> books) {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        bookListView = new ListView<>();
        for (Book book : books) {
            String bookInfo = String.format("ID: %d - %s by %s (Available: %d)",
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getAvailableCopies());
            bookListView.getItems().add(bookInfo);
        }

        lendBookButton = new Button("Lend a Book");
        borrowBookButton = new Button("Borrow a Book");
        logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("back-button");

        root.getChildren().addAll(bookListView, lendBookButton, borrowBookButton, logoutButton);
    }

    public Parent getView() { return root; }
    public Button getLendBookButton() { return lendBookButton; }
    public Button getBorrowBookButton() { return borrowBookButton; }
    public Button getLogoutButton() { return logoutButton; }
}
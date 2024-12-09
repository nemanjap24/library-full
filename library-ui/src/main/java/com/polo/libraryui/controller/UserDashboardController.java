package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;
import com.polo.libraryui.service.BookService;
import com.polo.libraryui.service.TransactionService;
import com.polo.libraryui.view.UserDashboardView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserDashboardController {
    private UserDashboardView view;
    private Stage stage;
    private SceneManager sceneManager;
    private User currentUser;
    private BookService bookService;
    private TransactionService transactionService;
    private List<Book> books;

    public UserDashboardController(Stage stage, SceneManager sceneManager, List<Book> books, User user) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.books = books;
        this.currentUser = user;
        this.bookService = new BookService();
        this.transactionService = new TransactionService();
        this.view = new UserDashboardView(books);
        initialize();
    }

    private void initialize() {
        // Set up button handlers
        view.getRefreshButton().setOnAction(e -> handleRefresh());
        view.getBorrowBookButton().setOnAction(e -> handleBorrowBook());
        view.getReturnBookButton().setOnAction(e -> handleReturnBook());
        view.getRegisterBookButton().setOnAction(e -> sceneManager.showRegisterBookFormScene());
        view.getLogoutButton().setOnAction(e -> sceneManager.showLoginScene());
        view.getRegisterBookButton().setOnAction(e -> sceneManager.showRegisterBookFormScene());

        handleRefresh();
    }

    private void handleBorrowBook() {
        String selectedItem = view.getAvailableBooksListView().getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a book to borrow");
            return;
        }

        Book selectedBook = getBookFromSelection(selectedItem);
        if (selectedBook != null) {
            transactionService.borrowBook(currentUser, selectedBook.getBookId())
                    .thenRun(this::handleRefresh)
                    .exceptionally(ex -> {
                        Platform.runLater(() -> showAlert("Error", ex.getMessage()));
                        return null;
                    });
        }
    }

    private void handleReturnBook() {
        String selectedItem = view.getBorrowedBooksListView().getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a book to return");
            return;
        }

        Book selectedBook = getBookFromSelection(selectedItem);
        if (selectedBook != null) {
            transactionService.returnBook(currentUser, selectedBook.getBookId())
                    .thenRun(this::handleRefresh)
                    .exceptionally(ex -> {
                        Platform.runLater(() -> showAlert("Error", ex.getMessage()));
                        return null;
                    });
        }
    }

    private Book getBookFromSelection(String selection) {
        try {
            String idStr = selection.substring(4, selection.indexOf(" -"));
            long bookId = Long.parseLong(idStr);
            return books.stream()
                    .filter(book -> book.getBookId() == bookId)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            showAlert("Error", "Could not parse book selection");
            return null;
        }
    }

    private void handleRefresh() {
        if (currentUser == null || currentUser.getToken() == null) {
            sceneManager.showLoginScene();
            return;
        }

        view.getRefreshButton().setDisable(true);

        CompletableFuture<List<Book>> allBooksFuture = bookService.getAllBooks(currentUser);
        CompletableFuture<List<Book>> borrowedBooksFuture = bookService.getBorrowedBooks(currentUser);

        CompletableFuture.allOf(allBooksFuture, borrowedBooksFuture)
                .thenRun(() -> Platform.runLater(() -> {
                    try {
                        List<Book> allBooks = allBooksFuture.get();
                        List<Book> borrowedBooks = borrowedBooksFuture.get();

                        this.books = allBooks;
                        updateBookLists(allBooks, borrowedBooks);
                        view.getRefreshButton().setDisable(false);
                    } catch (Exception e) {
                        showAlert("Error", "Failed to refresh books: " + e.getMessage());
                        view.getRefreshButton().setDisable(false);
                    }
                }))
                .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        view.getRefreshButton().setDisable(false);
                        showAlert("Error", "Failed to refresh books: " + ex.getMessage());
                    });
                    return null;
                });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateBooks(List<Book> newBooks) {
        this.books = newBooks;
        view.refreshBookLists(newBooks);
    }
    private void updateBookLists(List<Book> allBooks, List<Book> borrowedBooks) {
        view.getAvailableBooksListView().getItems().clear();
        view.getBorrowedBooksListView().getItems().clear();

        // Update available books
        for (Book book : allBooks) {
            if (book.getAvailableCopies() > 0) {
                String bookInfo = String.format("ID: %d - %s by %s (Available: %d)",
                        book.getBookId(), book.getTitle(), book.getAuthor(), book.getAvailableCopies());
                view.getAvailableBooksListView().getItems().add(bookInfo);
            }
        }

        // Update borrowed books
        for (Book book : borrowedBooks) {
            String bookInfo = String.format("ID: %d - %s by %s",
                    book.getBookId(), book.getTitle(), book.getAuthor());
            view.getBorrowedBooksListView().getItems().add(bookInfo);
        }
    }

    public Parent getView() { return view.getView(); }
}
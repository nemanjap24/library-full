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

public class UserDashboardController {
    private UserDashboardView view;
    private Stage stage;
    private SceneManager sceneManager;
    private User currentUser;
    private BookService bookService;
    private final TransactionService transactionService;
    private List<Book> books;

    public UserDashboardController(Stage stage, SceneManager sceneManager, List<Book> books, User user) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.books = books;
        this.currentUser = user;
        this.bookService = new BookService();
        this.view = new UserDashboardView(books);
        this.transactionService = new TransactionService();
        initialize();
    }

    private void initialize() {

        view.getRefreshButton().setOnAction(e -> handleRefresh());
        view.getLendBookButton().setOnAction(e -> {
            // Handle lend book logic
        });
        view.getBorrowBookButton().setOnAction(e -> {
            // Handle borrow book logic
        });
        view.getLogoutButton().setOnAction(e -> sceneManager.showLoginScene());

        view.getBorrowBookButton().setOnAction(e -> handleBorrowBook());
        view.getLendBookButton().setOnAction(e -> handleReturnBook());
    }
    private void handleBorrowBook() {
        Book selectedBook = getSelectedBook();
        if (selectedBook == null) return;
        transactionService.borrowBook(currentUser, selectedBook.getBookId())
                .thenRun(this::handleRefresh)
                .exceptionally(ex -> {
                    Platform.runLater(() -> showAlert("Error", ex.getMessage()));
                    System.out.println("handleBorrowBook() failed");
                    return null;
                });
        System.out.println("handleBorrowBook()");

    }

    private void handleReturnBook() {
        Book selectedBook = getSelectedBook();
        if(selectedBook == null) return;

        transactionService.returnBook(currentUser, selectedBook.getBookId())
                .thenRun(this::handleRefresh)
                .exceptionally(ex -> {
                    Platform.runLater(() -> showAlert("Error", ex.getMessage()));
                    return null;
                });
    }
    private Book getSelectedBook() {
        String selectedItem = view.getBookListView().getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a book first");
            return null;
        }

        try {
            // Parse ID from the display string format: "ID: %d - %s by %s (Available: %d)"
            String idStr = selectedItem.substring(4, selectedItem.indexOf(" -"));
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
            // Handle error - redirect to login
            sceneManager.showLoginScene();
            return;
        }
        System.out.println("HandleRefresh()");

        view.getRefreshButton().setDisable(true);
        bookService.getAllBooks(currentUser)
                .thenAccept(newBooks -> Platform.runLater(() -> {
                    view.refreshBookList(newBooks);
                    view.getRefreshButton().setDisable(false);
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
        view.refreshBookList(newBooks);
    }

    public Parent getView() { return view.getView(); }
}
package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;
import com.polo.libraryui.service.BookService;
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
    private List<Book> books;

    public UserDashboardController(Stage stage, SceneManager sceneManager, List<Book> books, User user) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.books = books;
        this.currentUser = user;
        this.bookService = new BookService();
        this.view = new UserDashboardView(books);
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
    }
    private void handleRefresh() {
        if (currentUser == null || currentUser.getToken() == null) {
            // Handle error - redirect to login
            sceneManager.showLoginScene();
            return;
        }

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
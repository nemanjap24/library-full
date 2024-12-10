package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;
import com.polo.libraryui.service.TransactionService;
import com.polo.libraryui.view.UserBorrowedBooksView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;

public class UserBorrowedBooksController {
    private UserBorrowedBooksView view;
    private SceneManager sceneManager;
    private TransactionService transactionService;
    private User user;

    public UserBorrowedBooksController(Stage stage, SceneManager sceneManager, User user) {
        this.sceneManager = sceneManager;
        this.user = user;
        this.transactionService = new TransactionService();
        this.view = new UserBorrowedBooksView();
        initialize();
    }

    private void initialize() {
        loadBorrowedBooks();
        view.getReturnBookButton().setOnAction(e -> handleReturnBook());
        view.getBackButton().setOnAction(e -> sceneManager.showManageUsersScene());
    }

    private void loadBorrowedBooks() {
        List<Book> borrowedBooks = user.getBorrowedBooks();
        Platform.runLater(() -> {
            view.getBorrowedBooksListView().getItems().clear();
            for (Book book : borrowedBooks) {
                String bookInfo = String.format("ID: %d - %s by %s",
                        book.getBookId(), book.getTitle(), book.getAuthor());
                view.getBorrowedBooksListView().getItems().add(bookInfo);
            }
        });
    }

    private void handleReturnBook() {
        String selectedItem = view.getBorrowedBooksListView().getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a book to return");
            return;
        }

        // Extract book ID from the selected item
        try {
            String idStr = selectedItem.split(" - ")[1].split(" ")[0];
            long bookId = Long.parseLong(idStr);
            transactionService.returnBook(user, bookId)
                    .thenRun(() -> {
                        Platform.runLater(() -> {
                            showAlert("Success", "Book returned successfully");
                            loadBorrowedBooks();
                        });
                    })
                    .exceptionally(ex -> {
                        Platform.runLater(() -> showAlert("Error", ex.getMessage()));
                        return null;
                    });
        } catch (Exception e) {
            showAlert("Error", "Failed to parse book ID");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getView() {
        return view.getView();
    }
}
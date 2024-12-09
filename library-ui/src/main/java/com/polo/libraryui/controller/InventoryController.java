package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;
import com.polo.libraryui.service.BookService;
import com.polo.libraryui.view.InventoryView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InventoryController {
    private InventoryView view;
    private SceneManager sceneManager;
    private BookService bookService;
    private User currentUser;
    private List<Book> books;

    public InventoryController(Stage stage, SceneManager sceneManager) {
        this.view = new InventoryView();
        this.sceneManager = sceneManager;
        this.currentUser = sceneManager.getCurrentUser();
        this.bookService = new BookService();

        initialize();
    }

    private void initialize() {
        loadBooks();
        view.getAddButton().setOnAction(e -> handleAdd());
        view.getUpdateButton().setOnAction(e -> handleUpdate());
        view.getRemoveButton().setOnAction(e -> handleRemove());
        view.getBackButton().setOnAction(e -> sceneManager.showAdminDashboardScene());
    }

    private void loadBooks() {
        bookService.getAllBooks(currentUser).thenAccept(books -> {
            this.books = books;
            Platform.runLater(this::updateBooksListView);
        }).exceptionally(ex -> {
            Platform.runLater(() -> showAlert("Error", "Failed to load books: " + ex.getMessage()));
            return null;
        });
    }

    private void updateBooksListView() {
        view.getBooksListView().getItems().clear();
        for (Book book : books) {
            String bookInfo = String.format("ID: %d - %s by %s (Available: %d)",
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getAvailableCopies());
            view.getBooksListView().getItems().add(bookInfo);
        }
    }

    private Book getSelectedBook() {
        String selectedItem = view.getBooksListView().getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Warning", "Please select a book.");
            return null;
        }
        try {
            String idPart = selectedItem.split(" - ")[0];
            long bookId = Long.parseLong(idPart.replace("ID: ", ""));
            return books.stream()
                    .filter(book -> book.getBookId() == bookId)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            showAlert("Error", "Failed to parse selected book.");
            return null;
        }
    }

    private void handleAdd() {
        Book selectedBook = getSelectedBook();
        if (selectedBook != null) {
            selectedBook.setAvailableCopies(selectedBook.getAvailableCopies() + 1);
            bookService.updateBook(currentUser, selectedBook).thenRun(() -> {
                Platform.runLater(() -> {
                    showAlert("Success", "Added one copy to the book.");
                    loadBooks();
                });
            }).exceptionally(ex -> {
                Platform.runLater(() -> showAlert("Error", "Failed to update book: " + ex.getMessage()));
                return null;
            });
        }
    }

    private void handleUpdate() {
        Book selectedBook = getSelectedBook();
        if (selectedBook != null) {
            sceneManager.showUpdateBookFormScene(selectedBook);
        }
    }

    private void handleRemove() {
        Book selectedBook = getSelectedBook();
        if (selectedBook != null) {
            bookService.deleteBook(currentUser, selectedBook.getBookId()).thenRun(() -> {
                Platform.runLater(() -> {
                    showAlert("Success", "Book removed.");
                    loadBooks();
                });
            }).exceptionally(ex -> {
                Platform.runLater(() -> showAlert("Error", "Failed to remove book: " + ex.getMessage()));
                return null;
            });
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(sceneManager.getStage());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getView() {
        return view.getView();
    }
}
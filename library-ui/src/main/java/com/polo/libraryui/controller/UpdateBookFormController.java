package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.service.BookService;
import com.polo.libraryui.view.UpdateBookFormView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class UpdateBookFormController {
    private UpdateBookFormView view;
    private SceneManager sceneManager;
    private BookService bookService;
    private Book book;

    public UpdateBookFormController(Stage stage, SceneManager sceneManager, Book book) {
        this.view = new UpdateBookFormView(book);
        this.sceneManager = sceneManager;
        this.bookService = new BookService();
        this.book = book;
        initialize();
    }

    private void initialize() {
        view.getSubmitButton().setOnAction(e -> handleUpdate());
        view.getBackButton().setOnAction(e -> sceneManager.showInventoryScene());
    }

    private void handleUpdate() {
        String title = view.getTitleField().getText();
        String author = view.getAuthorField().getText();
        String isbn = view.getIsbnField().getText();
        String copiesText = view.getCopiesField().getText();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || copiesText.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        int copies;
        try {
            copies = Integer.parseInt(copiesText);
            if (copies < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Error", "Copies must be a non-negative integer.");
            return;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setAvailableCopies(copies);

        bookService.updateBook(sceneManager.getCurrentUser(), book).thenRun(() -> {
            Platform.runLater(() -> {
                showAlert("Success", "Book updated successfully.");
                sceneManager.showInventoryScene();
            });
        }).exceptionally(ex -> {
            Platform.runLater(() -> showAlert("Error", "Failed to update book: " + ex.getMessage()));
            return null;
        });
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
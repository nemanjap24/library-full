package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;
import com.polo.libraryui.service.BookService;
import com.polo.libraryui.view.RegisterBookFormView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

public class RegisterBookFormController {
    private RegisterBookFormView view;
    private Stage stage;
    private SceneManager sceneManager;
    private BookService bookService;
    private User currentUser;

    public RegisterBookFormController(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.view = new RegisterBookFormView();
        this.bookService = new BookService();
        this.currentUser = sceneManager.getCurrentUser();
        initialize();
    }

    private void initialize() {
        view.getSubmitButton().setOnAction(e -> handleSubmit());
        view.getBackButton().setOnAction(e -> {
            if("ADMIN".equalsIgnoreCase(currentUser.getRole())){
                sceneManager.showAdminDashboardScene();
            }else {
                sceneManager.showUserDashboardScene();
            }
        });
    }

    private void handleSubmit() {
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
            if (copies <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            showAlert("Error", "Number of Copies must be a positive integer.");
            return;
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setIsbn(isbn);
        newBook.setAvailableCopies(copies);

        // Send the book to the backend
        CompletableFuture<Void> future = bookService.registerBook(currentUser, newBook)
                .thenRun(() -> Platform.runLater(() -> {
                    showAlert("Success", "Book registered successfully.");
                    if("ADMIN".equalsIgnoreCase(currentUser.getRole())){
                        sceneManager.showAdminDashboardScene();
                    }else {
                        sceneManager.showUserDashboardScene();
                    }

                }))
                .exceptionally(ex -> {
                    Platform.runLater(() -> showAlert("Error", "Failed to register book: " + ex.getMessage()));
                    return null;
                });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getView() { return view.getView(); }
}
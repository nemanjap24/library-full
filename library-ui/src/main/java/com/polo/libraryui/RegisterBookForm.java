package com.polo.libraryui;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class RegisterBookForm {

    public static void show(Stage parentStage, List<BookTest> books, VBox booksInfoBox) {
        Stage registerStage = new Stage();
        registerStage.setTitle("Register a new book");

        // Form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 20;");

        // Form fields
        TextField titleField = new TextField();
        titleField.setPromptText("Book Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        TextField copiesField = new TextField();
        copiesField.setPromptText("Number of Copies");


        Button submitButton = new Button("Register");
        submitButton.setOnAction(e -> {

            if (titleField.getText().isEmpty() || authorField.getText().isEmpty() ||
                    isbnField.getText().isEmpty() || copiesField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are required!");
                alert.show();
                return;
            }

            try {
                int copies = Integer.parseInt(copiesField.getText());
                BookTest newBook = new BookTest(
                        titleField.getText(),
                        authorField.getText(),
                        isbnField.getText(),
                        null,
                        null,
                        copies
                );
                books.add(newBook);
                booksInfoBox.getChildren().clear();
                AdminDashboard.updateBookDisplay(books, booksInfoBox);

                registerStage.close(); // Close the form
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Number of copies must be an integer.");
                alert.show();
            }
        });

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(isbnField, 1, 2);
        grid.add(new Label("Copies:"), 0, 3);
        grid.add(copiesField, 1, 3);
        grid.add(submitButton, 1, 4);

        Scene scene = new Scene(grid, 400, 300);
        scene.getStylesheets().add(RegisterBookForm.class.getResource("styles.css").toExternalForm());
        registerStage.setScene(scene);
        registerStage.initOwner(parentStage);
        registerStage.show();
    }

}

package com.polo.libraryui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AdminDashboard {
    public static VBox createAdminDashboard(Stage stage, Scene previousScene, List<BookTest> books) {
        VBox adminDashboard = new VBox(20);

        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.getStyleClass().add("label-title");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 10 0 10 0");

        Button viewInventoryButton = new Button("View Inventory");
        Button registerBookButton = new Button("Register a New Book");
        Button removeBookButton = new Button("Remove a Book");
        Button logoutButton = BackButtonUtil.createBackButton(stage, previousScene, "Logout");

        viewInventoryButton.setMaxWidth(Double.MAX_VALUE);
        registerBookButton.setMaxWidth(Double.MAX_VALUE);
        removeBookButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        viewInventoryButton.getStyleClass().add("button-view-inventory");
        registerBookButton.getStyleClass().add("button-register");
        removeBookButton.getStyleClass().add("button-remove");
        logoutButton.getStyleClass().add("back-button");

        VBox leftButtonsBox = new VBox(30);
        leftButtonsBox.setAlignment(Pos.TOP_LEFT);
        leftButtonsBox.setStyle("-fx-padding: 10");
        leftButtonsBox.getChildren().addAll(viewInventoryButton, registerBookButton, removeBookButton, logoutButton);


        VBox booksInfoBox = new VBox(10);
        booksInfoBox.setStyle("-fx-padding: 10;");

        ScrollPane booksScrollPane = new ScrollPane(booksInfoBox);
        booksScrollPane.setStyle("-fx-background: #f9f9f9; -fx-border-color: #dddddd;");
        booksScrollPane.setPrefHeight(400);
        booksScrollPane.setPrefWidth(450);
        booksScrollPane.setVisible(false);


        viewInventoryButton.setOnAction(e -> {
            booksScrollPane.setVisible(!booksScrollPane.isVisible());
            viewInventoryButton.setText(booksScrollPane.isVisible() ? "Hide Inventory" : "View Inventory");
        });


        registerBookButton.setOnAction(e -> {
            RegisterBookForm.show(stage, books, booksInfoBox);
        });


        updateBookDisplay(books, booksInfoBox);

        HBox mainLayout = new HBox(30);
        mainLayout.getChildren().addAll(leftButtonsBox, booksScrollPane);

        adminDashboard.getChildren().addAll(titleBox, mainLayout);
        adminDashboard.getStylesheets().add(AdminDashboard.class.getResource("styles.css").toExternalForm());

        return adminDashboard;
    }

    public static void updateBookDisplay(List<BookTest> books, VBox booksInfoBox) {
        booksInfoBox.getChildren().clear();
        for (BookTest book : books) {
            booksInfoBox.getChildren().add(createBookCard(book));
        }
    }

    private static VBox createBookCard(BookTest book) {
        VBox bookCard = new VBox(10);
        bookCard.setStyle(
                "-fx-padding: 15;" +
                        "-fx-border-color: #dddddd;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-color: #ffffff;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);"
        );

        Label titleLabel = new Label("Title: " + book.getTitle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #555555; -fx-font-family: Verdana");

        Label authorLabel = new Label("Author: " + book.getAuthor());
        Label copiesLabel = new Label("Copies: " + book.getCopies());
        authorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");
        copiesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");

        HBox actionButtons = new HBox(25);



        Button addCopyButton = new Button("+1");
        addCopyButton.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 5 10;" +
                        "-fx-border-radius: 5;"
        );
        addCopyButton.setOnAction(e -> {
            book.incrementCopies();
            copiesLabel.setText("Copies: " + book.getCopies());
        });

        Button removeCopyButton = new Button("-1");
        removeCopyButton.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #ee0c0c;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 5 10;" +
                        "-fx-border-radius: 5;"
        );

        removeCopyButton.setOnAction(e -> {
            book.decrementCopies();
            copiesLabel.setText("Copies: " + book.getCopies());
        });

        addCopyButton.setPrefWidth(45);
        removeCopyButton.setPrefWidth(45);
        actionButtons.getChildren().addAll(addCopyButton,removeCopyButton);


        bookCard.getChildren().addAll(titleLabel, authorLabel, copiesLabel, actionButtons);
        return bookCard;
    }
}

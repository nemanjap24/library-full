package com.polo.libraryui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class UserDashboard {
    public static VBox createUserDashboard(List<BookTest> books) {
        VBox userDashboard = new VBox(20); // Add spacing between components
        userDashboard.setStyle("-fx-padding: 15; -fx-background-color: #f9f9f9;");

        // Title
        Label titleLabel = new Label("User's Books");
        titleLabel.getStyleClass().add("label-title");
        //titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Scrollable display for books
        FlowPane bookDisplay = new FlowPane();
        bookDisplay.setHgap(15);
        bookDisplay.setVgap(15);
        bookDisplay.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-color: #dddddd; -fx-border-radius: 5;");

        // Populate the book display with images
        for (BookTest book : books) {
            ImageView bookCover = new ImageView(new Image("file:" + book.getCoverImagePath()));
            bookCover.setFitWidth(100);
            bookCover.setFitHeight(150);
            bookCover.setPreserveRatio(true);

            // Tooltip to show book details on hover
            Tooltip bookTooltip = new Tooltip(
                    "Title: " + book.getTitle() + "\n" +
                            "ISBN: " + book.getIsbn() + "\n" +
                            "Author: " + book.getAuthor() + "\n" +
                            "Lend Date: " + book.getLendDate()
            );
            Tooltip.install(bookCover, bookTooltip);

            // Add the book cover to the display
            bookDisplay.getChildren().add(bookCover);
        }

        // Add the FlowPane to a ScrollPane
        ScrollPane scrollPane = new ScrollPane(bookDisplay);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f9f9f9; -fx-border-color: #dddddd;");

        // Action Buttons Section
        HBox buttonsBox = new HBox(20); // Add spacing between buttons
        buttonsBox.setStyle("-fx-padding: 10;");

        Button lendBookButton = new Button("Lend a Book");

        Button borrowBookButton = new Button("Borrow a Book");


        lendBookButton.setMaxWidth(Double.MAX_VALUE); // Allow button to stretch
        borrowBookButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(lendBookButton, Priority.ALWAYS); // Allow resizing horizontally
        HBox.setHgrow(borrowBookButton, Priority.ALWAYS);

        // Style the buttons
        lendBookButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-family: 'Verdana';");
        borrowBookButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-family: 'Verdana';");

        buttonsBox.getChildren().addAll(lendBookButton, borrowBookButton);

        // Add everything to the dashboard
        userDashboard.getChildren().addAll(titleLabel, scrollPane, buttonsBox);
        return userDashboard;
    }
}

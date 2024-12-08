package com.polo.libraryui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class UserDashboard {
    public static VBox createUserDashboard(List<BookTest> books, Stage stage, Scene previousScene) {
        VBox userDashboard = new VBox(20);

        Label titleLabel = new Label("User's books");
        titleLabel.getStyleClass().add("label-title");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 10 0 0 0");


        FlowPane bookDisplay = new FlowPane();
        bookDisplay.setHgap(10);
        bookDisplay.setVgap(10);
        bookDisplay.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-color: #dddddd; -fx-border-radius: 5;");

        for (BookTest book : books) {
            ImageView bookCover = new ImageView(new Image("file:" + book.getCoverImagePath()));
            bookCover.setFitWidth(100);
            bookCover.setFitHeight(150);
            bookCover.setPreserveRatio(true);

            Tooltip bookTooltip = new Tooltip(
                    "Title: " + book.getTitle() + "\n" +
                            "ISBN: " + book.getIsbn() + "\n" +
                            "Author: " + book.getAuthor() + "\n" +
                            "Lend Date: " + book.getLendDate()
            );
            Tooltip.install(bookCover, bookTooltip);

            bookDisplay.getChildren().add(bookCover);
        }

        ScrollPane scrollPane = new ScrollPane(bookDisplay);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(250);
        scrollPane.setStyle("-fx-background: #f9f9f9; -fx-border-color: #dddddd;");

        Button lendBookButton = new Button("Lend a book");
        Button borrowBookButton = new Button("Borrow a book");
        Button logoutButton = BackButtonUtil.createBackButton(stage, previousScene, "Logout");

        lendBookButton.setMaxWidth(Double.MAX_VALUE);
        borrowBookButton.setMaxWidth(Double.MAX_VALUE);
        lendBookButton.getStyleClass().add("button-lend");
        borrowBookButton.getStyleClass().add("button-borrow");
        logoutButton.getStyleClass().add("back-button");


        HBox activityBox = new HBox(20, lendBookButton, borrowBookButton);
        activityBox.setAlignment(Pos.CENTER);

        HBox logoutBox = new HBox(logoutButton);
        logoutBox.setAlignment(Pos.CENTER);
        logoutBox.setSpacing(5);
        logoutBox.setStyle("-fx-padding: 5 0 15 0;");

        VBox buttonsBox = new VBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(activityBox, logoutBox);


        userDashboard.getChildren().addAll(titleBox, scrollPane, buttonsBox);
        userDashboard.getStylesheets().add(UserDashboard.class.getResource("styles.css").toExternalForm());
        return userDashboard;
    }
}

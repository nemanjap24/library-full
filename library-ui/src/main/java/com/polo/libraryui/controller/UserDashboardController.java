package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.view.UserDashboardView;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.List;

public class UserDashboardController {
    private UserDashboardView view;
    private Stage stage;
    private SceneManager sceneManager;
    private List<Book> books;

    public UserDashboardController(Stage stage, SceneManager sceneManager, List<Book> books) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.books = books;
        this.view = new UserDashboardView(books);
        initialize();
    }

    private void initialize() {
        view.getLendBookButton().setOnAction(e -> {
            // Handle lend book logic
        });
        view.getBorrowBookButton().setOnAction(e -> {
            // Handle borrow book logic
        });
        view.getLogoutButton().setOnAction(e -> sceneManager.showLoginScene());
    }

    public Parent getView() { return view.getView(); }
}
package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.view.AdminDashboardView;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class AdminDashboardController {
    private AdminDashboardView view;
    private Stage stage;
    private SceneManager sceneManager;

    public AdminDashboardController(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.view = new AdminDashboardView();
        initialize();
    }

    private void initialize() {
        view.getViewInventoryButton().setOnAction(e -> {
            // Handle view inventory logic
        });
        view.getRegisterBookButton().setOnAction(e -> sceneManager.showRegisterBookFormScene());
        view.getRemoveBookButton().setOnAction(e -> {
            // Handle remove book logic
        });
        view.getLogoutButton().setOnAction(e -> sceneManager.showLoginScene());
    }

    public Parent getView() { return view.getView(); }
}
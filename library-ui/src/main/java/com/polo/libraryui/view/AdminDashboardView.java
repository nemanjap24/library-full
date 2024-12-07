package com.polo.libraryui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminDashboardView {
    private VBox root;
    private Button viewInventoryButton;
    private Button registerBookButton;
    private Button removeBookButton;
    private Button logoutButton;

    public AdminDashboardView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        viewInventoryButton = new Button("View Inventory");
        registerBookButton = new Button("Register a New Book");
        removeBookButton = new Button("Remove a Book");
        logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("back-button");

        viewInventoryButton.getStyleClass().add("button-view");     // Blue style
        registerBookButton.getStyleClass().add("button-register");  // Green style
        removeBookButton.getStyleClass().add("button-remove");      // Red style


        root.getChildren().addAll(viewInventoryButton, registerBookButton,
                removeBookButton, logoutButton);
    }

    public Parent getView() {
        return root;
    }

    public Button getViewInventoryButton() {
        return viewInventoryButton;
    }

    public Button getRegisterBookButton() {
        return registerBookButton;
    }

    public Button getRemoveBookButton() {
        return removeBookButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}
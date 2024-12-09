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
    private Button manageUsersButton;
    private Button logoutButton;

    public AdminDashboardView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        viewInventoryButton = new Button("View Inventory");
        registerBookButton = new Button("Register a New Book");
        manageUsersButton = new Button("Manage Users");
        logoutButton = new Button("Logout");

        root.getChildren().addAll(
                viewInventoryButton,
                registerBookButton,
                manageUsersButton,
                logoutButton
        );
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

    public Button getManageUsersButton() {
        return manageUsersButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}
package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.User;
import com.polo.libraryui.service.UserService;
import com.polo.libraryui.view.ManageUsersView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;

public class ManageUsersController {
    private ManageUsersView view;
    private SceneManager sceneManager;
    private UserService userService;
    private User currentUser;
    private List<User> users;

    public ManageUsersController(Stage stage, SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.currentUser = sceneManager.getCurrentUser();
        this.userService = new UserService();
        this.view = new ManageUsersView();
        initialize();
    }

    private void initialize() {
        loadUsers();
        view.getDeleteButton().setOnAction(e -> handleDelete());
        view.getUpdateButton().setOnAction(e -> handleUpdate());
        view.getSeeBorrowedButton().setOnAction(e -> handleSeeBorrowed());
        view.getBackButton().setOnAction(e -> sceneManager.showAdminDashboardScene());
    }

    private void loadUsers() {
        userService.getAllUsers(currentUser).thenAccept(users -> {
            this.users = users;
            Platform.runLater(this::updateUsersListView);
        }).exceptionally(ex -> {
            Platform.runLater(() -> showAlert("Error", "Failed to load users: " + ex.getMessage()));
            return null;
        });
    }

    private void updateUsersListView() {
        view.getUsersListView().getItems().clear();
        for (User user : users) {
            String userInfo = String.format("ID: %d - %s (%s)",
                    user.getUserId(), user.getUsername(), user.getRole());
            view.getUsersListView().getItems().add(userInfo);
        }
    }

    private void handleDelete() {
        User selectedUser = getSelectedUser();
        if (selectedUser != null) {
            userService.deleteUser(currentUser, selectedUser.getUserId()).thenRun(() -> {
                Platform.runLater(() -> {
                    showAlert("Success", "User deleted successfully");
                    loadUsers();
                });
            }).exceptionally(ex -> {
                Platform.runLater(() -> showAlert("Error", "Failed to delete user: " + ex.getMessage()));
                return null;
            });
        }
    }

    private void handleUpdate() {
        User selectedUser = getSelectedUser();
        if (selectedUser != null) {
            sceneManager.showUpdateUserScene(selectedUser);
        }
    }

    private void handleSeeBorrowed() {
        User selectedUser = getSelectedUser();
        if (selectedUser != null) {
            sceneManager.showUserBorrowedBooksScene(selectedUser);
        }
    }

    private User getSelectedUser() {
        String selection = view.getUsersListView().getSelectionModel().getSelectedItem();
        if (selection == null) {
            showAlert("Error", "Please select a user");
            return null;
        }

        try {
            String idStr = selection.substring(4, selection.indexOf(" -"));
            long userId = Long.parseLong(idStr);
            return users.stream()
                    .filter(user -> user.getUserId() == userId)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            showAlert("Error", "Could not parse user selection");
            return null;
        }
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
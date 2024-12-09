package com.polo.libraryui;

import com.polo.libraryui.controller.*;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class SceneManager {
    private Stage stage;
    private Scene scene; // Single Scene instance
    private User currentUser;
    private List<Book> books;

    // Controllers
    private StartController startController;
    private LoginController loginController;
    private RegisterController registerController;
    private UserDashboardController userDashboardController;
    private AdminDashboardController adminDashboardController;
    private RegisterBookFormController registerBookFormController;
    private InventoryController inventoryController;
//    private ManageUsersController manageUsersController;
    private UpdateBookFormController updateBookFormController;

    public SceneManager(Stage stage, List<Book> books) {
        this.stage = stage;
        this.books = books;
        initializeControllersAndScene();
    }

    private void initializeControllersAndScene() {
        // Initialize controllers
        startController = new StartController(stage, this);
        loginController = new LoginController(stage, this);
        registerController = new RegisterController(stage, this);
        registerBookFormController = new RegisterBookFormController(stage, this);

        // Create a single Scene without specifying size
        scene = new Scene(startController.getView());

        // Apply stylesheet
        String stylesheet = getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        // Set the scene to the stage
        stage.setScene(scene);
    }
    public User getCurrentUser() {
        return currentUser;
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void showStartScene() {
        scene.setRoot(startController.getView());
    }

    public void showLoginScene() {
        scene.setRoot(loginController.getView());
    }

    public void showRegisterScene() {
        scene.setRoot(registerController.getView());
    }

    public void showUserDashboardScene() {
        if (currentUser == null) {
            showLoginScene();
            return;
        }
        userDashboardController = new UserDashboardController(stage, this, books, currentUser);
        scene.setRoot(userDashboardController.getView());
    }

    public Stage getStage() {
        return stage;
    }

    public void showAdminDashboardScene() {
        if (currentUser == null) {
            showLoginScene();
            return;
        }
        adminDashboardController = new AdminDashboardController(stage, this, currentUser);
        scene.setRoot(adminDashboardController.getView());
    }

    public void showRegisterBookFormScene() {
        registerBookFormController = new RegisterBookFormController(stage, this);
        scene.setRoot(registerBookFormController.getView());
    }

    public void updateBooks(List<Book> newBooks) {
        this.books = newBooks;
        if (userDashboardController != null) {
            userDashboardController.updateBooks(newBooks);
        }
        if (adminDashboardController != null) {
//            adminDashboardController.updateBooks(newBooks);
        }
    }
    public void showInventoryScene() {
        inventoryController = new InventoryController(stage, this);
        scene.setRoot(inventoryController.getView());
    }

//    public void showManageUsersScene() {
//        manageUsersController = new ManageUsersController(stage, this);
//        scene.setRoot(manageUsersController.getView());
//    }
    public void showUpdateBookFormScene(Book book) {
        updateBookFormController = new UpdateBookFormController(stage, this, book);
        scene.setRoot(updateBookFormController.getView());
    }
}
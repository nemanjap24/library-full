package com.polo.libraryui;

import com.polo.libraryui.controller.*;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class SceneManager {
    private Stage stage;
    private Scene startScene;
    private Scene loginScene;
    private Scene registerScene;
    private Scene userDashboardScene;
    private Scene adminDashboardScene;
    private Scene registerBookFormScene;
    private User currentUser;
    private List<Book> books;
    private UserDashboardController userDashboardController;
    private AdminDashboardController adminDashboardController;

    public SceneManager(Stage stage, List<Book> books) {
        this.stage = stage;
        this.books = books;
        initializeScenes();
    }

    private void initializeScenes() {
        // Start Scene
        StartController startController = new StartController(stage, this);
        startScene = new Scene(startController.getView(), 800, 600);

        // Login Scene
        LoginController loginController = new LoginController(stage, this);
        loginScene = new Scene(loginController.getView(), 800, 600);

        // Register Scene
        RegisterController registerController = new RegisterController(stage, this);
        registerScene = new Scene(registerController.getView(), 800, 600);

        // Initialize dashboard scenes without content - will be updated when showing
        userDashboardScene = new Scene(new VBox(), 800, 600);
        adminDashboardScene = new Scene(new VBox(), 800, 600);

        // Register Book Form Scene
        RegisterBookFormController registerBookFormController =
                new RegisterBookFormController(stage, this);
        registerBookFormScene = new Scene(registerBookFormController.getView(), 800, 600);

        // Apply stylesheets
        String stylesheet = getClass().getResource("styles.css").toExternalForm();
        startScene.getStylesheets().add(stylesheet);
        loginScene.getStylesheets().add(stylesheet);
        registerScene.getStylesheets().add(stylesheet);
        userDashboardScene.getStylesheets().add(stylesheet);
        adminDashboardScene.getStylesheets().add(stylesheet);
        registerBookFormScene.getStylesheets().add(stylesheet);
    }

    public void updateBooks(List<Book> newBooks) {
        this.books = newBooks;
        if (stage.getScene() == userDashboardScene && userDashboardController != null) {
            userDashboardController.updateBooks(newBooks);
        } else if (stage.getScene() == adminDashboardScene) {
            // TODO: implement admin dashboard book update if needed
        }
    }
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void showStartScene() { stage.setScene(startScene); }
    public void showLoginScene() { stage.setScene(loginScene); }
    public void showRegisterScene() { stage.setScene(registerScene); }
    public void showUserDashboardScene() {
        if (currentUser == null) {
            System.err.println("No user logged in!");
            showLoginScene();
            return;
        }

        userDashboardController = new UserDashboardController(stage, this, books, currentUser);
        userDashboardScene.setRoot(userDashboardController.getView());
        stage.setScene(userDashboardScene);
    }    public void showAdminDashboardScene() { stage.setScene(adminDashboardScene); }
    public void showRegisterBookFormScene() { stage.setScene(registerBookFormScene); }
}
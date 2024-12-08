package com.polo.libraryui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        Parent root = StartScene.createStartScene(primaryStage);
        Scene startScene = new Scene(root, 800, 600);
        startScene.getStylesheets().add(StartScene.class.getResource("styles.css").toExternalForm());


        Scene registerScene = RegisterScene.createRegisterScene(primaryStage, startScene);
        Scene loginScene = LoginScene.createLoginScene(primaryStage, startScene);

        List<BookTest> books = getBooksList();
        Scene userScene = createUserDashboard(books, primaryStage, loginScene);
        Scene adminScene = createAdminDashboard(primaryStage, loginScene);


        Button loginButton = (Button) root.lookup("#loginButton");
        Button signupButton = (Button) root.lookup("#signupButton");
        Button registerButton = (Button) registerScene.lookup("#registerButton");
        Button loginSuccess = (Button) loginScene.lookup("#loginButton");

        loginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        signupButton.setOnAction(e -> primaryStage.setScene(registerScene));
        registerButton.setOnAction(e -> primaryStage.setScene(loginScene));
        loginSuccess.setOnAction(e -> primaryStage.setScene(adminScene));


        primaryStage.setScene(startScene);
        primaryStage.setTitle("Library App");
        primaryStage.show();
    }

    private List<BookTest> getBooksList() {
        return new ArrayList<>(
                List.of(
                        new BookTest("Book 1", "1234567890", "Author 1", "2024-12-01", "path/to/book1.jpg", 1),
                        new BookTest("Book 2", "2345678901", "Author 2", "2024-11-28", "path/to/book2.jpg", 2),
                        new BookTest("Book 3", "3456789012", "Author 3", "2024-11-25", "path/to/book3.jpg", 3)
                )
        );
    }


    private Scene createUserDashboard(List<BookTest> books, Stage primaryStage, Scene startScene) {
        VBox userDashboard = UserDashboard.createUserDashboard(books, primaryStage, startScene);
        Scene userScene = new Scene(userDashboard, 800, 600);
        userScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        return userScene;
    }

    private Scene createAdminDashboard(Stage primaryStage, Scene startScene) {
        VBox adminDashboard = AdminDashboard.createAdminDashboard(primaryStage, startScene, getBooksList());
        Scene adminScene = new Scene(adminDashboard, 800, 600);
        adminScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        return adminScene;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
package com.polo.libraryui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Parent root = StartScene.createStartScene(primaryStage).getRoot();

        Scene registerScene = RegisterScene.createRegisterScene(primaryStage, root.getScene());
        Scene loginScene = LoginScene.createLoginScene(primaryStage, root.getScene());


        List<BookTest> books = getBooksList();
        Scene userScene = createUserDashboard(books, primaryStage, loginScene);
        Scene adminScene = createAdminDashboard();

        Button loginButton = (Button) root.lookup("#loginButton");
        Button signupButton = (Button) root.lookup("#signupButton");
        Button registerButton = (Button) registerScene.lookup("#registerButton");
        Button loginSuccess = (Button) loginScene.lookup("#loginButton");

        loginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        signupButton.setOnAction(e -> primaryStage.setScene(registerScene));
        registerButton.setOnAction(e -> primaryStage.setScene(loginScene));
        loginSuccess.setOnAction(e -> primaryStage.setScene(userScene));

        primaryStage.setScene(root.getScene());
        primaryStage.setTitle("Library App");
        primaryStage.show();
    }




    private List<BookTest> getBooksList() {
        return List.of(
                new BookTest("Book 1", "1234567890", "Author 1", "2024-12-01", "path/to/book1.jpg"),
                new BookTest("Book 2", "2345678901", "Author 2", "2024-11-28", "path/to/book2.jpg"),
                new BookTest("Book 3", "3456789012", "Author 3", "2024-11-25", "path/to/book3.jpg")
        );
    }

    private Scene createUserDashboard(List<BookTest> books, Stage primaryStage, Scene startScene) {
        VBox userDashboard = UserDashboard.createUserDashboard(books, primaryStage, startScene);
        Scene scene4a = new Scene(userDashboard, 600, 400);
        scene4a.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        return scene4a;
    }

    private Scene createAdminDashboard() {
        VBox adminDashboard = new VBox(10);
        adminDashboard.getChildren().addAll(
                new Label("View Inventory"),
                new Button("Register a New Book"),
                new Button("Remove a Book")
        );
        Scene scene4b = new Scene(adminDashboard, 300, 300);
        scene4b.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        return scene4b;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
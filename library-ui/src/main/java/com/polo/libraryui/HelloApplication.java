package com.polo.libraryui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {

        // Create scenes
        Scene scene1 = createStartPage(primaryStage);
        Scene scene2 = createSignupForm(primaryStage);
        Scene loginScene = LoginScene.createLoginScene();
        List<BookTest> books = getBooksList();
        Scene scene4a = createUserDashboard(books);
        Scene scene4b = createAdminDashboard();

        // Navigation logic
        Button loginButton = (Button) scene1.lookup("#loginButton");
        Button signupButton = (Button) scene1.lookup("#signupButton");
        Button registerButton = (Button) scene2.lookup("#registerButton");

        loginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        signupButton.setOnAction(e -> primaryStage.setScene(scene2));
        registerButton.setOnAction(e -> primaryStage.setScene(loginScene));

        primaryStage.setScene(scene1);
        primaryStage.setTitle("Library App");
        primaryStage.show();
    }

    private Scene createStartPage(Stage primaryStage) {
        VBox startPage = new VBox(10);
        startPage.setId("start-page");
        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        Button signupButton = new Button("Signup");
        signupButton.setId("signupButton");
        startPage.getChildren().addAll(loginButton, signupButton);
        startPage.setStyle("-fx-alignment: center;");
        Scene scene1 = new Scene(startPage, 300, 200);
        scene1.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        return scene1;
    }

    private Scene createSignupForm(Stage primaryStage) {
        GridPane signupForm = new GridPane();
        signupForm.setId("form-container");
        signupForm.setVgap(10);
        signupForm.setHgap(10);

        signupForm.add(new Label("Username:"), 0, 0);
        signupForm.add(new TextField(), 1, 0);
        signupForm.add(new Label("Email:"), 0, 1);
        signupForm.add(new TextField(), 1, 1);
        signupForm.add(new Label("Password:"), 0, 2);
        signupForm.add(new PasswordField(), 1, 2);
        signupForm.add(new Label("Re-enter Password:"), 0, 3);
        signupForm.add(new PasswordField(), 1, 3);

        Button registerButton = new Button("Register");
        registerButton.setId("registerButton");
        signupForm.add(registerButton, 1, 4);

        signupForm.setStyle("-fx-alignment: center;");

        Scene scene2 = new Scene(signupForm, 400, 300);
        scene2.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        return scene2;
    }



    private List<BookTest> getBooksList() {
        return List.of(
                new BookTest("Book 1", "1234567890", "Author 1", "2024-12-01", "path/to/book1.jpg"),
                new BookTest("Book 2", "2345678901", "Author 2", "2024-11-28", "path/to/book2.jpg"),
                new BookTest("Book 3", "3456789012", "Author 3", "2024-11-25", "path/to/book3.jpg")
        );
    }

    private Scene createUserDashboard(List<BookTest> books) {
        VBox userDashboard = UserDashboard.createUserDashboard(books);
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
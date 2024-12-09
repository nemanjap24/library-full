package com.polo.libraryui;

import com.polo.libraryui.model.Book;
import com.polo.libraryui.util.HttpService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private final HttpService httpService = new HttpService();

    @Override
    public void start(Stage primaryStage) {
        // Start with empty book list
        List<Book> initialBooks = new ArrayList<>();
        SceneManager sceneManager = new SceneManager(primaryStage, initialBooks);
        sceneManager.showStartScene();

        primaryStage.setTitle("Library App");
        primaryStage.show();

//        // Fetch books asynchronously
//        httpService.getAllBooks()
//                .thenAccept(books -> Platform.runLater(() -> {
//                    sceneManager.updateBooks(books);
//                }))
//                .exceptionally(throwable -> {
//                    Platform.runLater(() -> {
//                        // Show error alert
//                        System.err.println("Failed to load books: " + throwable.getMessage());
//                    });
//                    return null;
//                });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
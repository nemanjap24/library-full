package com.polo.libraryui;

import com.polo.libraryui.model.Book;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<Book> books = getBooksList();

        SceneManager sceneManager = new SceneManager(primaryStage, books);
        sceneManager.showStartScene();

        primaryStage.setTitle("Library App");
        primaryStage.show();
    }

    // todo: to be removed
    private List<Book> getBooksList() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "ISBN001", "Author 1", 5));
        books.add(new Book(2L, "Book 2", "ISBN002", "Author 2", 3));
        // Add more books as needed
        return books;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.polo.libraryui;

import com.polo.libraryui.model.Book;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<Book> initialBooks = new ArrayList<>();
        SceneManager sceneManager = new SceneManager(primaryStage, initialBooks);

        primaryStage.setTitle("Library App");
        primaryStage.setMaximized(true); // Keep window maximized
        primaryStage.show();

        sceneManager.showStartScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
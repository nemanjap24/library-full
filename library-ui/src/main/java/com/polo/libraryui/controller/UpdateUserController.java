package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.model.User;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateUserController {
    public UpdateUserController(Stage stage, SceneManager sceneManager, User user) {
    }

    public Parent getView() {
        return new VBox();
    }
}

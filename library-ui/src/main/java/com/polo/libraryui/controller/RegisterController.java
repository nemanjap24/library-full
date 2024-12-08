package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.view.RegisterView;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class RegisterController {
    private RegisterView view;
    private Stage stage;
    private SceneManager sceneManager;

    public RegisterController(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.view = new RegisterView();
        initialize();
    }

    private void initialize() {
        view.getRegisterButton().setOnAction(e -> sceneManager.showLoginScene());
        view.getBackButton().setOnAction(e -> sceneManager.showStartScene());
    }

    public Parent getView() { return view.getView(); }
}
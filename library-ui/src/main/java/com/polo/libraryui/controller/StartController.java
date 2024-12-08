package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.view.StartView;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class StartController {
    private StartView view;
    private Stage stage;
    private SceneManager sceneManager;

    public StartController(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.view = new StartView();
        initialize();
    }

    private void initialize() {
        view.getLoginButton().setOnAction(e -> sceneManager.showLoginScene());
        view.getSignupButton().setOnAction(e -> sceneManager.showRegisterScene());
    }

    public Parent getView() { return view.getView(); }
}
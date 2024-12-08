package com.polo.libraryui.controller;

import com.polo.libraryui.SceneManager;
import com.polo.libraryui.view.RegisterBookFormView;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class RegisterBookFormController {
    private RegisterBookFormView view;
    private Stage stage;
    private SceneManager sceneManager;

    public RegisterBookFormController(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.view = new RegisterBookFormView();
        initialize();
    }

    private void initialize() {
        view.getSubmitButton().setOnAction(e -> sceneManager.showAdminDashboardScene());
    }

    public Parent getView() { return view.getView(); }
}
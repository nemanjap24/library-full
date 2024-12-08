package com.polo.libraryui;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BackButtonUtil {
    public static Button createBackButton(Stage stage, javafx.scene.Scene targetScene, String input) {
        Button backButton = new Button(input);
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> stage.setScene(targetScene));
        return backButton;
    }
}

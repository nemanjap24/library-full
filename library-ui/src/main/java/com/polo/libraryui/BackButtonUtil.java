package com.polo.libraryui;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BackButtonUtil {
    public static Button createBackButton(Stage stage, javafx.scene.Scene targetScene, String input) {
        Button backButton = new Button(input);

        // Style the button (optional, adapt to your styles.css if needed)
        backButton.getStyleClass().add("back-button");

        // On-click action to navigate to the target scene
        backButton.setOnAction(e -> stage.setScene(targetScene));

        return backButton;
    }
}

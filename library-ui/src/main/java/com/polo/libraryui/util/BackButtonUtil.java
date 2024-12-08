package com.polo.libraryui.util;

import javafx.scene.control.Button;

public class BackButtonUtil {
    public static Button createBackButton(String text) {
        Button backButton = new Button(text);
        backButton.getStyleClass().add("back-button");
        return backButton;
    }
}
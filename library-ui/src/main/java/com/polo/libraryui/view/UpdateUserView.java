package com.polo.libraryui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UpdateUserView {
    private VBox root;
    private PasswordField newPasswordField;
    private ComboBox<String> roleComboBox;
    private Button submitButton;
    private Button backButton;

    public UpdateUserView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");

        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("USER", "ADMIN");

        submitButton = new Button("Update");
        submitButton.getStyleClass().add("button-update");

        backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");

        formGrid.add(new Label("New Password:"), 0, 0);
        formGrid.add(newPasswordField, 1, 0);
        formGrid.add(new Label("Role:"), 0, 1);
        formGrid.add(roleComboBox, 1, 1);

        root.getChildren().addAll(formGrid, submitButton, backButton);
    }

    public Parent getView() { return root; }
    public PasswordField getNewPasswordField() { return newPasswordField; }
    public ComboBox<String> getRoleComboBox() { return roleComboBox; }
    public Button getSubmitButton() { return submitButton; }
    public Button getBackButton() { return backButton; }
}
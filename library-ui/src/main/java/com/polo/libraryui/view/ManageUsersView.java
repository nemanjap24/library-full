package com.polo.libraryui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManageUsersView {
    private VBox root;
    private ListView<String> usersListView;
    private Button deleteButton;
    private Button updateButton;
    private Button seeBorrowedButton;
    private Button backButton;

    public ManageUsersView() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        usersListView = new ListView<>();
        usersListView.setPrefSize(600, 400);

        deleteButton = new Button("Delete User");
        deleteButton.getStyleClass().add("button-remove");

        updateButton = new Button("Update User");
        updateButton.getStyleClass().add("button-update");

        seeBorrowedButton = new Button("See Borrowed Books");
        seeBorrowedButton.getStyleClass().add("button-view");

        backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(deleteButton, updateButton, seeBorrowedButton);

        root.getChildren().addAll(usersListView, buttonBox, backButton);
    }

    public Parent getView() { return root; }
    public ListView<String> getUsersListView() { return usersListView; }
    public Button getDeleteButton() { return deleteButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getSeeBorrowedButton() { return seeBorrowedButton; }
    public Button getBackButton() { return backButton; }
}
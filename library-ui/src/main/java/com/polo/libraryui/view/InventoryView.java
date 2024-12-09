package com.polo.libraryui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InventoryView {
    private VBox root;
    private ListView<String> booksListView;
    private Button addButton;
    private Button updateButton;
    private Button removeButton;
    private Button backButton;

    public InventoryView() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Book Inventory");
        booksListView = new ListView<>();
        booksListView.setPrefSize(600, 400);

        addButton = new Button("Add");
        updateButton = new Button("Update");
        removeButton = new Button("Remove");
        backButton = new Button("Back");

        HBox buttonBox = new HBox(10, addButton, updateButton, removeButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titleLabel, booksListView, buttonBox, backButton);
    }

    public Parent getView() {
        return root;
    }

    public ListView<String> getBooksListView() {
        return booksListView;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}
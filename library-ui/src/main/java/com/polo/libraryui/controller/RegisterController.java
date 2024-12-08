package com.polo.libraryui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polo.libraryui.SceneManager;
import com.polo.libraryui.dto.RegisterRequest;
import com.polo.libraryui.model.User;
import com.polo.libraryui.view.RegisterView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class RegisterController {
    private RegisterView view;
    private Stage stage;
    private SceneManager sceneManager;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public RegisterController(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.view = new RegisterView();
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        initialize();
    }

    private void initialize() {
        view.getRegisterButton().setOnAction(e -> handleRegistration());
        view.getBackButton().setOnAction(e -> sceneManager.showStartScene());
    }

    private void handleRegistration() {
        String username = view.getUsernameField().getText();
        String email = view.getEmailField().getText();
        String password = view.getPasswordField().getText();
        String confirmPassword = view.getConfirmPasswordField().getText();

        // Client-side validation
        if (!validateInputs(username, email, password, confirmPassword)) {
            return;
        }

        // Disable register button to prevent multiple submissions
        view.getRegisterButton().setDisable(true);

        RegisterRequest registerRequest = new RegisterRequest(username, email, password, confirmPassword);

        try {
            String requestBody = objectMapper.writeValueAsString(registerRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() == 201) {
                            try {
                                return objectMapper.readValue(response.body(), User.class);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                return null;
                            }
                        } else {
                            String errorMessage = response.body();
                            Platform.runLater(() -> showAlert("Registration Error", errorMessage));
                            return null;
                        }
                    })
                    .thenAccept(user -> Platform.runLater(() -> {
                        view.getRegisterButton().setDisable(false);
                        if (user != null) {
                            showAlert("Success", "Registration successful!");
                            sceneManager.showLoginScene();
                        }
                    }))
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        Platform.runLater(() -> {
                            view.getRegisterButton().setDisable(false);
                            showAlert("Error", "An error occurred during registration.");
                        });
                        return null;
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
            view.getRegisterButton().setDisable(false);
            showAlert("Error", "An error occurred during registration.");
        }
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return false;
        }

        if (!Pattern.matches(EMAIL_REGEX, email)) {
            showAlert("Validation Error", "Invalid email format.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Validation Error", "Passwords don't match.");
            return false;
        }

        if (password.length() < 6) {
            showAlert("Validation Error", "Password must be at least 6 characters long.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getView() {
        return view.getView();
    }
}
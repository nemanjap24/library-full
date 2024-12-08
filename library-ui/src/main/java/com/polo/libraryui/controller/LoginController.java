package com.polo.libraryui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polo.libraryui.SceneManager;
import com.polo.libraryui.dto.AuthenticationResponse;
import com.polo.libraryui.dto.LoginRequest;
import com.polo.libraryui.model.User;
import com.polo.libraryui.service.BookService;
import com.polo.libraryui.view.LoginView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class LoginController {
    private LoginView view;
    private Stage stage;
    private SceneManager sceneManager;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private BookService bookService;

    public LoginController(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
        this.view = new LoginView();
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.bookService = new BookService();
        initialize();
    }

    private void initialize() {
        view.getLoginButton().setOnAction(e -> handleLogin());
        view.getBackButton().setOnAction(e -> sceneManager.showStartScene());
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Username and password cannot be empty.");
            return;
        }

        view.getLoginButton().setDisable(true);
        LoginRequest loginRequest = new LoginRequest(username, password);

        try {
            String requestBody = objectMapper.writeValueAsString(loginRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() == 200) {
                            try {
                                AuthenticationResponse authResponse =
                                        objectMapper.readValue(response.body(), AuthenticationResponse.class);

                                User user = new User();
                                user.setUsername(authResponse.getUsername());
                                user.setRole(authResponse.getRole());
                                user.setToken(authResponse.getToken());
                                return user;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                return null;
                            }
                        } else {
                            return null;
                        }
                    })
                    .thenCompose(user -> {
                        if (user != null) {
                            // Fetch books after successful login
                            return bookService.getAllBooks(user)
                                    .thenApply(books -> {
                                        sceneManager.updateBooks(books);
                                        return user;
                                    });
                        }
                        return CompletableFuture.completedFuture(null);
                    })
                    .thenAccept(user -> Platform.runLater(() -> {
                        view.getLoginButton().setDisable(false);
                        if (user != null) {
                            sceneManager.setCurrentUser(user); // Add this line
                            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                                sceneManager.showAdminDashboardScene();
                            } else {
                                sceneManager.showUserDashboardScene();
                            }
                        } else {
                            showAlert("Login Failed", "Invalid username or password.");
                        }
                    }))
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        Platform.runLater(() -> {
                            view.getLoginButton().setDisable(false);
                            showAlert("Error", "An error occurred while trying to log in.");
                        });
                        return null;
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
            view.getLoginButton().setDisable(false);
            showAlert("Error", "An error occurred while trying to log in.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
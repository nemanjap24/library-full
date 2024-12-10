package com.polo.libraryui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polo.libraryui.model.User;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserService {
    private static final String API_BASE_URL = "http://localhost:8080/api/users";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public UserService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<List<User>> getAllUsers(User currentUser) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .header("Authorization", "Bearer " + currentUser.getToken())
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            return objectMapper.readValue(response.body(),
                                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse users", e);
                        }
                    }
                    throw new RuntimeException("Failed to fetch users: " + response.statusCode());
                });
    }

    public CompletableFuture<Void> deleteUser(User currentUser, Long userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + userId))
                .header("Authorization", "Bearer " + currentUser.getToken())
                .DELETE()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() != 204) {
                        throw new RuntimeException("Failed to delete user: " + response.body());
                    }
                });
    }
}
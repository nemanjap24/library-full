package com.polo.libraryui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polo.libraryui.model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TransactionService {
    private static final String API_BASE_URL = "http://localhost:8080/api/transactions";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TransactionService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<Void> borrowBook(User user, Long bookId) {
        return sendTransactionRequest(user, bookId, "borrow");
    }

    public CompletableFuture<Void> returnBook(User user, Long bookId) {
        return sendTransactionRequest(user, bookId, "return");
    }

    private CompletableFuture<Void> sendTransactionRequest(User user, Long bookId, String action) {
        if (user.getUserId() == null) {
            return CompletableFuture.failedFuture(
                    new IllegalStateException("User ID not initialized")
            );
        }

        var dto = Map.of(
                "userId", user.getUserId(),
                "bookId", bookId
        );

        try {
            String requestBody = objectMapper.writeValueAsString(dto);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "/" + action))
                    .header("Authorization", "Bearer " + user.getToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() != 200) {
                            throw new RuntimeException("Failed to " + action + " book: " + response.body());
                        }
                        return null;
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}